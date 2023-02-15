package org.eclipse.tractusx.ssi.extensions.core.wallet;

import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWalletRegistry;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWalletService;

public class VerifiableCredentialWalletServiceImpl implements VerifiableCredentialWalletService {

  private final VerifiableCredentialWalletRegistry registry;
  private final SsiSettings settings;

  public VerifiableCredentialWalletServiceImpl(
      VerifiableCredentialWalletRegistry registry, SsiSettings settings) {
    this.registry = registry;
    this.settings = settings;
  }

  @Override
  public VerifiableCredentialWallet getWallet() {
    return registry.get(settings.getWalletIdentifier());
  }
}
