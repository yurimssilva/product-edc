package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.resolver.DidPublicKeyResolver;

import java.util.ArrayList;
import java.util.List;

public class VerifiablePresentationVerificationImpl implements VerifiablePresentationVerification {

    VerifiablePresentationVerificationImpl(){

    }

    public static VerifiablePresentationVerification withAllHandlers(DidPublicKeyResolver didPublicKeyResolver) {
        final VerifiablePresentationVerificationImpl verification = new VerifiablePresentationVerificationImpl();
        verification.registerHandler(new VerifiablePresentationSignatureHandler(didPublicKeyResolver));

        return verification;
    }

    private List<VerifiablePresentationVerificationHandler> handlers = new ArrayList<>();

    @Override
    public boolean checkTrust(SignedJWT presentation) {
        return handlers.stream()
                .filter(h -> h.canHandle(presentation))
                .allMatch(h -> h.checkTrust(presentation));
    }

    @Override
    public boolean verifyJwtPresentation(SignedJWT jwtPresentation) {
        return false;
    }

    public void registerHandler(VerifiablePresentationVerificationHandler handler) {
        handlers.add(handler);
    }
}
