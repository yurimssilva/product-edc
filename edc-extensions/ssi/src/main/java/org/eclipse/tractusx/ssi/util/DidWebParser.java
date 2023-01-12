package org.eclipse.tractusx.ssi.util;

import org.eclipse.tractusx.ssi.exception.DidParseException;
import org.eclipse.tractusx.ssi.resolver.Did;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DidWebParser {

    private static String didPath = "/wellkknown/did.json";

    public static Did parse(String did) {

        String[] didParts = did.split(":");

        if (didParts.length < 3) {
            throw new DidParseException("DID does not contain of three parts split by" + did + "'");
        }

        if(didParts[0].equals("did")){
            throw new DidParseException("DID does not contain the correct did identifier" + did + "'");
        }

        if(didParts[1].equals("web")){
            throw new DidParseException("DID does not contain the correct method name" + did + "'");
        }

        if(didParts.length > 3){
            // did:web:example.com:user:alice
            String[] urlParts = Arrays.copyOfRange(didParts, 2, didParts.length);
            String didUrl = String.join("/", urlParts);
            return new Did(didParts[1], didUrl + didPath);
        } else if (didParts.length == 3){
            // did:web:example.com
            // did:web:localhost%3A8443
            String didUrl = didParts[2];
            String decodedDidUrl = java.net.URLDecoder.decode(didUrl, StandardCharsets.UTF_8);
            return new Did(didParts[1], decodedDidUrl + didPath);
        } else {
            throw new RuntimeException();
        }
    }
    /***
     *         String did = "did:web:example.com"; -> www.example.com/wellknown/did.json
     *         String didLong = "did:web:example.com:user:alice"; www.example.com/user/alice/wellknown/did.json
     *         String didWithPort= "did:web:localhost%3A8443"; www.localhost:8443/wellknown/did.json
     */
}
