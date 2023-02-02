package org.eclipse.tractusx.ssi.extensions.core.proof;

import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.extensions.core.proof.transform.LinkedDataTransformer;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.DidMethodIdentifier;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredentialType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LinkedDataTransformerTest {

  private LinkedDataTransformer linkedDataTransformer = new LinkedDataTransformer();

  @Test
  @SneakyThrows
  public void test() {
    final VerifiableCredential credential =
        VerifiableCredential.builder()
            .id(URI.create("did:test:id"))
                .contexts(Arrays.asList(new URI("testcontext")))
            .types(List.of(VerifiableCredentialType.VERIFIABLE_CREDENTIAL))
            .issuer(URI.create("did:test:isser"))
            .expirationDate(new Date(2025, 1, 1))
            .issuanceDate(new Date(2020, 1, 1))
            .proof(null)
            .credentialStatus(null)
            .build();

    var result = linkedDataTransformer.transform(credential);

    System.out.println(result.getValue());
  }

  @Test
  public void testDidEquals() {
    Did did1 = new Did(new DidMethod("test"), new DidMethodIdentifier("myKey"));
    Did did2 = new Did(new DidMethod("test"), new DidMethodIdentifier("myKey"));

    Assertions.assertEquals(did1, did2);
  }
}
