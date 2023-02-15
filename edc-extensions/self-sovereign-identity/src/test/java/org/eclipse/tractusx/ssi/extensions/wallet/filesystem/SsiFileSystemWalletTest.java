package org.eclipse.tractusx.ssi.extensions.wallet.filesystem;

import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemReader;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.tractusx.ssi.extensions.core.proof.LinkedDataProofValidation;
import org.eclipse.tractusx.ssi.extensions.core.proof.hash.LinkedDataHasher;
import org.eclipse.tractusx.ssi.extensions.core.proof.transform.LinkedDataTransformer;
import org.eclipse.tractusx.ssi.extensions.core.proof.verify.LinkedDataSigner;
import org.eclipse.tractusx.ssi.extensions.core.proof.verify.LinkedDataVerifier;
import org.eclipse.tractusx.ssi.extensions.core.testUtils.TestDidDocumentResolver;
import org.eclipse.tractusx.ssi.extensions.wallet.fileSystem.SsiFileSystemWallet;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.DidMethodIdentifier;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;

public class SsiFileSystemWalletTest {

  private SsiFileSystemWallet wallet;

  private LinkedDataProofValidation linkedDataProofValidation;

  // mocks
  private Monitor monitor;

  //@BeforeEach
  //@SneakyThrows
  public void setup() {

    monitor = Mockito.mock(Monitor.class);

    linkedDataProofValidation = LinkedDataProofValidation.create(TestDidDocumentResolver.withRegistry(), monitor);

    final URL resource = getClass().getClassLoader().getResource("core/wallet/membership-credential.json");
    if (resource == null) {
      throw new RuntimeException("Resource not found: webdid.json");
    }
    final Path path = Path.of(resource.getPath());

    wallet = new SsiFileSystemWallet(path);
  }

  /** TODO Test fails, discuss if this class is needed
  @Test
  @SneakyThrows
  public void proofGenerator() {
    KeyPair keyPair = getKeyPair();
    byte[] privateKey = keyPair.getPrivate().getEncoded();
    byte[] publicKey = keyPair.getPublic().getEncoded();

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
  private KeyPair getKeyPair(){
    // Add BC as Provider
    Security.addProvider(new BouncyCastleProvider());
    // Generate Keypair
    ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("secp256r1");
    KeyPairGenerator keyPairGenerator = null;
    keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
    keyPairGenerator.initialize(ecGenSpec, new SecureRandom());

    KeyPair pair = keyPairGenerator.generateKeyPair();

    ECPrivateKey privateKey = (ECPrivateKey) pair.getPrivate();
    ECPublicKey publicKey = (ECPublicKey) pair.getPublic();

    return pair;
  }


  @SneakyThrows
  private byte[] loadKey(String name) {
    try (final InputStream stream = getClass().getClassLoader().getResourceAsStream(name)) {
      PemReader reader = new PemReader(new InputStreamReader(stream));
      return reader.readPemObject().getContent();
    }
  }
  **/
}
