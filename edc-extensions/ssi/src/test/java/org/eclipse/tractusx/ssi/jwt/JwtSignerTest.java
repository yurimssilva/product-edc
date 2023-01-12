package org.eclipse.tractusx.ssi.jwt;

import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtFactory;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtVerifier;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;


public class JwtSignerTest {

    ECPrivateKey privateKey;
    ECPublicKey publicKey;
    SignedJwtFactory signedJwtFactory;
    SignedJwtVerifier signedJwtVerifier;

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        // Add BC as Provider
        Security.addProvider(new BouncyCastleProvider());
        // Generate Keypair
        ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("secp256r1");
        KeyPairGenerator keyPairGenerator = null;
        keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
        keyPairGenerator.initialize(ecGenSpec, new SecureRandom());

        java.security.KeyPair pair = keyPairGenerator.generateKeyPair();

        final SsiSettings settings = null;
        final DidDocumentResolver didDocumentResolver = null;

        privateKey = (ECPrivateKey) pair.getPrivate();
        publicKey = (ECPublicKey) pair.getPublic();
        signedJwtFactory = new SignedJwtFactory(settings);
        signedJwtVerifier = new SignedJwtVerifier(didDocumentResolver);
    }

    @Test
    @SneakyThrows
    public void testJwtCreationValidation() {
        // given
        boolean result = false;
        String audience = "did:web:someaudience/wellknown";
        // when
        SignedJWT jwt = signedJwtFactory.create(privateKey, audience, "claim");
        result = signedJwtVerifier.verify(jwt);
        // then
        Assertions.assertTrue(result);
    }
}
