package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Value
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifiableCredentialSubject {
  Map<String, Object> claims;
}
