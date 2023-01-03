package org.eclipse.tractusx.ssi.iam;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.FromJwtConverter;
import com.danubetech.verifiablecredentials.jwt.JwtJwtVerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.JwtObject;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.JwtWrappingObject;
import com.danubetech.verifiablecredentials.jwt.ToJwtConverter;
import com.danubetech.verifiablecredentials.validation.Validation;
import org.eclipse.edc.spi.iam.ClaimToken;
import org.eclipse.edc.spi.iam.IdentityService;
import org.eclipse.edc.spi.iam.TokenParameters;
import org.eclipse.edc.spi.iam.TokenRepresentation;
import org.eclipse.edc.spi.result.Result;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentation;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentationFactory;
import org.eclipse.tractusx.ssi.store.VerifiableCredentialStore;

import java.text.ParseException;

public class SsiIdentityService implements IdentityService {

    private final SerializedJwtPresentationFactory presentationFactory;
    private final VerifiableCredentialStore credentialStore;

    public SsiIdentityService(SerializedJwtPresentationFactory serializedJwtPresentationFactory, VerifiableCredentialStore credentialStore) {
        this.presentationFactory = serializedJwtPresentationFactory;
        this.credentialStore = credentialStore;
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
        final SerializedJwtPresentation membershipPresentation = presentationFactory.createPresentation(membershipCredential, audience);
        final TokenRepresentation tokenRepresentation = TokenRepresentation.Builder.newInstance().token(membershipPresentation.getValue()).build();

        return Result.success(tokenRepresentation);
    }

    @Override
    public Result<ClaimToken> verifyJwtToken(TokenRepresentation tokenRepresentation, String audience) {
        try {

            final JwtVerifiablePresentation jwtVerifiablePresentation = JwtVerifiablePresentation.fromCompactSerialization(tokenRepresentation.getToken());
            // jwtVerifiablePresentation.verify_Ed25519_EdDSA() // TODO
            final VerifiablePresentation verifiablePresentation = jwtVerifiablePresentation.getPayloadObject();
            Validation.validate(verifiablePresentation);
            // TODO Validate Presentation more (e.g. with public key)

            final VerifiableCredential verifiableCredential = verifiablePresentation.getVerifiableCredential();
            Validation.validate(verifiableCredential);
            // TODO Validate Each Credential more (e.g. with public key)

            final CredentialSubject subject = verifiableCredential.getCredentialSubject();
            final ClaimToken token = ClaimToken.Builder.newInstance().claims(subject.getClaims()).build();
            return Result.success(token);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
