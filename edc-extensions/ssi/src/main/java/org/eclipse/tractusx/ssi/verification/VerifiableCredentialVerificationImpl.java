package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import org.eclipse.tractusx.ssi.setting.SsiSettings;

import java.util.ArrayList;
import java.util.List;

public class VerifiableCredentialVerificationImpl implements VerifiableCredentialVerification {

    public static VerifiableCredentialVerification withAllHandlers(SsiSettings settings) {
        final VerifiableCredentialVerificationImpl verification = new VerifiableCredentialVerificationImpl();
        verification.registerHandler(new MembershipCredentialIssuerHandler(settings));
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
