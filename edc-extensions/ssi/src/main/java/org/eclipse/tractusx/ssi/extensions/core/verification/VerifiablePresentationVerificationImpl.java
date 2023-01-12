package org.eclipse.tractusx.ssi.extensions.core.verification;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.extensions.core.jwt.JwtUtils;
import org.eclipse.tractusx.ssi.extensions.core.resolver.DidPublicKeyResolverImpl;
import org.eclipse.tractusx.ssi.extensions.core.util.DidParser;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidPublicKeyResolver;
import org.eclipse.tractusx.ssi.extensions.did.web.util.DidWebParser;

import java.security.interfaces.ECPublicKey;
import java.text.ParseException;

public class VerifiablePresentationVerificationImpl implements VerifiablePresentationVerification {

    private final JwtUtils jwtUtils;
    private final DidPublicKeyResolver publicKeyResolver;
    private final DidWebParser didWebParser;

    VerifiablePresentationVerificationImpl(JwtUtils jwtUtils, DidPublicKeyResolverImpl didPublicKeyResolver, DidWebParser didWebParser){
            this.jwtUtils = jwtUtils;
            this.publicKeyResolver = didPublicKeyResolver;
            this.didWebParser = didWebParser;
    }

    @Override
    public boolean verifyJwtPresentation(SignedJWT jwtPresentation) {
        JwtUtils u = new JwtUtils();
        String audience = "";
        ECPublicKey pk = null;
        try {
            String issuerDid = (String)jwtPresentation.getJWTClaimsSet().getClaim("iss");
            Did issuer = DidParser.parse(issuerDid);
            this.publicKeyResolver.resolve(issuer);
            u.verify(jwtPresentation, null, null);
        } catch (JOSEException e) {
            return false;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     public static VerifiablePresentationVerification withAllHandlers(DidPublicKeyResolver didPublicKeyResolver) {
     final VerifiablePresentationVerificationImpl verification = new VerifiablePresentationVerificationImpl();
     verification.registerHandler(new VerifiablePresentationSignatureHandler(didPublicKeyResolver));
     return verification;
     }

     private List<VerifiablePresentationVerificationHandler> handlers = new ArrayList<>();

     public void registerHandler(VerifiablePresentationVerificationHandler handler) {
     handlers.add(handler);
     }

     public boolean checkTrust(SignedJWT presentation) {
     return handlers.stream()
     .filter(h -> h.canHandle(presentation))
     .allMatch(h -> h.checkTrust(presentation));
     }
     **/
}
