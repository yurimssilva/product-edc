package org.eclipse.tractusx.ssi.extensions.core.verification;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtVerifier;

public class VerifiablePresentationVerificationImpl implements VerifiablePresentationVerification {

    private final SignedJwtVerifier jwtVerifier;

    VerifiablePresentationVerificationImpl(SignedJwtVerifier jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    @Override
    public boolean verifyJwtPresentation(SignedJWT signedJwt) {
        try {

            // TODO Check Audience
            // TODO Check Expiry

            return jwtVerifier.verify(signedJwt);


        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
