package org.eclipse.tractusx.ssi.credentials;


import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.nimbusds.jwt.SignedJWT;

public interface SerializedJwtPresentationFactory {
    SignedJWT createPresentation(VerifiableCredential credentials, String audience);
}
