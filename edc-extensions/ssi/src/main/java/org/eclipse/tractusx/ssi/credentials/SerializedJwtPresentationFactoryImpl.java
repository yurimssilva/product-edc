package org.eclipse.tractusx.ssi.credentials;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.ToJwtConverter;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.tractusx.ssi.setting.SsiSettings;

import java.net.URI;

public class SerializedJwtPresentationFactoryImpl implements SerializedJwtPresentationFactory {

    private final SsiSettings settings;

    public SerializedJwtPresentationFactoryImpl(SsiSettings settings) {
        this.settings = settings;
    }

    @Override
    public SerializedJwtPresentation createPresentation(VerifiableCredential credentials, String audience) {
        return createPresentationTokenForCredential(credentials, audience);
    }

    private SerializedJwtPresentation createPresentationTokenForCredential(VerifiableCredential verifiableCredential, String audience) {
        try {
            final byte[] privateKey = settings.getDidPrivateKey();

            // TODO Maybe add more properties
            final VerifiablePresentation verifiablePresentation = VerifiablePresentation.builder()
                    .holder(settings.getDid())
                    .verifiableCredential(verifiableCredential)
                    .build();

            final JwtVerifiablePresentation jwtPresentation = ToJwtConverter.toJwtVerifiablePresentation(verifiablePresentation, audience);
            final String jwtSerialized = jwtPresentation.sign_Ed25519_EdDSA(privateKey);

            return new SerializedJwtPresentation(jwtSerialized);
        } catch (JOSEException e) {
            throw new RuntimeException(e); // TODO
        }


    }
}
