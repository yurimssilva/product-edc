package org.eclipse.tractusx.edc.tests.features;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.eclipse.edc.boot.system.ExtensionLoader.loadMonitor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;
import org.eclipse.edc.boot.system.runtime.BaseRuntime;
import org.eclipse.edc.spi.EdcException;
import org.eclipse.edc.spi.monitor.ConsoleMonitor;
import org.eclipse.edc.spi.monitor.Monitor;
import org.jetbrains.annotations.NotNull;

public class TestRuntime extends BaseRuntime {
  private static final Monitor MONITOR = loadMonitor();
  public static final String MVN_WRAPPER = "mvnw";

  private final String moduleName;

  private final String logPrefix;
  private final Map<String, String> properties;
  private Thread runtimeThread;
  private File buildRoot = null;

  public TestRuntime(String moduleName, String logPrefix, Map<String, String> properties) {
    this.moduleName = moduleName;
    this.logPrefix = logPrefix;
    this.properties = Map.copyOf(properties);
  }

  public void start() throws Exception {
    // Find the project root directory, moving up the directory tree
    var root = findBuildRoot();

    // Run a Gradle custom task to determine the runtime classpath of the module to run
    String[] command = {
      new File(root, MVN_WRAPPER).getCanonicalPath(),
      "-pl",
      moduleName,
      "dependency:build-classpath"
    };
    Process exec = Runtime.getRuntime().exec(command, new String[] {}, root);
    var classpathString = new String(exec.getInputStream().readAllBytes());
    var cleanedClasspath =
        Arrays.stream(classpathString.split("\n"))
            .filter(it -> !it.startsWith("["))
            .filter(it -> !it.startsWith("Warning"))
            .findFirst()
            .get();
    var errorOutput = new String(exec.getErrorStream().readAllBytes());
    if (exec.waitFor() != 0) {
      throw new EdcException(
          format(
              "Failed to run gradle command: [%s]. Output: %s %s",
              String.join(" ", command), cleanedClasspath, errorOutput));
    }

    // Replace subproject JAR entries with subproject build directories in classpath.
    // This ensures modified classes are picked up without needing to rebuild dependent JARs.
    var classPathEntries =
        Arrays.stream(cleanedClasspath.split(":|\\s"))
            .filter(s -> !s.isBlank())
            .flatMap(p -> resolveClassPathEntry(root, p))
            .toArray(URL[]::new);

    // Create a ClassLoader that only has the target module class path, and is not
    // parented with the current ClassLoader.
    var classLoader =
        URLClassLoader.newInstance(classPathEntries, ClassLoader.getSystemClassLoader());

    // Temporarily inject system properties.
    var savedProperties = (Properties) System.getProperties().clone();
    properties.forEach(System::setProperty);

    var latch = new CountDownLatch(1);

    runtimeThread =
        new Thread(
            () -> {
              try {

                // Make the ClassLoader available to the ServiceLoader.
                // This ensures the target module's extensions are discovered and loaded at runtime
                // boot.
                Thread.currentThread().setContextClassLoader(classLoader);

                // Boot EDC runtime.
                super.boot();

                latch.countDown();
              } catch (Exception e) {
                throw new EdcException(e);
              }
            });

    MONITOR.info("Starting module " + moduleName);
    // Start thread and wait for EDC to start up.
    runtimeThread.start();

    if (!latch.await(20, SECONDS)) {
      throw new EdcException("Failed to start EDC runtime");
    }

    MONITOR.info("Module " + moduleName + " started");
    // Restore system properties.
    System.setProperties(savedProperties);
  }

  public void stop() throws Exception {
    if (runtimeThread != null) {
      runtimeThread.join();
    }
    super.shutdown();
  }

  public String getModuleName() {
    return moduleName;
  }

  @Override
  protected @NotNull Monitor createMonitor() {
    // disable logs when "quiet" log level is set
    if (System.getProperty("org.gradle.logging.level") != null) {
      return new Monitor() {};
    } else {
      return new ConsoleMonitor(logPrefix, ConsoleMonitor.Level.DEBUG);
    }
  }

  /**
   * Replace Gradle subproject JAR entries with subproject build directories in classpath. This
   * ensures modified classes are picked up without needing to rebuild dependent JARs.
   *
   * @param root project root directory.
   * @param classPathEntry class path entry to resolve.
   * @return resolved class path entries for the input argument.
   */
  private Stream<URL> resolveClassPathEntry(File root, String classPathEntry) {
    try {
      File f = new File(classPathEntry).getCanonicalFile();

      // If class path entry is not a JAR under the root (i.e. a sub-project), do not transform it
      boolean isUnderRoot =
          f.getCanonicalPath().startsWith(root.getCanonicalPath() + File.separator);
      if (!classPathEntry.toLowerCase(Locale.ROOT).endsWith(".jar") || !isUnderRoot) {
        var sanitizedClassPathEntry =
            classPathEntry.replace("build/resources/main", "src/main/resources");
        return Stream.of(new File(sanitizedClassPathEntry).toURI().toURL());
      }

      // Replace JAR entry with the resolved classes and resources folder
      var buildDir = f.getParentFile().getParentFile();
      return Stream.of(
          new File(buildDir, "/classes/java/main").toURI().toURL(),
          new File(buildDir, "../src/main/resources").toURI().toURL());
    } catch (IOException e) {
      throw new EdcException(e);
    }
  }

  // TODO: this does not need to cache buildroot. maybe this could be done by the test class and the
  // file passed to TestRuntime
  public File findBuildRoot() {
    if (buildRoot != null) {
      return buildRoot;
    } else {
      File canonicalFile;
      try {
        canonicalFile = (new File(".")).getCanonicalFile();
      } catch (IOException var2) {
        throw new IllegalStateException("Could not resolve current directory.", var2);
      }

      buildRoot = findBuildRoot(canonicalFile);
      if (buildRoot == null) {
        throw new IllegalStateException(
            "Could not find " + MVN_WRAPPER + " in parent directories.");
      } else {
        return buildRoot;
      }
    }
  }

  private static File findBuildRoot(File path) {
    var mvnw = new File(path, MVN_WRAPPER);
    if (mvnw.exists()) {
      return path;
    }
    var parent = path.getParentFile();
    if (parent != null) {
      return findBuildRoot(parent);
    }
    return null;
  }
}
