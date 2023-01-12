package org.eclipse.tractusx.ssi.spi.did;

import java.net.URI;

public interface Did {
    String getMethod();

    String getMethodIdentifier();

    URI toUri();
}
