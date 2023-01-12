package org.eclipse.tractusx.ssi.extensions.core.verification;

import com.danubetech.verifiablecredentials.VerifiableCredential;

public interface VerifiableCredentialVerification {
    boolean checkTrust(VerifiableCredential credential);

    boolean validate(VerifiableCredential credential);
}
