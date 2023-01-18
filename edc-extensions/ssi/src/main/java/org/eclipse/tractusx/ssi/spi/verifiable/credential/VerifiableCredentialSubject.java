package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import java.util.Map;
import lombok.Value;

@Value
public class VerifiableCredentialSubject {
  Map<String, Object> claims;
}
