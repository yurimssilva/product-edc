package org.eclipse.tractusx.ssi.extensions.core.credentials;

import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdMapper;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtFactory;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;

import java.util.List;

public class SerializedJwtPresentationFactoryImpl implements SerializedJwtPresentationFactory {

    private final SignedJwtFactory signedJwtFactory;
    private final JsonLdMapper jsonLdMapper;

    public SerializedJwtPresentationFactoryImpl(SignedJwtFactory signedJwtFactory, JsonLdMapper jsonLdMapper) {
        this.signedJwtFactory = signedJwtFactory;
        this.jsonLdMapper = jsonLdMapper;
    }

    @Override
    public SignedJWT createPresentation(List<VerifiableCredential> credentials, String audience) {
        final VerifiablePresentation verifiablePresentation = new VerifiablePresentation(credentials);
        final SerializedVerifiablePresentation serializedVerifiablePresentation = jsonLdMapper.serializePresentation(verifiablePresentation);

        return signedJwtFactory.create(audience, serializedVerifiablePresentation);
    }

}
