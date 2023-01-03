package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;

public interface VerifiablePresentationVerificationHandler {
    boolean canHandle(JwtVerifiablePresentation presentation);
    boolean checkTrust(JwtVerifiablePresentation presentation);
}
