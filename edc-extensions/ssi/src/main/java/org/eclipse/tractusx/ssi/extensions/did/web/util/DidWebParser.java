package org.eclipse.tractusx.ssi.extensions.did.web.util;

import org.eclipse.tractusx.ssi.spi.did.Did;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class DidWebParser {

    private static String didPath = "/.well-known/did.json";

    public static URI parse(Did did) {
        if (did.getMethod().equals(Constants.DID_WEB_METHOD)) {
            throw new RuntimeException("TODO");
        }

        String didUrl = did.getMethodIdentifier().getValue();
        didUrl = didUrl.replace(':', '/');
        didUrl = java.net.URLDecoder.decode(didUrl, StandardCharsets.UTF_8);

        return URI.create("https://" + didUrl + didPath); // TODO Escape URL better
    }
}
