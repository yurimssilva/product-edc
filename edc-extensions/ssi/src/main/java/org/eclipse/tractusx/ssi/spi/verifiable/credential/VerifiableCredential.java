package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@Builder
@ToString
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifiableCredential {
  @NonNull
  URI id;
  @JsonProperty("type")
  List<String> types; // TODO must be at least one
  @NonNull
  URI issuer;
  // @NonNull URI holder; TODO postponed until clafirication with danubtech
  // library, what to do without holder?
  /**
   * This specification defines the <a
   * href="https://www.w3.org/TR/vc-data-model/#issuance-date">issuanceDate</a>
   * property for
   * expressing the date and time when a credential becomes valid.
   */
  @NonNull
  Date issuanceDate;

  Date expirationDate;
  VerifiableCredentialStatus credentialStatus;
  Ed25519Proof proof;

  @NonNull
  @Builder.Default
  @JsonProperty("credentialSubject")
  public Map<String, Object> claims = new HashMap<>();

  @SneakyThrows
  public String toJson(){
    ObjectMapper om = new ObjectMapper();
    return om.writeValueAsString(this);
  }
}
