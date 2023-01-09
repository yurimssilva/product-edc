package org.eclipse.tractusx.ssi.jwt;

import com.nimbusds.jwt.SignedJWT;
import jakarta.validation.constraints.AssertTrue;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;

import static org.junit.jupiter.api.Assertions.fail;

public class JwtUtilsTest {

  ECPrivateKey privateKey;
  ECPublicKey publicKey;
  JwtUtils jwtUtils;

  @BeforeEach
  public void setUp(){
    // Generate Keys
    Security.addProvider(new BouncyCastleProvider());
    ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("secp256r1");
    KeyPairGenerator keyPairGenerator = null;
    try {
      keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
      keyPairGenerator.initialize(ecGenSpec, new SecureRandom());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    } catch (NoSuchProviderException e) {
      throw new RuntimeException(e);
    } catch (InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e);
    }
    java.security.KeyPair pair = keyPairGenerator.generateKeyPair();
    privateKey = (ECPrivateKey) pair.getPrivate();
    publicKey = (ECPublicKey) pair.getPublic();
    jwtUtils = new JwtUtils();
  }

  @Test
  @SneakyThrows
  public void testJwtCreation() {
    //given
    boolean result = false;
    //when
    try{
      SignedJWT jwt = jwtUtils.create(privateKey, "did:web:someurl/wellknown" ,"subject", "did:web:someaudience/wellknown");
      result = jwtUtils.verify(jwt, this.publicKey, "did:web:someaudience/wellknown");
      Assertions.assertTrue(result);
    } catch (Exception e){
      fail();
    }

  }

}
