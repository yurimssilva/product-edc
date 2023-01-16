package org.eclipse.tractusx.ssi.spi.verifiable;

import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;


@Value
public class MultibaseString {

    public MultibaseString(@NotNull String base58, @NotNull String base64) {

        if (!base58.startsWith("z")) {
            throw new IllegalArgumentException(String.format("Expecting base58 string '%s' to be base58 bitcoin encoded", base58));
        }
        if (!base64.startsWith("M")) {
            throw new IllegalArgumentException(String.format("Expecting base64 string '%s' to be base64 MIME encoded [RFC4648]", base64));
        }

        this.base58 = base58;
        this.base64 = base64;
    }

    @NonNull String base58;
    @NonNull String base64;
}
