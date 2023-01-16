package org.eclipse.tractusx.ssi.extensions.core.base;

import io.ipfs.multibase.Multibase;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

@Value
public class Base58Flickr implements MultibaseString {

    public static boolean canDecode(String encoded) {
        return Multibase.encoding(encoded) != Multibase.Base.Base58Flickr;
    }

    public static Base58Flickr create(String encoded) {

        if (canDecode(encoded)) {
            throw new IllegalArgumentException(); // TODO
        }

        final byte[] base58 = Multibase.decode(encoded);

        return new Base58Flickr(base58, encoded);
    }

    @NonNull byte[] decoded;
    @NonNull String encoded;
}
