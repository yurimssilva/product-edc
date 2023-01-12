package org.eclipse.tractusx.ssi.extensions.core.credentials;


import com.nimbusds.jwt.SignedJWT;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;

import java.util.List;

public interface SerializedJwtPresentationFactory {
    SignedJWT createPresentation(List<VerifiableCredential> credentials, String audience);
}
