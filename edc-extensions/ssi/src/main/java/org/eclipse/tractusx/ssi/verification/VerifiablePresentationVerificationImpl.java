package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import org.eclipse.tractusx.ssi.resolver.DidPublicKeyResolver;

import java.util.ArrayList;
import java.util.List;

public class VerifiablePresentationVerificationImpl implements VerifiablePresentationVerification {

    public static VerifiablePresentationVerification withAllHandlers(DidPublicKeyResolver didPublicKeyResolver) {
        final VerifiablePresentationVerificationImpl verification = new VerifiablePresentationVerificationImpl();
        verification.registerHandler(new VerifiablePresentationSignatureHandler(didPublicKeyResolver));

        return verification;
    }

    private List<VerifiablePresentationVerificationHandler> handlers = new ArrayList<>();

    @Override
    public boolean checkTrust(JwtVerifiablePresentation presentation) {
        return handlers.stream()
                .filter(h -> h.canHandle(presentation))
                .allMatch(h -> h.checkTrust(presentation));
    }

    public void registerHandler(VerifiablePresentationVerificationHandler handler) {
        handlers.add(handler);
    }
}
