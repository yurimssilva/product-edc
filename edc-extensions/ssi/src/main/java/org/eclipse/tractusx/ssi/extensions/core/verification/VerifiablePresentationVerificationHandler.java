package org.eclipse.tractusx.ssi.extensions.core.verification;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import com.nimbusds.jwt.SignedJWT;

public interface VerifiablePresentationVerificationHandler {
    boolean canHandle(SignedJWT presentation);
    boolean checkTrust(SignedJWT presentation);
}
