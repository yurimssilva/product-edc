package org.eclipse.tractusx.ssi.spi.wallet;

public interface VerifiableCredentialWalletRegistry {

    VerifiableCredentialWallet get(String walletIdentifier);
    void register(VerifiableCredentialWallet wallet);

    void unregister(VerifiableCredentialWallet wallet);
}
