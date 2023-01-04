package org.eclipse.tractusx.ssi.spi;

import java.net.URI;
import java.util.List;

public interface VerifiableCredential {
    List<String> getTypes();
    URI getIssuer();
}
