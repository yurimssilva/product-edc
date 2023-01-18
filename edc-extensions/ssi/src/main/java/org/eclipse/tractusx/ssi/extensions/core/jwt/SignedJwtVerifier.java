package org.eclipse.tractusx.ssi.extensions.core.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.interfaces.ECPublicKey;
import java.text.ParseException;
import org.eclipse.tractusx.ssi.extensions.core.util.DidParser;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;

/**
 * Convenience/helper class to generate and verify Signed JSON Web Tokens (JWTs) for communicating
 * between connector instances.
 */
public class SignedJwtVerifier {

  private final DidDocumentResolver didDocumentResolver;

  public SignedJwtVerifier(DidDocumentResolver didDocumentResolver) {
    this.didDocumentResolver = didDocumentResolver;
  }

  /**
   * Verifies a VerifiableCredential using the issuer's public key
   *
   * @param jwt a {@link SignedJWT} that was sent by the claiming party.
   * @return true if verified, false otherwise
   */
  public boolean verify(SignedJWT jwt) throws JOSEException {

    JWTClaimsSet jwtClaimsSet;
    try {
      jwtClaimsSet = jwt.getJWTClaimsSet();
    } catch (ParseException e) {
      throw new JOSEException(e.getMessage());
    }

    final String issuer = jwtClaimsSet.getIssuer();
    final Did issuerDid = DidParser.parse(issuer);
    final DidDocument issuerDidDocument = didDocumentResolver.resolve(issuerDid);

    // TODO Get Public Key from DID Document
    ECPublicKey publicKey = null;

    // verify JWT signature
    try {
      return jwt.verify(new ECDSAVerifier(publicKey));
    } catch (JOSEException e) {
      throw new JOSEException(e.getMessage());
    }
  }
}
