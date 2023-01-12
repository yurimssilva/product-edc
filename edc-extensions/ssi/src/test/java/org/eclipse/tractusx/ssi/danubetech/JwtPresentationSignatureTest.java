package org.eclipse.tractusx.ssi.danubetech;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.ToJwtConverter;
import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.extensions.core.iam.SsiIdentityService;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.eclipse.tractusx.ssi.util.KeyResourceLoader;
import org.eclipse.tractusx.ssi.util.TestDidHandler;
import org.eclipse.tractusx.ssi.util.VerifiableCredentialStoreFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtPresentationSignatureTest {

    public static final String AUDIENCE = "http://localhost";

    @Test
    @SneakyThrows
    public void testSignature() {
        final byte[] privateKey = KeyResourceLoader.readPrivateKey();
        final byte[] publicKey = KeyResourceLoader.readPublicKey();
        final SsiSettings settings = new SsiSettings(TestDidHandler.DID_TEST_OPERATOR, TestDidHandler.DID_TEST_OPERATOR, privateKey);
        final VerifiableCredentialStoreFake store = new VerifiableCredentialStoreFake(settings);

        final VerifiableCredential credential = store.GetMembershipCredential();
        final VerifiablePresentation verifiablePresentation = VerifiablePresentation.builder()
                .holder(TestDidHandler.DID_TEST_OPERATOR.toUri())
                .verifiableCredential(credential)
                .build();

        final JwtVerifiablePresentation jwtPresentation = ToJwtConverter.toJwtVerifiablePresentation(verifiablePresentation, AUDIENCE);
        final String jwtSerialized = jwtPresentation.sign_Ed25519_EdDSA(privateKey);

        final JwtVerifiablePresentation deserializedPresentation = SsiIdentityService.fromCompactSerialization(jwtSerialized);

        final boolean isValid = deserializedPresentation.verify_Ed25519_EdDSA(publicKey);

        Assertions.assertTrue(isValid);
    }
}
