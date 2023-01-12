package org.eclipse.tractusx.ssi.extensions.core.verification;

import com.danubetech.verifiablecredentials.VerifiableCredential;

public interface VerifiableCredentialVerificationHandler {
    boolean canHandle(VerifiableCredential credential);

    boolean checkTrust(VerifiableCredential credential);
}
