package org.eclipse.tractusx.ssi.iam;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.edc.spi.iam.ClaimToken;
import org.eclipse.edc.spi.iam.TokenParameters;
import org.eclipse.edc.spi.iam.TokenRepresentation;
import org.eclipse.edc.spi.result.Result;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentationFactory;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentationFactoryImpl;
import org.eclipse.tractusx.ssi.util.KeyResourceLoader;
import org.eclipse.tractusx.ssi.util.TestDidHandler;
import org.eclipse.tractusx.ssi.util.VerifiableCredentialStoreFake;
import org.eclipse.tractusx.ssi.resolver.DidPublicKeyResolverHandler;
import org.eclipse.tractusx.ssi.resolver.DidPublicKeyResolverImpl;
import org.eclipse.tractusx.ssi.setting.SsiSettings;
import org.eclipse.tractusx.ssi.verification.VerifiableCredentialVerificationImpl;
import org.eclipse.tractusx.ssi.verification.VerifiablePresentationVerificationImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SsiIdentityServiceComponentTest {

    private static final String AUDIENCE = "http://localhost";

    private SsiIdentityService ssiIdentityService;

    // fakes
    private VerifiableCredentialStoreFake credentialStore;

    @BeforeEach
    public void setup() {
        final byte[] privateKey = KeyResourceLoader.readPrivateKey();
        final byte[] publicKey = KeyResourceLoader.readPublicKey();
        final SsiSettings settings = new SsiSettings(TestDidHandler.DID_TEST_ROOT, privateKey, publicKey);
        final DidPublicKeyResolverHandler publicKeyHandler = new TestDidHandler();
        final DidPublicKeyResolverImpl publicKeyResolver = new DidPublicKeyResolverImpl();
        publicKeyResolver.registerHandler(publicKeyHandler);

        final SerializedJwtPresentationFactory serializedJwtPresentationFactory = new SerializedJwtPresentationFactoryImpl(settings);
        credentialStore = new VerifiableCredentialStoreFake(settings);
        ssiIdentityService = new SsiIdentityService(serializedJwtPresentationFactory, credentialStore,
                VerifiableCredentialVerificationImpl.withAllHandlers(),
                VerifiablePresentationVerificationImpl.withAllHandlers(publicKeyResolver));
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
            System.out.printf("Key: %s, Value: %s%n", keyValue.getKey(), keyValue.getValue());
        }
    }

    @Test
    @SneakyThrows
    public void test2() {
        byte[] testEd25519PrivateKey = Hex.decodeHex("984b589e121040156838303f107e13150be4a80fc5088ccba0b0bdc9b1d89090de8777a28f8da1a74e7a13090ed974d879bf692d001cddee16e4cc9f84b60580".toCharArray());
        System.out.println("EXPECTED " + new String(testEd25519PrivateKey));

        var test = "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZWQyNTUxOQAAACDF36DQQiH8dMpv8fqd2PX77XOTSWqNu7pZCrE4cSJWVAAAAKCV5ePzleXj8wAAAAtzc2gtZWQyNTUxOQAAACDF36DQQiH8dMpv8fqd2PX77XOTSWqNu7pZCrE4cSJWVAAAAEA0Gxs9VHyMyeA6zDjqbGbFuzUOI1jFCNsDMC/TGKX4MMXfoNBCIfx0ym/x+p3Y9fvtc5NJao27ulkKsThxIlZUAAAAF2RvbWluaWtAZGVza3RvcC1NUy03QzU2AQIDBAUG";
        System.out.println("TEST " + test);
        var test64 = new String(Base64.decodeBase64(test.getBytes()));
        System.out.println("TEST64 " + test64);

        for (var part: test64.split(" ")) {
             System.out.println("PART " + part);
        }

        var test64Hex = Hex.encodeHexString(test64.getBytes());
        System.out.println("TEST64Hex " + test64Hex);

    }
}
