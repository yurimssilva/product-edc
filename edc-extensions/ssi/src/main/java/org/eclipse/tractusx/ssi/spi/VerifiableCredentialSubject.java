package org.eclipse.tractusx.ssi.spi;

import java.util.Map;

public interface VerifiableCredentialSubject {
    Map<String, Object> getClaims();
}
