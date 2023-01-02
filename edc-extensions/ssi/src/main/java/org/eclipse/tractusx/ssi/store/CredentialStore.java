package org.eclipse.tractusx.ssi.store;

import org.eclipse.tractusx.ssi.credentials.VerifiableCredential;

import java.util.List;

public interface CredentialStore {
    public List<VerifiableCredential> GetAll();
    public List<VerifiableCredential> Get(String type);
}
