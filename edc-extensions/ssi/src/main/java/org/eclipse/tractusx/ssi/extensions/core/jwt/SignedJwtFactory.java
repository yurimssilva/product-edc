package org.eclipse.tractusx.ssi.extensions.core.jwt;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.extensions.core.exception.SsiSettingException;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.jetbrains.annotations.NotNull;

import java.security.interfaces.ECPrivateKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Convenience/helper class to generate and verify Signed JSON Web Tokens (JWTs) for communicating between connector instances.
 */
public class SignedJwtFactory {

    public static final String SIGNING_METHOD_ES256 = "ES256";
    public static final List<String> SUPPORTED_SIGNING_METHODS = List.of(SIGNING_METHOD_ES256);

    private final SsiSettings settings;

    public SignedJwtFactory(SsiSettings settings) {
        this.settings = settings;
    }

    /**
     * Creates a signed JWT {@link SignedJWT} that contains a set of claims and an issuer. Although all private key types are possible, in the context of Distributed Identity
     * using an Elliptic Curve key ({@code P-256}) is advisable.
     *
     * @param privateKey A Private Key
     * @param audience   the value of the token audience claim, e.g. the IDS Webhook address.
     * @return a {@code SignedJWT} that is signed with the private key and contains all claims listed.
     */
    public SignedJWT create(ECPrivateKey privateKey, String audience, String vpClaim) { // TODO Use Signing Key Resolver Instead

        final String issuer = settings.getDidConnector().toString();
        final String subject = settings.getDidConnector().toString();

        var claimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(subject)
                .audience(audience)
                .claim("vp", vpClaim)
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .jwtID(UUID.randomUUID().toString())
                .build();


        if (settings.getVerifiablePresentationSigningMethod().equalsIgnoreCase(SIGNING_METHOD_ES256)) {
            return createSignedES256Jwt(privateKey, claimsSet);
        } else {
            throw new SsiSettingException("Unsupported signing method " + settings.getVerifiablePresentationSigningMethod());
        }
    }

    @NotNull
    private static SignedJWT createSignedES256Jwt(ECPrivateKey privateKey, JWTClaimsSet claimsSet) {
        ECDSASigner signer = null;
        try {
            signer = new ECDSASigner(privateKey);
            if (!signer.supportedJWSAlgorithms().contains(JWSAlgorithm.ES256)) {
                throw new RuntimeException("Invalid Signing Algorithm");
            }
            var algorithm = JWSAlgorithm.ES256;
            var header = new JWSHeader(algorithm);
            var vc = new SignedJWT(header, claimsSet);
            vc.sign(signer);
            return vc;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}