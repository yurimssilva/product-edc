package org.eclipse.tractusx.ssi.extensions.core.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.Ed25519Signer;
import com.nimbusds.jose.crypto.Ed25519Verifier;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;

public class JwtSigner {


    private final OctetKeyPair privateJwk;
    private final OctetKeyPair publicJWK;

    public JwtSigner(OctetKeyPair privateJwk, OctetKeyPair publicJWK) {
        this.privateJwk = privateJwk;
        this.publicJWK = publicJWK;
    }

    public void sign(String jwk) throws JOSEException {
        JWSSigner signer = new Ed25519Signer(privateJwk);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("alice")
                .issuer("https://c2id.com")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.EdDSA).keyID(privateJwk.getKeyID()).build(),
                claimsSet);

        signedJWT.sign(signer);

        String s = signedJWT.serialize();
    }

    public boolean checkSignature(String jwt) throws ParseException, JOSEException {

        var signedJWT = SignedJWT.parse(jwt);

        JWSVerifier verifier = new Ed25519Verifier(publicJWK);

        return signedJWT.verify(verifier);
    }

}
