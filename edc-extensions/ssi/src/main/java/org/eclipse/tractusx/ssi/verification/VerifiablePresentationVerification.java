package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;

@FunctionalInterface
public interface VerifiablePresentationVerification {
    boolean checkTrust(JwtVerifiablePresentation credential);
}
