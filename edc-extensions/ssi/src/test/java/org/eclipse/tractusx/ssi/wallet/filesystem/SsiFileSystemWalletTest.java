package org.eclipse.tractusx.ssi.wallet.filesystem;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;

import lombok.SneakyThrows;

import org.bouncycastle.util.io.pem.PemReader;
import org.eclipse.tractusx.ssi.extensions.core.proof.LinkedDataProofValidation;
import org.eclipse.tractusx.ssi.extensions.core.proof.hash.LinkedDataHasher;
import org.eclipse.tractusx.ssi.extensions.core.proof.transform.LinkedDataTransformer;
import org.eclipse.tractusx.ssi.extensions.core.proof.verify.LinkedDataSigner;
import org.eclipse.tractusx.ssi.extensions.core.proof.verify.LinkedDataVerifier;
import org.eclipse.tractusx.ssi.extensions.wallet.fileSystem.SsiFileSystemWallet;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.DidMethodIdentifier;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;
import org.eclipse.tractusx.ssi.core.util.TestDidDocumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SsiFileSystemWalletTest {

  private SsiFileSystemWallet wallet;
  private TestDidDocumentResolver didDocumentResolver;

  private LinkedDataProofValidation linkedDataProofValidation;

  @BeforeEach
  @SneakyThrows
  public void setup() {

    didDocumentResolver = new TestDidDocumentResolver();
    linkedDataProofValidation = new LinkedDataProofValidation(
        new LinkedDataHasher(),
        new LinkedDataTransformer(),
        new LinkedDataVerifier(didDocumentResolver),
        new LinkedDataSigner());

    final URL resource = getClass().getClassLoader().getResource("wallet/membership-credential.json");
    if (resource == null) {
      throw new RuntimeException("Resource not found: webdid.json");
    }
    final Path path = Path.of(resource.getPath());

    wallet = new SsiFileSystemWallet(path);
  }

  @Test
  @SneakyThrows
  public void proofGenerator() {
    byte[] privateKey = loadKey("ssi-private-key.pem");
    byte[] publicKey = loadKey("ssi-public-key.pem");

    Did verificationMethod = new Did(new DidMethod("test"), new DidMethodIdentifier("myKey"));
    var credential = wallet.getMembershipCredential();

    didDocumentResolver.registerVerificationMethod(
        TestDidDocumentResolver.DID_TEST_OPERATOR, verificationMethod, publicKey);

    final Ed25519Proof proof = linkedDataProofValidation.createProof(credential, verificationMethod, privateKey);

    System.out.println(proof);
  }

  @Test
  public void test() {
    var credential = wallet.getMembershipCredential();
    System.out.println(credential);
  }

  @SneakyThrows
  private byte[] loadKey(String name) {
    try (final InputStream stream = getClass().getClassLoader().getResourceAsStream(name)) {
      PemReader reader = new PemReader(new InputStreamReader(stream));
      return reader.readPemObject().getContent();
    }
  }
}
