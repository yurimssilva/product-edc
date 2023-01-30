package org.eclipse.tractusx.ssi.extensions.core.setting;

import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.tractusx.ssi.extensions.core.SsiCoreExtension;
import org.eclipse.tractusx.ssi.extensions.core.exception.DidParseException;
import org.eclipse.tractusx.ssi.extensions.core.exception.SsiSettingException;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtFactory;
import org.eclipse.tractusx.ssi.spi.did.DidParser;
import org.eclipse.tractusx.ssi.spi.did.Did;

public class SsiSettingsFactoryImpl implements SsiSettingsFactory {

  private static final String EXCEPTION_NO_VALID_DID =
      "SSI Settings: No valid DID in configured for %s. Was %s";
  private static final String EXCEPTION_SIGNING_METHOD_NOT_SUPPORTED =
      "SSI Settings: Verifiable Presentation Signing Method '%s' from setting '%s' is not supported. Please use supported signing method: %s";
  private static final String EXCEPTION_MANDATORY_SETTINGS_MISSING =
      "SSI Settings: Configuration of %s is mandatory";
  private static final String EXCEPTION_CANNOT_DECODE_PRIVATE_KEY =
      "SSI Settings: No valid private key configured.";
  private static final String WARNING_NO_DID_CONFIGURED =
      "SSI Settings: No DID configured. Using (invalid) default DID: %s"; // TODO split for operator
  // and connector

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

    final String didConnectorString =
        context.getSetting(
            SsiCoreExtension.SETTING_DID_CONNECTOR, SsiCoreExtension.SETTING_DID_DEFAULT);
    if (didConnectorString.equals(SsiCoreExtension.SETTING_DID_DEFAULT)) {
      monitor.warning(
          String.format(WARNING_NO_DID_CONFIGURED, SsiCoreExtension.SETTING_DID_DEFAULT));
    }

    final Did didConnector;
    try {
      didConnector = DidParser.parse(didConnectorString);
    } catch (DidParseException e) {
      throw new SsiSettingException(
          String.format(
              EXCEPTION_NO_VALID_DID, SsiCoreExtension.SETTING_DID_CONNECTOR, didConnectorString),
          e);
    }

    final String didOperatorString =
        context.getSetting(
            SsiCoreExtension.SETTING_DID_OPERATOR, SsiCoreExtension.SETTING_DID_DEFAULT);
    if (didOperatorString.equals(SsiCoreExtension.SETTING_DID_DEFAULT)) {
      monitor.warning(
          String.format(WARNING_NO_DID_CONFIGURED, SsiCoreExtension.SETTING_DID_DEFAULT));
    }

    final Did didOperator;
    try {
      didOperator = DidParser.parse(didOperatorString);
    } catch (DidParseException e) {
      throw new SsiSettingException(
          String.format(
              EXCEPTION_NO_VALID_DID, SsiCoreExtension.SETTING_DID_OPERATOR, didConnectorString),
          e);
    }

    String verifiablePresentationSigningKeyAlias =
        context.getSetting(
            SsiCoreExtension.SETTING_VERIFIABLE_PRESENTATION_SIGNING_KEY_ALIAS, null);
    if (verifiablePresentationSigningKeyAlias == null) {
      throw new SsiSettingException(
          String.format(
              EXCEPTION_MANDATORY_SETTINGS_MISSING,
              SsiCoreExtension.SETTING_VERIFIABLE_PRESENTATION_SIGNING_KEY_ALIAS));
    }

    final String walletIdentifier = context.getSetting(SsiCoreExtension.SETTINGS_WALLET, null);
    if (walletIdentifier == null) {
      throw new SsiSettingException(
          String.format(EXCEPTION_MANDATORY_SETTINGS_MISSING, SsiCoreExtension.SETTINGS_WALLET));
    }

    final String verifiablePresentationSigningMethod =
        context.getSetting(
            SsiCoreExtension.SETTING_VERIFIABLE_PRESENTATION_SIGNING_METHOD,
            SsiCoreExtension.SETTING_VERIFIABLE_PRESENTATION_SIGNING_METHOD_DEFAULT);
    if (!SignedJwtFactory.SUPPORTED_SIGNING_METHODS.contains(verifiablePresentationSigningMethod)) {
      throw new SsiSettingException(
          String.format(
              EXCEPTION_SIGNING_METHOD_NOT_SUPPORTED,
              verifiablePresentationSigningMethod,
              SsiCoreExtension.SETTING_VERIFIABLE_PRESENTATION_SIGNING_METHOD,
              String.join(", ", SignedJwtFactory.SUPPORTED_SIGNING_METHODS)));
    }

    return new SsiSettings(
        verifiablePresentationSigningMethod,
        walletIdentifier,
        didOperator,
        didConnector,
        verifiablePresentationSigningKeyAlias);
  }
}
