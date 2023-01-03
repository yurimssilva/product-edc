package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.VerifiableCredential;

@FunctionalInterface
public interface VerifiableCredentialVerification {
    boolean checkTrust(VerifiableCredential credential);
}
