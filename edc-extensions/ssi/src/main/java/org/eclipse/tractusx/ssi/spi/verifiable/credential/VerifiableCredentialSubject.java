package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import lombok.Value;

import java.util.Map;

@Value
public class VerifiableCredentialSubject {
    Map<String, Object> claims;
}
