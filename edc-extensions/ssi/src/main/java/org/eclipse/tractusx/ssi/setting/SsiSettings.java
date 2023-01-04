package org.eclipse.tractusx.ssi.setting;

import lombok.Value;
import org.eclipse.tractusx.ssi.resolver.Did;

@Value
public class SsiSettings {
    Did didDataspaceOperator;
    Did didConnector;
    byte[] didPrivateKey;
    byte[] didPublicKey;
}
