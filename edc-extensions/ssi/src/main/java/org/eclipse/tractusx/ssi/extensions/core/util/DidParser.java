package org.eclipse.tractusx.ssi.extensions.core.util;

import org.eclipse.tractusx.ssi.extensions.core.exception.DidParseException;
import org.eclipse.tractusx.ssi.extensions.core.resolver.DidImpl;

import java.net.URI;

public class DidParser {

    public static DidImpl parse(URI uri) {
        if (!uri.getScheme().equals("did"))
            throw new DidParseException("URI is not a DID. URI: '" + uri + "'");

        var parts = uri.toString().split(":");
        if (parts.length != 3) {
            throw new DidParseException("DID does not contain of three parts split by ':'. URI: '" + uri + "'");
        }

        return new DidImpl(parts[1], parts[2]);
    }

    public static DidImpl parse(String did) {
        final URI uri;
        try {
            uri = URI.create(did);
        } catch (Exception e) {
            throw new DidParseException("Not able to create DID URI from string: " + did, e);
        }

        return parse(uri);
    }
}
