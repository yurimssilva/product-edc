package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.HashMap;
import java.util.Map;

@Value
@EqualsAndHashCode
public class VerifiableCredentialSubject extends HashMap<String, Object> {
}
