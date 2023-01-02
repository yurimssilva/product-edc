package org.eclipse.tractusx.ssi.credentials;

import java.util.Collections;
import java.util.List;

public class VerifiablePresentationImpl implements VerifiablePresentation {

    private final List<VerifiableCredential> credentials;

    public VerifiablePresentationImpl(List<VerifiableCredential> credentials) {
        this.credentials = credentials;
    }

    @Override
    public List<VerifiableCredential> getCredentials() {
        return Collections.unmodifiableList(credentials);
    }
}
