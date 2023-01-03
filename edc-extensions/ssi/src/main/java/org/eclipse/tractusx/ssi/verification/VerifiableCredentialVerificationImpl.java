package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.VerifiableCredential;

import java.util.ArrayList;
import java.util.List;

public class VerifiableCredentialVerificationImpl implements VerifiableCredentialVerification {

    public static VerifiableCredentialVerification withAllHandlers() {
        final VerifiableCredentialVerificationImpl verification = new VerifiableCredentialVerificationImpl();
        verification.registerHandler(new MembershipCredentialIssuerHandler());
        verification.registerHandler(new MembershipCredentialStatusHandler());

        return verification;
    }

    private List<VerifiableCredentialVerificationHandler> handlers = new ArrayList<>();

    @Override
    public boolean checkTrust(VerifiableCredential credential) {
        return handlers.stream()
                .filter(h -> h.canHandle(credential))
                .allMatch(h -> h.checkTrust(credential));
    }

    public void registerHandler(VerifiableCredentialVerificationHandler handler) {
        this.handlers.add(handler);
    }
}
