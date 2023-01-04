package org.eclipse.tractusx.ssi.verification;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import com.nimbusds.jose.JOSEException;
import org.eclipse.tractusx.ssi.resolver.Did;
import org.eclipse.tractusx.ssi.resolver.DidPublicKeyResolver;
import org.eclipse.tractusx.ssi.util.UriToDidConverter;

import java.net.URI;
import java.security.PublicKey;

public class VerifiablePresentationSignatureHandler implements VerifiablePresentationVerificationHandler {

    private final DidPublicKeyResolver didPublicKeyResolver;

    public VerifiablePresentationSignatureHandler(DidPublicKeyResolver didPublicKeyResolver) {
        this.didPublicKeyResolver = didPublicKeyResolver;
    }

    @Override
    public boolean canHandle(JwtVerifiablePresentation presentation) {
        return true;
    }

    @Override
    public boolean checkTrust(JwtVerifiablePresentation presentation) {
        final URI holderUri = presentation.getPayloadObject().getHolder();
        final Did holderDid = UriToDidConverter.convert(holderUri);
        final PublicKey key = didPublicKeyResolver.resolve(holderDid);

        try {
            return presentation.verify_Ed25519_EdDSA(key.getEncoded());
        } catch (JOSEException e) {
            throw new RuntimeException(e); // TODO
        }
    }
}
