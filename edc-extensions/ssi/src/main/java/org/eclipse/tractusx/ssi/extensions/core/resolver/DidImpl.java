package org.eclipse.tractusx.ssi.extensions.core.resolver;

import org.eclipse.tractusx.ssi.spi.did.Did;

import java.net.URI;
import java.util.Objects;

public class DidImpl implements Did {

    private final String method;
    private final String methodIdentifier;

    public DidImpl(String method, String methodIdentifier) {
        this.method = method;
        this.methodIdentifier = methodIdentifier;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getMethodIdentifier() {
        return methodIdentifier;
    }

    @Override
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
        if (obj instanceof DidImpl) {
            final DidImpl o = (DidImpl) obj;
            return Objects.equals(o.method, this.method) && Objects.equals(o.methodIdentifier, this.methodIdentifier);
        }

        return false;
    }
}
