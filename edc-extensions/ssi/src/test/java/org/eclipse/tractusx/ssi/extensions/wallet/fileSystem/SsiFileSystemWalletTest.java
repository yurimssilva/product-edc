package org.eclipse.tractusx.ssi.extensions.wallet.fileSystem;

import java.net.URL;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SsiFileSystemWalletTest {

  private SsiFileSystemWallet wallet;

  @BeforeEach
  @SneakyThrows
  public void setup() {

    final URL resource = getClass().getClassLoader().getResource("webdid.json");
    if (resource == null) {
      throw new RuntimeException("Resource not found: webdid.json");
    }
    final Path path = Path.of(resource.getPath());

    wallet = new SsiFileSystemWallet(path);
  }

  @Test
  public void test() {
    var credential = wallet.getMembershipCredential();
  }
}
