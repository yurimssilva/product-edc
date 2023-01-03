package org.eclipse.tractusx.ssi.credentials;


import com.danubetech.verifiablecredentials.VerifiableCredential;

public interface SerializedJwtPresentationFactory {
    SerializedJwtPresentation createPresentation(VerifiableCredential credentials, String audience);
}
