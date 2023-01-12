package org.eclipse.tractusx.ssi.extensions.core.credentials;

import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdSerializer;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtFactory;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;

import java.util.List;

public class SerializedJwtPresentationFactoryImpl implements SerializedJwtPresentationFactory {

    private final SignedJwtFactory signedJwtFactory;
    private final JsonLdSerializer jsonLdSerializer;

    public SerializedJwtPresentationFactoryImpl(SignedJwtFactory signedJwtFactory, JsonLdSerializer jsonLdSerializer) {
        this.signedJwtFactory = signedJwtFactory;
        this.jsonLdSerializer = jsonLdSerializer;
    }

    @Override
    public SignedJWT createPresentation(List<VerifiableCredential> credentials, String audience) {
        final VerifiablePresentation verifiablePresentation = new VerifiablePresentation(credentials);
        final SerializedVerifiablePresentation serializedVerifiablePresentation = jsonLdSerializer.serializePresentation(verifiablePresentation);

        return signedJwtFactory.create(audience, serializedVerifiablePresentation);
    }

}
