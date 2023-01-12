package org.eclipse.tractusx.ssi.extensions.core.credentials;


import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.nimbusds.jwt.SignedJWT;

import java.util.List;

public interface SerializedJwtPresentationFactory {
    SignedJWT createPresentation(List<VerifiableCredential> credentials, String audience);
}
