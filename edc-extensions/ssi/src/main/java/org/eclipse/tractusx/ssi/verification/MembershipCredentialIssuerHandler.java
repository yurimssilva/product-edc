package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import org.eclipse.tractusx.ssi.credentials.CredentialType;

public class MembershipCredentialIssuerHandler implements VerifiableCredentialVerificationHandler {
    @Override
    public boolean canHandle(VerifiableCredential credential) {
        return credential.getCredentialSubject().isType(CredentialType.MEMBERSHIP_CREDENTIAL);
    }

    @Override
    public boolean checkTrust(VerifiableCredential credential) {
        return credential.getIssuer().equals("http://localhost"); // TODO Make Configurable
    }
}
