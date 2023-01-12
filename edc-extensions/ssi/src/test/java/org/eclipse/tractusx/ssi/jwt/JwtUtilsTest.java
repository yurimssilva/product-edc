package org.eclipse.tractusx.ssi.jwt;

import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.tractusx.ssi.extensions.core.jwt.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;


public class JwtUtilsTest {

  ECPrivateKey privateKey;
  ECPublicKey publicKey;
  JwtUtils jwtUtils;

  @SneakyThrows
  @BeforeEach
  public void setUp(){
    // Add BC as Provider
    Security.addProvider(new BouncyCastleProvider());
    // Generate Keypair
    ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("secp256r1");
    KeyPairGenerator keyPairGenerator = null;
    keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
    keyPairGenerator.initialize(ecGenSpec, new SecureRandom());

    java.security.KeyPair pair = keyPairGenerator.generateKeyPair();
    privateKey = (ECPrivateKey) pair.getPrivate();
    publicKey = (ECPublicKey) pair.getPublic();
    jwtUtils = new JwtUtils();
  }

  @Test
  @SneakyThrows
  public void testJwtCreationValidation() {
    // given
    boolean result = false;
    String issuer = "did:web:someurl/wellknown";
    String subject = "subject";
    String audience = "did:web:someaudience/wellknown";
    // when
    SignedJWT jwt = jwtUtils.create(privateKey, issuer,subject, audience, "claim");
    result = jwtUtils.verify(jwt, this.publicKey, audience);
    // then
    Assertions.assertTrue(result);
  }
}
