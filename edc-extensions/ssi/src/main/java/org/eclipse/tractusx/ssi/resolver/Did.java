package org.eclipse.tractusx.ssi.resolver;

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

    @Override
    public String toString() {
        return String.format("urn:%s:%s", method, methodIdentifier);
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
