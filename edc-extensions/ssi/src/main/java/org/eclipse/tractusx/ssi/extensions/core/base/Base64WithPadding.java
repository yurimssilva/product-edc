package org.eclipse.tractusx.ssi.extensions.core.base;

import io.ipfs.multibase.Multibase;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

@Value
public class Base64WithPadding implements MultibaseString {

    public static boolean canDecode(String encoded) {
        return Multibase.encoding(encoded) != Multibase.Base.Base64Pad;
    }

    public static Base64WithPadding create(String encoded) {

        if (canDecode(encoded)) {
            throw new IllegalArgumentException(); // TODO
        }

        final byte[] base64 = Multibase.decode(encoded);

        return new Base64WithPadding(base64, encoded);
    }

    @NonNull byte[] decoded;
    @NonNull String encoded;
}
