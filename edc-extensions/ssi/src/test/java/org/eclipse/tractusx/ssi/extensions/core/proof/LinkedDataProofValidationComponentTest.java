package org.eclipse.tractusx.ssi.extensions.core.proof;

import lombok.SneakyThrows;
import org.bouncycastle.math.ec.rfc8032.Ed25519;
import org.eclipse.tractusx.ssi.extensions.core.proof.hash.LinkedDataHasher;
import org.eclipse.tractusx.ssi.extensions.core.proof.transform.LinkedDataTransformer;
import org.eclipse.tractusx.ssi.extensions.core.proof.verify.LinkedDataSigner;
import org.eclipse.tractusx.ssi.extensions.core.proof.verify.LinkedDataVerifier;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.DidMethodIdentifier;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredentialType;
import org.eclipse.tractusx.ssi.extensions.core.testUtils.TestDidDocumentResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.eclipse.tractusx.ssi.extensions.core.testUtils.TestDidDocumentResolver.DID_TEST_OPERATOR;

public class LinkedDataProofValidationComponentTest {

  private LinkedDataProofValidation linkedDataProofValidation;

  // fake
  private TestDidDocumentResolver didDocumentResolver;

  @BeforeEach
  public void setup() {

    didDocumentResolver = new TestDidDocumentResolver();

    linkedDataProofValidation =
        new LinkedDataProofValidation(
            new LinkedDataHasher(),
            new LinkedDataTransformer(),
            new LinkedDataVerifier(didDocumentResolver),
            new LinkedDataSigner());
  }

  @Test
  public void test() {

    // prepare key
    Did verificationMethod = new Did(new DidMethod("test"), new DidMethodIdentifier("myKey"));
    byte[] privateKey = new byte[32];
    byte[] publicKey = new byte[32];

    Ed25519.generatePrivateKey(new SecureRandom(), privateKey);
    Ed25519.generatePublicKey(privateKey, 0, publicKey, 0);

    didDocumentResolver.registerVerificationMethod(
        DID_TEST_OPERATOR, verificationMethod, publicKey);

    VerifiableCredential credential = createCredential(null);

    final Ed25519Proof proof =
        linkedDataProofValidation.createProof(credential, verificationMethod, privateKey);

    credential = createCredential(proof);

    var isOk = linkedDataProofValidation.checkProof(credential);

    Assertions.assertTrue(isOk);
  }

  @SneakyThrows
  private VerifiableCredential createCredential(Ed25519Proof proof) {
    return VerifiableCredential.builder()
            .id(URI.create("did:test:id"))
            .types(List.of(VerifiableCredentialType.VERIFIABLE_CREDENTIAL))
            .issuer(DID_TEST_OPERATOR.toUri())
            .contexts(Arrays.asList(new URI("someUri")))
            .expirationDate(new Date(2025, 1, 1))
            .issuanceDate(new Date(2020, 1, 1))
            .proof(proof)
            .credentialStatus(null)
            .build();
  }
}