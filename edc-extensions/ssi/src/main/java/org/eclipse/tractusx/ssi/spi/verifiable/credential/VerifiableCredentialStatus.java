package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.net.URI;


@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifiableCredentialStatus {
  URI id;
  String type;
}
