package org.eclipse.tractusx.ssi.extensions.core.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.spi.did.DidParser;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.PublicKey;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;

import java.security.interfaces.ECPublicKey;
import java.text.ParseException;
import java.util.List;

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
    final String issuer;
    final Did issuerDid;
    try{
      issuer = jwtClaimsSet.getIssuer();
      issuerDid = DidParser.parse(issuer);
    }catch (NullPointerException e){
      throw new JOSEException(e.getMessage());
    }
    // TODO add exception handling on invalid DID + Test for it
    final DidDocument issuerDidDocument = didDocumentResolver.resolve(issuerDid);

    List<PublicKey> publicKeys = issuerDidDocument.getPublicKeys();

    // verify JWT signature
    try {
      for(org.eclipse.tractusx.ssi.spi.did.PublicKey pk : publicKeys){
        boolean verified = jwt.verify(new ECDSAVerifier((ECPublicKey) pk.getKey()));
        if(verified){
          return true;
        }
      }
      return false;
    } catch (JOSEException e) {
      throw new JOSEException(e.getMessage());
    }
  }
}
