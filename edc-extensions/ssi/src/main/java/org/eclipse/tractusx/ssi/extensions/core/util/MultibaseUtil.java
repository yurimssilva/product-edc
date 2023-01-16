package org.eclipse.tractusx.ssi.extensions.core.util;

import io.ipfs.multibase.Multibase;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

import java.util.*;

public class MultibaseUtil {

    private static final char[] BASE_58_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();

    // TODO Finish Impl
    // reference https://github.com/bitcoinj/bitcoinj/blob/66b0cc832499b561b730639f9ad3d6d6835e5e44/core/src/main/java/org/bitcoinj/base/Base58.java

    public static MultibaseString fromBase64(String base64) {
        return new MultibaseString(MultibaseUtil.toBase58(base64), base64);
    }

    public static MultibaseString fromBase58(String basel58) {
        return new MultibaseString(basel58, MultibaseUtil.toBase64(basel58));
    }

    private static String toBase58(String base64String) {


        //String encoded = Multibase.encode(Multibase.Base.Base58BTC, data);

        final byte[] base64Characters = Base64.getDecoder().decode(base64String);

        // first in last out
        final char[] base58Chars = new char[base64Characters.length];

        // TODO Handle Leading Zeros

        for (var i = 0; i < base64Characters.length; i++) {
            final char nextChar = BASE_58_ALPHABET[divmod(base64Characters, i, 64, 58)];
            base58Chars[base64Characters.length - i - 1] = nextChar;
        }

        return new String(base58Chars);
    }

    public static String toBase64(String base58String) {

        return "";
    }

    private static byte divmod(byte[] number, int firstDigit, int base, int divisor) {
        // this is just long division which accounts for the base of the input digits
        int remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            int digit = (int) number[i] & 0xFF;
            int temp = remainder * base + digit;
            number[i] = (byte) (temp / divisor);
            remainder = temp % divisor;
        }
        return (byte) remainder;
    }

}
