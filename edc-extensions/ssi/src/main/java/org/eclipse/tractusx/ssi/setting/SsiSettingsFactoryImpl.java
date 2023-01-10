package org.eclipse.tractusx.ssi.setting;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.tractusx.ssi.SsiWebExtension;
import org.eclipse.tractusx.ssi.exception.DidParseException;
import org.eclipse.tractusx.ssi.exception.SsiSettingException;
import org.eclipse.tractusx.ssi.resolver.Did;
import org.eclipse.tractusx.ssi.util.DidParser;

public class SsiSettingsFactoryImpl implements SsiSettingsFactory {

    private static final String EXCEPTION_NO_VALID_DID = "SSI Settings: No valid DID in configured for %s. Was %s";
    private static final String EXCEPTION_KEY_CONFIG_MISSING = "SSI Settings: Configuration of %s or %s is mandatory";
    private static final String EXCEPTION_CANNOT_DECODE_PRIVATE_KEY = "SSI Settings: No valid private key configured.";
    private static final String WARNING_NO_DID_CONFIGURED = "SSI Settings: No DID configured. Using (invalid) default DID: %s"; // TODO split for operator and connector

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

        final String didConnectorString = context.getSetting(SsiWebExtension.SETTING_DID_CONNECTOR, SsiWebExtension.SETTING_DID_DEFAULT);
        if (didConnectorString.equals(SsiWebExtension.SETTING_DID_DEFAULT)) {
            monitor.warning(String.format(WARNING_NO_DID_CONFIGURED, SsiWebExtension.SETTING_DID_DEFAULT));
        }

        final Did didConnector;
        try {
            didConnector = DidParser.parse(didConnectorString);
        } catch (DidParseException e) {
            throw new SsiSettingException(String.format(EXCEPTION_NO_VALID_DID, SsiWebExtension.SETTING_DID_CONNECTOR, didConnectorString), e);
        }


        final String didOperatorString = context.getSetting(SsiWebExtension.SETTING_DID_OPERATOR, SsiWebExtension.SETTING_DID_DEFAULT);
        if (didOperatorString.equals(SsiWebExtension.SETTING_DID_DEFAULT)) {
            monitor.warning(String.format(WARNING_NO_DID_CONFIGURED, SsiWebExtension.SETTING_DID_DEFAULT));
        }

        final Did didOperator;
        try {
            didOperator = DidParser.parse(didOperatorString);
        } catch (DidParseException e) {
            throw new SsiSettingException(String.format(EXCEPTION_NO_VALID_DID, SsiWebExtension.SETTING_DID_OPERATOR, didConnectorString), e);
        }

        byte[] decodedPrivateKey;
        try {
            decodedPrivateKey = Hex.decodeHex(didPrivateKey);
        } catch (DecoderException e) {
            throw new SsiSettingException(EXCEPTION_CANNOT_DECODE_PRIVATE_KEY);
        }

        return new SsiSettings(didOperator, didConnector, decodedPrivateKey);
    }
}
