package org.eclipse.tractusx.ssi.extensions.wallet.vaultStorage;

import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;

public class SsiVaultStorageWallet implements VerifiableCredentialWallet {
  @Override
  public String getIdentifier() {
    return null;
  }

  @Override
  public VerifiableCredential getMembershipCredential() {
    return null;
  }
}
