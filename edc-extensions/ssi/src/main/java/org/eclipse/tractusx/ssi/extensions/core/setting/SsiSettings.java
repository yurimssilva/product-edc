package org.eclipse.tractusx.ssi.extensions.core.setting;

import lombok.Value;
import org.eclipse.tractusx.ssi.extensions.core.resolver.DidImpl;

@Value
public class SsiSettings {
    String walletIdentifier;
    DidImpl didDataspaceOperator;
    DidImpl didConnector;
    byte[] didPrivateKey;
}
