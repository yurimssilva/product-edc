package org.eclipse.tractusx.ssi.iam;

import org.eclipse.edc.spi.iam.ClaimToken;
import org.eclipse.edc.spi.iam.TokenParameters;
import org.eclipse.edc.spi.iam.TokenRepresentation;
import org.eclipse.edc.spi.result.Result;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentationFactory;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentationFactoryImpl;
import org.eclipse.tractusx.ssi.fakes.VerifiableCredentialStoreFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SsiIdentityServiceComponentTest {

    private static final String AUDIENCE = "http://localhost";

    private SsiIdentityService ssiIdentityService;

    // fakes
    private VerifiableCredentialStoreFake credentialStore = new VerifiableCredentialStoreFake();

    @BeforeEach
    public void setup() {
        credentialStore = new VerifiableCredentialStoreFake();

        final SerializedJwtPresentationFactory serializedJwtPresentationFactory = new SerializedJwtPresentationFactoryImpl();
        ssiIdentityService = new SsiIdentityService(serializedJwtPresentationFactory, credentialStore);
    }

    @Test
    public void test() {

        // prepare
        credentialStore.prepareMembershipCredential();

        final TokenParameters tokenParameters = TokenParameters.Builder.newInstance().audience(AUDIENCE).build();
        final Result<TokenRepresentation> tokenRepresentationResult = ssiIdentityService.obtainClientCredentials(tokenParameters);
        final TokenRepresentation tokenRepresentation = tokenRepresentationResult.getContent();

        final Result<ClaimToken> claimTokenResult = ssiIdentityService.verifyJwtToken(tokenRepresentation, AUDIENCE);
        final ClaimToken claimToken = claimTokenResult.getContent();

        for (var keyValue : claimToken.getClaims().entrySet()) {
            System.out.println(String.format("Key: %s, Value: %s", keyValue.getKey(), keyValue.getValue()));
        }
    }
}
