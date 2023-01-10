package org.eclipse.tractusx.ssi.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Convenience/helper class to generate and verify Signed JSON Web Tokens (JWTs) for communicating between connector instances.
 */
public class JwtUtils {

  /**
   * Creates a signed JWT {@link SignedJWT} that contains a set of claims and an issuer. Although all private key types are possible, in the context of Distributed Identity
   * using an Elliptic Curve key ({@code P-256}) is advisable.
   *
   * @param privateKey A Private Key
   * @param issuer     the value of the token issuer claim.
   * @param subject    the value of the token subject claim. For Distributed Identity, this value is identical to the issuer claim.
   * @param audience   the value of the token audience claim, e.g. the IDS Webhook address.
   * @param clock      clock used to get current time.
   * @return a {@code SignedJWT} that is signed with the private key and contains all claims listed.
   */

  public SignedJWT create(ECPrivateKey privateKey, String issuer, String subject, String audience) {
    var claimsSet = new JWTClaimsSet.Builder()
            .issuer(issuer)
            .subject(subject)
            .audience(audience)
            .expirationTime(new Date(new Date().getTime() + 60 * 1000))
            .jwtID(UUID.randomUUID().toString())
            .build();

    ECDSASigner signer = null;
    try {
      signer = new ECDSASigner(privateKey);
      if(!signer.supportedJWSAlgorithms().contains(JWSAlgorithm.ES256)){
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

  /**
   * Verifies a VerifiableCredential using the issuer's public key
   *
   * @param jwt       a {@link SignedJWT} that was sent by the claiming party.
   * @param publicKey The claiming party's public key
   * @param audience  The intended audience
   * @return true if verified, false otherwise
   */
  public boolean verify(SignedJWT jwt, ECPublicKey publicKey, String audience) throws JOSEException {
    // verify JWT signature
    try {
      var verified = jwt.verify(new ECDSAVerifier(publicKey));
      if (!verified) {
        return false;
      }
    } catch (JOSEException e) {
      throw new JOSEException(e.getMessage());
    }

    JWTClaimsSet jwtClaimsSet;
    try {
      jwtClaimsSet = jwt.getJWTClaimsSet();
    } catch (ParseException e) {
      throw new JOSEException(e.getMessage());
    }

    // verify claims
    var exactMatchClaims = new JWTClaimsSet.Builder()
            .audience(audience)
            .build();
    var requiredClaims = Set.of("iss", "sub", "exp");

    var claimsVerifier = new DefaultJWTClaimsVerifier<>(exactMatchClaims, requiredClaims);
    try {
      claimsVerifier.verify(jwtClaimsSet, null);
    } catch (BadJWTException e) {
      throw new JOSEException(e.getMessage());
    }

    return true;
  }
}