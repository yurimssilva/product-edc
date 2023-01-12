package org.eclipse.tractusx.ssi.extensions.core.credentials;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtFactory;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;

public class SerializedJwtPresentationFactoryImpl implements SerializedJwtPresentationFactory {

    private final SsiSettings settings;
    private final SignedJwtFactory signedJwtFactory;

    public SerializedJwtPresentationFactoryImpl(SsiSettings settings, SignedJwtFactory signedJwtFactory) {
        this.settings = settings;
        this.signedJwtFactory = signedJwtFactory;
    }

    @Override
    public SignedJWT createPresentation(List<VerifiableCredential> credentials, String audience) {
        return createPresentationTokenForCredential(credentials, audience);
    }

    private SignedJWT createPresentationTokenForCredential(List<VerifiableCredential> verifiableCredentials, String audience) {
            final VerifiablePresentation verifiablePresentation = null;
            SignedJWT jwt = signedJwtFactory.create(audience, verifiablePresentation);

            return jwt;
    }
}
