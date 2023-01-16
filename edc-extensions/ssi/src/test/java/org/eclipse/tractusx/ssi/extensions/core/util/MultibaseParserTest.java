package org.eclipse.tractusx.ssi.extensions.core.util;

import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MultibaseParserTest {

    private static final String MULTIBASE_58_BITCOIN_IDENTIFIER = "z";
    private static final String MULTIBASE_64_PADDING_IDENTIFIER = "M";
    private static final String BASE_64 = MULTIBASE_64_PADDING_IDENTIFIER + "TXVsdGliYXNlIGlzIGF3ZXNvbWUhIFxvLw==";  // "Multibase is awesome! \o/"
    private static final String BASE_58 = MULTIBASE_58_BITCOIN_IDENTIFIER + "YAjKoNbau5KiqmHPmSxYCvn66dA1vLmwbt"; // "Multibase is awesome! \o/"

    @Test
    public void testFromBase64() {
        final MultibaseString mbs = MultibaseParser.parse(BASE_64);

        Assertions.assertEquals(BASE_58, mbs.getBase58(), "Base58 not equal");
        Assertions.assertEquals(BASE_64, mbs.getBase64(), "Base64 not equal");
    }

    @Test
    public void testFromBase58() {

        final MultibaseString mbs = MultibaseParser.parse(BASE_58);

        Assertions.assertEquals(BASE_58, mbs.getBase58(), "Base58 not equal");
        Assertions.assertEquals(BASE_64, mbs.getBase64(), "Base64 not equal");
    }
}
