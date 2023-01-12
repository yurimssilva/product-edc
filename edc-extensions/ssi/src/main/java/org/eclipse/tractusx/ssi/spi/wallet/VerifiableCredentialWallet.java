package org.eclipse.tractusx.ssi.spi.wallet;


import com.danubetech.verifiablecredentials.VerifiableCredential;

public interface VerifiableCredentialWallet {
    String getIdentifier();
    VerifiableCredential GetMembershipCredential();
}
