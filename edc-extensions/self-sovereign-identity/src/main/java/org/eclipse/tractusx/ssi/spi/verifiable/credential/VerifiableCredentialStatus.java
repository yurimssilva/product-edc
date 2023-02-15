package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.net.URI;


@Value
@Builder
@EqualsAndHashCode
public class VerifiableCredentialStatus {
  URI id;
  String type;
}
