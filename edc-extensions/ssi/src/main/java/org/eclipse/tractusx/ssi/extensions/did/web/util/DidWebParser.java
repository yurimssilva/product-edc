package org.eclipse.tractusx.ssi.extensions.did.web.util;

import org.eclipse.tractusx.ssi.spi.did.Did;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DidWebParser {

    private static String didPath = "/.well-known/did.json";

    public static URL parse(Did did) {
        if (did.getMethod().equals(Constants.DID_WEB_METHOD)) {
            throw new RuntimeException("TODO");
        }

        String didUrl = did.getMethodIdentifier().getValue();
        didUrl = didUrl.replace(':', '/');
        didUrl = java.net.URLDecoder.decode(didUrl, StandardCharsets.UTF_8);

        try {
            return new URL("https://" + didUrl + didPath); // TODO Escape URL better
        } catch (MalformedURLException e) {
            throw new RuntimeException(e); // TODO
        }
    }
}
