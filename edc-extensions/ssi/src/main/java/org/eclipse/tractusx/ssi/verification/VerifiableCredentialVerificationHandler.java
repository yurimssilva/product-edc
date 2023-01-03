package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.VerifiableCredential;

public interface VerifiableCredentialVerificationHandler {
    boolean canHandle(VerifiableCredential credential);

    boolean checkTrust(VerifiableCredential credential);
}
