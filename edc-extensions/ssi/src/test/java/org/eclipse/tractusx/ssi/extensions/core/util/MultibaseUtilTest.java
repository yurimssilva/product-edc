package org.eclipse.tractusx.ssi.extensions.core.util;

import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MultibaseUtilTest {

    private static final String BASE_64 = "oH2hXjs6mEelLJhUBDeJ9Q==";
    private static final String BASE_58 = "5R5ANNcrsf9HHTRd5Eq6RYW";

    @Test
    public void testFromBase64() {
        final MultibaseString mbs = MultibaseUtil.fromBase64(BASE_64);

        Assertions.assertEquals(BASE_58, mbs.getBase58(), "Base58 not equal");
        Assertions.assertEquals(BASE_64, mbs.getBase64(), "Base64 not equal");
    }

    @Test
    public void testFromBase58() {

        final MultibaseString mbs = MultibaseUtil.fromBase58(BASE_58);

        Assertions.assertEquals(BASE_58, mbs.getBase58(), "Base58 not equal");
        Assertions.assertEquals(BASE_64, mbs.getBase64(), "Base64 not equal");
    }
}
