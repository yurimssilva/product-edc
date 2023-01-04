package org.eclipse.tractusx.ssi.setting;

import lombok.Value;
import org.eclipse.tractusx.ssi.resolver.Did;

@Value
public class SsiSettings {
    Did did;
    byte[] didPrivateKey;
    byte[] didPublicKey;
}
