package org.eclipse.tractusx.ssi.setting;

import lombok.Value;

import java.net.URI;

@Value
public class SsiSettings {
    URI did;
    byte[] didPrivateKey;
    byte[] didPublicKey;
}
