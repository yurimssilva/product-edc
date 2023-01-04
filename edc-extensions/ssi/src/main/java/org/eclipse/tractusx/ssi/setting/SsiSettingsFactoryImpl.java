package org.eclipse.tractusx.ssi.setting;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.tractusx.ssi.SsiWebExtension;
import org.eclipse.tractusx.ssi.exception.SsiSettingException;

import java.net.URI;

public class SsiSettingsFactoryImpl implements SsiSettingsFactory {

    private static final String EXCEPTION_NO_VALID_DID = "SSI Settings: No valid DID in configured for %s. Was %s";
    private static final String EXCEPTION_KEY_CONFIG_MISSING = "SSI Settings: Configuration of %s or %s is mandatory";
    private static final String EXCEPTION_CANNOT_DECODE_PUBLIC_KEY = "SSI Settings: No valid public key configured.";
    private static final String EXCEPTION_CANNOT_DECODE_PRIVATE_KEY = "SSI Settings: No valid private key configured.";
    private static final String WARNING_NO_DID_CONFIGURED = "SSI Settings: No DID configured. Using (invalid) default DID: %s";

    private final Monitor monitor;
    private final Vault vault;
    private final ServiceExtensionContext context;

    public SsiSettingsFactoryImpl(Monitor monitor, Vault vault, ServiceExtensionContext context) {
        this.monitor = monitor;
        this.vault = vault;
        this.context = context;
    }

    @Override
    public SsiSettings createSettings() {

        String didPrivateKey = context.getSetting(SsiWebExtension.SETTING_DID_KEY_PRIVATE, null);
        if (didPrivateKey == null) {
            final String didPrivateKeyAlias = context.getSetting(SsiWebExtension.SETTING_DID_KEY_PRIVATE_ALIAS, null);
            if (didPrivateKeyAlias == null) {
                throw new SsiSettingException(String.format(EXCEPTION_KEY_CONFIG_MISSING, SsiWebExtension.SETTING_DID_KEY_PRIVATE, SsiWebExtension.SETTING_DID_KEY_PRIVATE_ALIAS));
            }

            didPrivateKey = vault.resolveSecret(didPrivateKeyAlias);
        }

        if (didPrivateKey == null || didPrivateKey.isEmpty()) {
            throw new SsiSettingException(String.format(EXCEPTION_KEY_CONFIG_MISSING, SsiWebExtension.SETTING_DID_KEY_PRIVATE, SsiWebExtension.SETTING_DID_KEY_PRIVATE_ALIAS));
        }
        // TODO verify 'didPrivateKey' is a valid key

        String didPublicKey = context.getSetting(SsiWebExtension.SETTING_DID_KEY_PUBLIC, null);
        if (didPublicKey == null) {
            final String didPublicKeyAlias = context.getSetting(SsiWebExtension.SETTING_DID_KEY_PUBLIC_ALIAS, null);
            if (didPublicKeyAlias == null) {
                throw new SsiSettingException(String.format(EXCEPTION_KEY_CONFIG_MISSING, SsiWebExtension.SETTING_DID_KEY_PUBLIC, SsiWebExtension.SETTING_DID_KEY_PUBLIC_ALIAS));
            }

            didPublicKey = vault.resolveSecret(didPublicKeyAlias);
        }

        if (didPublicKey == null || didPublicKey.isEmpty()) {
            throw new SsiSettingException(String.format(EXCEPTION_KEY_CONFIG_MISSING, SsiWebExtension.SETTING_DID_KEY_PUBLIC, SsiWebExtension.SETTING_DID_KEY_PUBLIC_ALIAS));
        }
        // TODO verify 'didPublicKey' is a valid key

        final String did = context.getSetting(SsiWebExtension.SETTING_DID, SsiWebExtension.SETTING_DID_DEFAULT);
        if (did.equals(SsiWebExtension.SETTING_DID_DEFAULT)) {
            monitor.warning(String.format(WARNING_NO_DID_CONFIGURED, SsiWebExtension.SETTING_DID_DEFAULT));
        }

        if (!did.startsWith("did:")) {
            throw new SsiSettingException(String.format(EXCEPTION_NO_VALID_DID, SsiWebExtension.SETTING_DID, did));
        }

        final URI didUri;
        try {
            didUri = URI.create(did);
        } catch (IllegalArgumentException e) {
            throw new SsiSettingException(String.format(EXCEPTION_NO_VALID_DID, SsiWebExtension.SETTING_DID, did), e);
        }

        byte[] decodedPrivateKey;
        byte[] decodedPublicKey;
        try {
            decodedPrivateKey = Hex.decodeHex(didPrivateKey);
        } catch (DecoderException e) {
            throw new SsiSettingException(EXCEPTION_CANNOT_DECODE_PRIVATE_KEY);
        }


        try {
            decodedPublicKey = Hex.decodeHex(didPublicKey);
        } catch (DecoderException e) {
            throw new SsiSettingException(EXCEPTION_CANNOT_DECODE_PUBLIC_KEY);
        }

        return new SsiSettings(didUri, decodedPrivateKey, decodedPublicKey);
    }
}
