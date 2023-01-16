package org.eclipse.tractusx.ssi.spi.verifiable;

import lombok.NonNull;
import lombok.Value;


@Value
public class MultibaseString {
    @NonNull String base58;
    @NonNull String base64;
}
