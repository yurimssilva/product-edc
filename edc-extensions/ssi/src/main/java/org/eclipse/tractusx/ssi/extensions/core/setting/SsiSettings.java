package org.eclipse.tractusx.ssi.extensions.core.setting;

import lombok.Value;
import org.eclipse.tractusx.ssi.spi.did.Did;

@Value
public class SsiSettings {
  String verifiablePresentationSigningMethod;
  String walletIdentifier;
  Did didDataspaceOperator;
  Did didConnector;
  String verifiablePresentationSigningKeyAlias;
}
