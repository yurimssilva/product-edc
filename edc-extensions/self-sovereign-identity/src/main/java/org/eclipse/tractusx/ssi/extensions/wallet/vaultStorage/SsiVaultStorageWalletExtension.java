package org.eclipse.tractusx.ssi.extensions.wallet.vaultStorage;

import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWalletRegistry;

public class SsiVaultStorageWalletExtension implements ServiceExtension {

  public static final String EXTENSION_NAME = "Vault Storage Wallet Extension";

  @Inject
  private Vault vault;
  @Inject private SsiSettings settings;

  private ServiceExtensionContext context;

  @Override
  public String name() {
    return EXTENSION_NAME;
  }

  @Override
  public void start() {
      // TODO Check if credentials from settings are in the vault
  }

  @Override
  public void initialize(ServiceExtensionContext context) {
    this.context = context;
    final VerifiableCredentialWallet wallet = new SsiVaultStorageWallet(vault, settings);

    VerifiableCredentialWalletRegistry walletRegistry =
            context.getService(VerifiableCredentialWalletRegistry.class);
    walletRegistry.register(wallet);
  }
}
