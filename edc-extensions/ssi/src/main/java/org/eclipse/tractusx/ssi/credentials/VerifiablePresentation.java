package org.eclipse.tractusx.ssi.credentials;

import java.util.List;

public interface VerifiablePresentation {
    List<VerifiableCredential> getCredentials();
}
