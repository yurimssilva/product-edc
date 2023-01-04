package org.eclipse.tractusx.ssi.setting;

import lombok.Value;

@Value
public class SsiSettings {
    String didWebHost;
    String didPrivateKey;
    String didPublicKey;
}
