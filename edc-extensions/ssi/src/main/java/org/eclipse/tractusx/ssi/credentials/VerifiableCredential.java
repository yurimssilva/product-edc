package org.eclipse.tractusx.ssi.credentials;

import java.util.List;

public interface VerifiableCredential {

    String resolveJson();
    List<String> getTypes();
}
