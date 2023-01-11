package org.eclipse.tractusx.ssi.iam;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.JwtKeywords;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import com.danubetech.verifiablecredentials.validation.Validation;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.edc.spi.iam.ClaimToken;
import org.eclipse.edc.spi.iam.IdentityService;
import org.eclipse.edc.spi.iam.TokenParameters;
import org.eclipse.edc.spi.iam.TokenRepresentation;
import org.eclipse.edc.spi.result.Result;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentation;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentationFactory;
import org.eclipse.tractusx.ssi.exception.JwtParseException;
import org.eclipse.tractusx.ssi.exception.SsiException;
import org.eclipse.tractusx.ssi.store.VerifiableCredentialStore;
import org.eclipse.tractusx.ssi.verification.VerifiableCredentialVerification;
import org.eclipse.tractusx.ssi.verification.VerifiablePresentationVerification;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SsiIdentityService implements IdentityService {

    private final SerializedJwtPresentationFactory presentationFactory;
    private final VerifiableCredentialStore credentialStore;
    private final VerifiableCredentialVerification credentialVerification;
    private final VerifiablePresentationVerification presentationVerification;

    public SsiIdentityService(SerializedJwtPresentationFactory serializedJwtPresentationFactory, VerifiableCredentialStore credentialStore, VerifiableCredentialVerification credentialVerification, VerifiablePresentationVerification presentationVerification) {
        this.presentationFactory = serializedJwtPresentationFactory;
        this.credentialStore = credentialStore;
        this.credentialVerification = credentialVerification;
        this.presentationVerification = presentationVerification;
    }

    /**
     * This function is called to get the JWT token, that is send to another connector via IDS protocol.
     *
     * @param tokenParameters token parameters
     * @return token
     */
    @Override
    public Result<TokenRepresentation> obtainClientCredentials(TokenParameters tokenParameters) {
        final String audience = tokenParameters.getAudience(); // IDS URL of another connector
        final VerifiableCredential membershipCredential = credentialStore.GetMembershipCredential();
        final SignedJWT membershipPresentation = presentationFactory.createPresentation(membershipCredential, audience);
        final TokenRepresentation tokenRepresentation = TokenRepresentation.Builder.newInstance().token(membershipPresentation.getParsedString()).build();

        return Result.success(tokenRepresentation);
    }

    @Override
    public Result<ClaimToken> verifyJwtToken(TokenRepresentation tokenRepresentation, String audience) {
        try {
            String token = tokenRepresentation.getToken();
            SignedJWT jwt = SignedJWT.parse(token);
            if(!presentationVerification.verifyJwtPresentation(jwt)){
                return Result.failure(token);
            }
            if(!presentationVerification.checkTrust(jwt)){
                return Result.failure(token);
            }

            final VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(jwt.getPayload().toString());

            if(credentialVerification.validate(verifiableCredential)){

            }

            if(credentialVerification.checkTrust(verifiableCredential)){

            }

            final CredentialSubject subject = verifiableCredential.getCredentialSubject();
            final ClaimToken claimToken = ClaimToken.Builder.newInstance().claims(subject.getClaims()).build();
            return Result.success(claimToken);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        /**
        //final JwtVerifiablePresentation jwtVerifiablePresentation = fromCompactSerialization();

        final VerifiablePresentation verifiablePresentation = jwtVerifiablePresentation.getPayloadObject();
        Validation.validate(verifiablePresentation);

        if (!presentationVerification.checkTrust(jwtVerifiablePresentation)) {
            throw new RuntimeException(); // TODO
        }

        final VerifiableCredential verifiableCredential = verifiablePresentation.getVerifiableCredential();
        Validation.validate(verifiableCredential);
        if (!credentialVerification.checkTrust(verifiableCredential)) {
            throw new RuntimeException(); // TODO
        }

        final CredentialSubject subject = verifiableCredential.getCredentialSubject();
        final ClaimToken token = ClaimToken.Builder.newInstance().claims(subject.getClaims()).build();
        return Result.success(token);
        **/

    }

    // From https://github.com/danubetech/verifiable-credentials-java/blob/main/src/main/java/com/danubetech/verifiablecredentials/jwt/JwtVerifiablePresentation.java
    // the release version uses a VC enum keyword, and the fix with the VP is not released yet.
    // Therefore, we use this code snipped as workaround
    public static JwtVerifiablePresentation fromCompactSerialization(String compactSerialization) {

        SignedJWT signedJWT = null;
        JWTClaimsSet jwtPayload = null;
        try {
            signedJWT = SignedJWT.parse(compactSerialization);
            jwtPayload = signedJWT.getJWTClaimsSet();
        } catch (ParseException e) {
            throw new JwtParseException("no valid JWT", e);
        }

        if (jwtPayload == null) {
            throw new SsiException("no valid JWT");
        }

        Map<String, Object> jsonObject = new HashMap<>();

        final Map<String, Object> claims = jwtPayload.getClaims();
        if (claims.containsKey(JwtKeywords.JWT_CLAIM_VP)) {
            final Object vp = claims.get(JwtKeywords.JWT_CLAIM_VP);
            if (vp instanceof Map) {
                jsonObject.putAll((Map<String, Object>) claims.get(JwtKeywords.JWT_CLAIM_VP));
            }
        }

        VerifiablePresentation payloadVerifiablePresentation = VerifiablePresentation.fromJsonObject(new LinkedHashMap<>(jsonObject));

        return new JwtVerifiablePresentation(jwtPayload, payloadVerifiablePresentation, signedJWT, compactSerialization);
    }
}
