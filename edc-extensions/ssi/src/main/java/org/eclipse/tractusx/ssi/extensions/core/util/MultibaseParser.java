package org.eclipse.tractusx.ssi.extensions.core.util;

import io.ipfs.multibase.Multibase;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

public class MultibaseParser {
    public static MultibaseString parse(String base64String) {
        byte[] decoded = Multibase.decode(base64String);
        final String base58 = Multibase.encode(Multibase.Base.Base58BTC, decoded);
        final String base64 = Multibase.encode(Multibase.Base.Base64Pad, decoded);

        return new MultibaseString(base58, base64);
    }
}
