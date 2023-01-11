package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.VerifiableCredential;

public interface VerifiableCredentialVerification {
    boolean checkTrust(VerifiableCredential credential);

    boolean validate(VerifiableCredential credential);
}
