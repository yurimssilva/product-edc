package org.eclipse.tractusx.ssi.extensions.wallet.fileSystem;

import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWalletRegistry;

import java.nio.file.Path;

public class SsiFileSystemWalletExtension implements ServiceExtension {

    public static final String EXTENSION_NAME = "File System Wallet Extension";

    public static final String SETTINGS_WALLET_PATH = "edc.ssi.wallet.credential.filepath";

    @Override
    public String name() {
        return EXTENSION_NAME;
    }

    @Override
    public void start() {
        // check whether path is directory
        // check whether json file exists / or is valid json
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        final Monitor monitor = context.getMonitor();

        final String credentialFilePath = context.getSetting(SETTINGS_WALLET_PATH, null);
        if (credentialFilePath == null) {
            throw new IllegalArgumentException("Mandatory setting not provided: " + SETTINGS_WALLET_PATH);
        }

        final VerifiableCredentialWallet wallet = new SsiFileSystemWallet(Path.of(credentialFilePath));

        VerifiableCredentialWalletRegistry walletRegistry = context.getService(VerifiableCredentialWalletRegistry.class);
        walletRegistry.register(wallet);
    }
}
