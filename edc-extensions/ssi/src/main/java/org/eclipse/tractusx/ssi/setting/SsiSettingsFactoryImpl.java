package org.eclipse.tractusx.ssi.setting;

import org.eclipse.edc.spi.security.Vault;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.tractusx.ssi.SsiWebExtension;

public class SsiSettingsFactoryImpl implements SsiSettingsFactory {

    private final Vault vault;
    private final ServiceExtensionContext context;

    public SsiSettingsFactoryImpl(Vault vault, ServiceExtensionContext context) {
        this.vault = vault;
        this.context = context;
    }

    @Override
    public SsiSettings createSettings() {

        String didPrivateKey = context.getSetting(SsiWebExtension.SETTING_DID_KEY_PRIVATE, null);
        if (didPrivateKey == null) {
            final String didPrivateKeyAlias = context.getSetting(SsiWebExtension.SETTING_DID_KEY_PRIVATE_ALIAS, null);
            if (didPrivateKeyAlias == null) {
                throw new RuntimeException(); // TODO
            }

            didPrivateKey = vault.resolveSecret(didPrivateKeyAlias);
        }

        // TODO verify 'didPrivateKey' is a valid key

        String didPublicKey = context.getSetting(SsiWebExtension.SETTING_DID_KEY_PUBLIC, null);
        if (didPublicKey == null) {
            final String didPublicKeyAlias = context.getSetting(SsiWebExtension.SETTING_DID_KEY_PUBLIC_ALIAS, null);
            if (didPublicKeyAlias == null) {
                throw new RuntimeException(); // TODO
            }

            didPublicKey = vault.resolveSecret(didPublicKeyAlias);
        }

        // TODO verify 'didPublicKey' is a valid key

        final String didWebHost = context.getSetting(SsiWebExtension.SETTING_DID_WEB_HOST, null);
        if (didWebHost == null) {
            throw new RuntimeException(); // TODO Mandatory for now, because this is the only possiblity to configure a DID
        }

        return new SsiSettings(didWebHost, didPrivateKey, didPublicKey);
    }
}
