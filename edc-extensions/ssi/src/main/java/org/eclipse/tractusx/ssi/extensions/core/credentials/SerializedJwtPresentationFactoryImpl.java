package org.eclipse.tractusx.ssi.extensions.core.credentials;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtFactory;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class SerializedJwtPresentationFactoryImpl implements SerializedJwtPresentationFactory {

    private final SsiSettings settings;
    private final SignedJwtFactory jwtUtils;

    public SerializedJwtPresentationFactoryImpl(SsiSettings settings, SignedJwtFactory jwtUtils) {
        this.settings = settings;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public SignedJWT createPresentation(VerifiableCredential credentials, String audience) {
        return createPresentationTokenForCredential(credentials, audience);
    }

    private SignedJWT createPresentationTokenForCredential(VerifiableCredential verifiableCredential, String audience) {
        try {
            final byte[] privateKey = settings.getVerifibalePresentationSigningKey();
            final String didConnector = settings.getDidConnector().toString();
            KeyFactory kf = KeyFactory.getInstance("ECDSA");
            ECPrivateKey ecPk = (ECPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
            SignedJWT jwt = jwtUtils.create(ecPk,didConnector,didConnector, audience, verifiableCredential.toJson());

            return jwt;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // TODO
        }
    }
}
