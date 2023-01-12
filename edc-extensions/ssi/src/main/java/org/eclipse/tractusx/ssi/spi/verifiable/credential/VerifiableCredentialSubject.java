package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import java.util.Map;

public interface VerifiableCredentialSubject {
    Map<String, Object> getClaims();
}
