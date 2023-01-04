package org.eclipse.tractusx.ssi.resolver;

import java.net.URI;
import java.util.Objects;

public class Did {

    private final String method;
    private final String methodIdentifier;

    public Did(String method, String methodIdentifier) {
        this.method = method;
        this.methodIdentifier = methodIdentifier;
    }

    public String getMethod() {
        return method;
    }

    public String getMethodIdentifier() {
        return methodIdentifier;
    }

    public URI toUri() {
        return URI.create(toString());
    }

    @Override
    public String toString() {
        return String.format("did:%s:%s", method, methodIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, methodIdentifier);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Did) {
            final Did o = (Did) obj;
            return Objects.equals(o.method, this.method) && Objects.equals(o.methodIdentifier, this.methodIdentifier);
        }

        return false;
    }
}
