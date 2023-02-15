package org.eclipse.tractusx.ssi.extensions.core.setting;

import lombok.Value;
import org.eclipse.tractusx.ssi.spi.did.Did;

import java.util.List;

@Value
public class SsiSettings {
  String verifiablePresentationSigningMethod;
  String walletIdentifier;
  Did didDataspaceOperator;
  Did didConnector;
  String verifiablePresentationSigningKeyAlias;
  String membershipVerifiableCredentialAlias;
  List<String> storedCredentialAliases;
}
