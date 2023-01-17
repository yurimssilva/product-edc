package org.eclipse.tractusx.ssi.extensions.core.iam;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import kotlin.ranges.UIntRange;
import org.bouncycastle.asn1.x509.Holder;
import org.eclipse.edc.spi.iam.ClaimToken;
import org.eclipse.edc.spi.iam.IdentityService;
import org.eclipse.edc.spi.iam.TokenParameters;
import org.eclipse.edc.spi.iam.TokenRepresentation;
import org.eclipse.edc.spi.result.Result;
import org.eclipse.tractusx.ssi.extensions.core.credentials.SerializedJwtPresentationFactory;
import org.eclipse.tractusx.ssi.extensions.core.credentials.SerializedVerifiablePresentation;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdSerializer;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtVerifier;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.eclipse.tractusx.ssi.extensions.core.util.DidParser;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class SsiIdentityService implements IdentityService {

    private final SerializedJwtPresentationFactory presentationFactory;
    private final VerifiableCredentialWallet credentialStore;
    private final JsonLdSerializer jsonLdSerializer;
    private final SignedJwtVerifier jwtVerifier;
    private final SsiSettings settings;
    private final DidDocumentResolver didDocumentResolver;

    public SsiIdentityService(SerializedJwtPresentationFactory serializedJwtPresentationFactory, VerifiableCredentialWallet credentialStore, JsonLdSerializer jsonLdSerializer, SignedJwtVerifier jwtVerifier, SsiSettings settings, DidDocumentResolver didDocumentResolver) {
        this.presentationFactory = serializedJwtPresentationFactory;
        this.credentialStore = credentialStore;
        this.jsonLdSerializer = jsonLdSerializer;
        this.jwtVerifier = jwtVerifier;
        this.settings = settings;
        this.didDocumentResolver = didDocumentResolver;
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
        final SignedJWT membershipPresentation = presentationFactory.createPresentation(List.of(membershipCredential), audience);
        final TokenRepresentation tokenRepresentation = TokenRepresentation.Builder.newInstance().token(membershipPresentation.getParsedString()).build();

        return Result.success(tokenRepresentation);
    }

    @Override
    public Result<ClaimToken> verifyJwtToken(TokenRepresentation tokenRepresentation, String audience) {

        try {
            String token = tokenRepresentation.getToken();
            SignedJWT jwt = SignedJWT.parse(token);

            jwtVerifier.verify(jwt);

            // TODO where is audience and expiry etc. checked?

            final String vpClaimValue = jwt.getJWTClaimsSet().getClaim("vp").toString();
            final SerializedVerifiablePresentation vpSerialized = new SerializedVerifiablePresentation(vpClaimValue);
            VerifiablePresentation verifiablePresentation = jsonLdSerializer.deserializePresentation(vpSerialized);


            final Did trustedIssuer = settings.getDidDataspaceOperator();
            final Date today = new Date();
            for (final VerifiableCredential credential : verifiablePresentation.getVerifiableCredentials()) {

                if (!credential.getIssuer().equals(trustedIssuer.toUri())) {
                    // TODO Log untrusted issuer
                    return Result.failure("foo");
                }

                // check timezone = (UTC?)
                if (credential.getExpirationDate().after(today)) {
                    // TODO Log expired
                    return Result.failure("foo");
                }


                final URI credentialIssuer = credential.getIssuer();
                final Did issuer = DidParser.parse(credentialIssuer);
                final DidDocument didDocument = didDocumentResolver.resolve(issuer);

                final Ed25519Proof proof = credential.getProof();

                var proofValue = proof.getProofValue();


            }

            // TODO Validate & Verify Verifiable Credentials
            // TODO Parse Information from Verifiable Credentials and add to ClaimToken (e.g. BusinessPartnerNumber)


        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

//            if(!presentationVerification.verifyJwtPresentation(jwt)){
//                return Result.failure(token);
//            }
//            final VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(jwt.getPayload().toString());
//            if(!credentialVerification.validate(verifiableCredential)){
//                return Result.failure(token);
//            }
//            if(!credentialVerification.checkTrust(verifiableCredential)){
//                return Result.failure(token);
//            }
//            final CredentialSubject subject = verifiableCredential.getCredentialSubject();
//            final ClaimToken claimToken = ClaimToken.Builder.newInstance().claims(subject.getClaims()).build();
//            return Result.success(claimToken);
        return null;
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
//    public static JwtVerifiablePresentation fromCompactSerialization(String compactSerialization) {
//
//        SignedJWT signedJWT = null;
//        JWTClaimsSet jwtPayload = null;
//        try {
//            signedJWT = SignedJWT.parse(compactSerialization);
//            jwtPayload = signedJWT.getJWTClaimsSet();
//        } catch (ParseException e) {
//            throw new JwtParseException("no valid JWT", e);
//        }
//
//        if (jwtPayload == null) {
//            throw new SsiException("no valid JWT");
//        }
//
//        Map<String, Object> jsonObject = new HashMap<>();
//
//        final Map<String, Object> claims = jwtPayload.getClaims();
//        if (claims.containsKey(JwtKeywords.JWT_CLAIM_VP)) {
//            final Object vp = claims.get(JwtKeywords.JWT_CLAIM_VP);
//            if (vp instanceof Map) {
//                jsonObject.putAll((Map<String, Object>) claims.get(JwtKeywords.JWT_CLAIM_VP));
//            }
//        }
//
//        VerifiablePresentation payloadVerifiablePresentation = VerifiablePresentation.fromJsonObject(new LinkedHashMap<>(jsonObject));
//
//        return new JwtVerifiablePresentation(jwtPayload, payloadVerifiablePresentation, signedJWT, compactSerialization);
//    }
}
