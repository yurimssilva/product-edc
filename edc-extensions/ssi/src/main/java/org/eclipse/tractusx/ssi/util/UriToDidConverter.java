package org.eclipse.tractusx.ssi.util;

import org.eclipse.tractusx.ssi.resolver.Did;

import java.net.URI;

public class UriToDidConverter {

    public static Did convert(URI uri) {
        if (!uri.getScheme().equals("did"))
            throw new IllegalArgumentException("URI is not a DID");

        var parts = uri.toString().split(":");
        if (parts.length != 3) {
            throw new RuntimeException("DID does not contain of three parts split by ':'");
        }

        return new Did(parts[1], parts[2]);
    }
}
