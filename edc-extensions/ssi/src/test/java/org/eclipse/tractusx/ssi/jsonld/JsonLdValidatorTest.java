package org.eclipse.tractusx.ssi.jsonld;

import com.apicatalog.jsonld.loader.DocumentLoader;
import foundation.identity.jsonld.ConfigurableDocumentLoader;
import foundation.identity.jsonld.JsonLDObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdValidator;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonLdValidatorTest {

  private JsonLdValidator validator;

  @BeforeEach
  public void setUp() {
    validator = new JsonLdValidatorImpl();
  }

  @Test
  public void validateTestFail() {
    JsonLDObject toTest = loadInvalidjsonLDObject();
    boolean result = false;
    String expectedException = "Undefined JSON-LD term: publicKeyMultibase";

    Exception exception =
        Assertions.assertThrows(
            RuntimeException.class,
            () -> {
              validator.validate(toTest);
            });

    Assertions.assertTrue(exception.getMessage().contains(expectedException));
  }

  @SneakyThrows
  private JsonLDObject loadInvalidjsonLDObject() {
    String didJsonLd =
        Files.readString(
            Paths.get(
                getClass().getClassLoader().getResource("./jsonld/invalidJsonLd.json").toURI()));
    DocumentLoader loader = new ConfigurableDocumentLoader();
    ((ConfigurableDocumentLoader) loader).setEnableHttps(true);
    JsonLDObject object = JsonLDObject.fromJson(didJsonLd);
    object.setDocumentLoader(loader);
    return object;
  }
}
