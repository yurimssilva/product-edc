package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"id", "contexts", "type", "issuer", "issuanceDate", "expirationDate", "credentialSubject", "credentialStatus", "proof"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifiableCredential {

  @JsonProperty("@context")
  @NonNull
  List<URI> contexts;

  @NonNull
  URI id;

  @JsonProperty("type")
  List<String> types;

  @NonNull
  URI issuer;

  @NonNull
  Date issuanceDate;

  Date expirationDate;

  @NonNull
  @Builder.Default
  @JsonProperty("credentialSubject")
  public Map<String, Object> claims = new HashMap<>();

  VerifiableCredentialStatus credentialStatus;

  String holderIdentifier;

  Ed25519Proof proof;

  @SneakyThrows
  public String toJson(){
    ObjectMapper om = new ObjectMapper();
    return om.writeValueAsString(this);
  }

}
