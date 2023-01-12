package org.eclipse.tractusx.ssi.resolver;

import java.net.URI;
import java.util.Objects;

public class Did {

    private final String method;
    private final String methodSpecifidId;

    //private final String didSpecificKey;

    //private final String didQuery;

    private final String docPath = "/.well-known/did.json";

    public Did(String method, String methodIdentifier) {
        this.method = method;
        this.methodSpecifidId = methodIdentifier;
        //this.didQuery = didQuery;
        //this.didSpecificKey = didSpecificKey;
    }

    public String getMethod() {
        return method;
    }

    public String getMethodSpecifidId() {
        return methodSpecifidId;
    }

    public URI toUri() {
        return URI.create(toString());
    }

    @Override
    public String toString() {
        return String.format("did:%s:%s", method, methodSpecifidId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, methodSpecifidId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Did) {
            final Did o = (Did) obj;
            return Objects.equals(o.method, this.method) && Objects.equals(o.methodSpecifidId, this.methodSpecifidId);
        }

        return false;
    }

    public static Did parseDid(String did){
        String method;
        String identifier;

        String parsedDid = did.

        return new Did();
    }
}
