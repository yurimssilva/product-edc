package org.eclipse.tractusx.ssi.store;


import com.danubetech.verifiablecredentials.VerifiableCredential;

public interface VerifiableCredentialStore {
    VerifiableCredential GetMembershipCredential();
}
