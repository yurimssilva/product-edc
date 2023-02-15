package org.eclipse.tractusx.ssi.spi.verifiable;

public interface MultibaseFactory {
    static MultibaseString create(byte[] decoded) {
        return null;
    }

    static MultibaseString create(String encoded) {
        throw new IllegalArgumentException("Encoded Multibase String is not supported. Non of xxx");
    }
}
