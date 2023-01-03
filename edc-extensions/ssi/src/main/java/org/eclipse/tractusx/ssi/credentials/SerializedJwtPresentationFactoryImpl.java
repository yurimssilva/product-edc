package org.eclipse.tractusx.ssi.credentials;

import com.danubetech.verifiablecredentials.VerifiableCredential;
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
            final byte[] privateKey = Hex.decodeHex("TODO get from Vault".toCharArray());
            final JwtVerifiableCredential jwtCredential = ToJwtConverter.toJwtVerifiableCredential(verifiableCredential, audience);
            final JwtJwtVerifiablePresentation jwtPresentation = JwtJwtVerifiablePresentation.fromJwtVerifiableCredential(jwtCredential, audience);
            final String jwtSerialized = jwtPresentation.sign_Ed25519_EdDSA(privateKey);

            return new SerializedJwtPresentation(jwtSerialized);
        } catch (DecoderException | IOException | JOSEException e) {
            throw new RuntimeException(e); // TODO
        }


    }
}
