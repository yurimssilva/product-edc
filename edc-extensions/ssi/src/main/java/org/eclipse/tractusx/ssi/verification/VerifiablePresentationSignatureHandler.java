package org.eclipse.tractusx.ssi.verification;

import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.resolver.DidPublicKeyResolver;

public class VerifiablePresentationSignatureHandler implements VerifiablePresentationVerificationHandler {

    private final DidPublicKeyResolver didPublicKeyResolver;

    public VerifiablePresentationSignatureHandler(DidPublicKeyResolver didPublicKeyResolver) {
        this.didPublicKeyResolver = didPublicKeyResolver;
    }

    @Override
    public boolean canHandle(SignedJWT presentation) {
        return true;
    }

    @Override
    public boolean checkTrust(SignedJWT jwt) {
        return true;
        /*
        final String subject = jwt.getPayload().getSubject(); // holder
        final Did holderDid = DidParser.parse(subject); // TODO might be a URI, handle this. Maybe check for DID:Web URI
        final byte[] key = didPublicKeyResolver.resolve(holderDid);

        try {
            return jwt.verify_Ed25519_EdDSA(key);
        } catch (JOSEException e) {
            throw new RuntimeException(e); // TODO
        }
        */
    }
}
