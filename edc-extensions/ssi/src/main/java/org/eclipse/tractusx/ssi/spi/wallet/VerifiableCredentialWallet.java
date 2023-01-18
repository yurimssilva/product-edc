package org.eclipse.tractusx.ssi.spi.wallet;

import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;

public interface VerifiableCredentialWallet {
  String getIdentifier();

  VerifiableCredential getMembershipCredential();
}
