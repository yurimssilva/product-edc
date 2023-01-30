package org.eclipse.tractusx.ssi.extensions.wallet.vaultStorage;

import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.tractusx.ssi.extensions.wallet.fileSystem.SsiFileSystemWallet;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWalletRegistry;

import java.nio.file.Path;

public class SsiVaultStorageWalletExtension implements ServiceExtension {

  public static final String EXTENSION_NAME = "Vault Storage Wallet Extension";

  public static final String SETTINGS_WALLET_PATH = "edc.ssi.wallet.credential.vault.basepath";

  private ServiceExtensionContext context;

  @Override
  public String name() {
    return EXTENSION_NAME;
  }

  @Override
  public void start() {

  }

  @Override
  public void initialize(ServiceExtensionContext context) {
    this.context = context;

    final Path credentialsPath = getCredentialsPath();
    final VerifiableCredentialWallet wallet = new SsiFileSystemWallet(credentialsPath);

    VerifiableCredentialWalletRegistry walletRegistry =
            context.getService(VerifiableCredentialWalletRegistry.class);
    walletRegistry.register(wallet);
  }

  private Path getCredentialsPath() {
    return null;
  }
}
