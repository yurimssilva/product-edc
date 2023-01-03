package org.eclipse.tractusx.ssi.credentials;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.JwtJwtVerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.ToJwtConverter;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;

public class SerializedJwtPresentationFactoryImpl implements SerializedJwtPresentationFactory {

    @Override
    public SerializedJwtPresentation createPresentation(VerifiableCredential credentials, String audience) {
        return createPresentationTokenForCredential(credentials, audience);
    }

    private SerializedJwtPresentation createPresentationTokenForCredential(VerifiableCredential verifiableCredential, String audience) {
        try {
            // TODO get private key
            final byte[] privateKey = Hex.decodeHex("984b589e121040156838303f107e13150be4a80fc5088ccba0b0bdc9b1d89090de8777a28f8da1a74e7a13090ed974d879bf692d001cddee16e4cc9f84b60580".toCharArray());

            final VerifiablePresentation verifiablePresentation = VerifiablePresentation.builder()
                    .verifiableCredential(verifiableCredential)
                    .build();
            ToJwtConverter.toJwtVerifiablePresentation(verifiableCredential, audience);

            final JwtVerifiableCredential jwtCredential = ToJwtConverter.toJwtVerifiableCredential(verifiableCredential, audience);
            final JwtJwtVerifiablePresentation jwtPresentation = JwtJwtVerifiablePresentation.fromJwtVerifiableCredential(jwtCredential, audience);
            final String jwtSerialized = jwtPresentation.sign_Ed25519_EdDSA(privateKey);

            return new SerializedJwtPresentation(jwtSerialized);
        } catch (DecoderException | IOException | JOSEException e) {
            throw new RuntimeException(e); // TODO
        }


    }
}
