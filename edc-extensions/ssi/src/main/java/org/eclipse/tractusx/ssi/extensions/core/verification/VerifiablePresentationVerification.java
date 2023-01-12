package org.eclipse.tractusx.ssi.extensions.core.verification;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import com.nimbusds.jwt.SignedJWT;

public interface VerifiablePresentationVerification {
    boolean verifyJwtPresentation(SignedJWT jwtPresentation);
}
