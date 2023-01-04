package org.eclipse.tractusx.ssi.util;

import org.eclipse.tractusx.ssi.exception.DidParseException;
import org.eclipse.tractusx.ssi.resolver.Did;

import java.net.URI;

public class DidParser {

    public static Did parse(URI uri) {
        if (!uri.getScheme().equals("did"))
            throw new DidParseException("URI is not a DID. URI: '" + uri + "'");

        var parts = uri.toString().split(":");
        if (parts.length != 3) {
            throw new DidParseException("DID does not contain of three parts split by ':'. URI: '" + uri + "'");
        }

        return new Did(parts[1], parts[2]);
    }

    public static Did parse(String did) {
        final URI uri;
        try {
            uri = URI.create(did);
        } catch (Exception e) {
            throw new DidParseException("Not able to create DID URI from string: " + did, e);
        }

        return parse(uri);
    }
}
