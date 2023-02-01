package org.eclipse.tractusx.ssi.extensions.did.web.resolver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.tractusx.ssi.spi.did.Ed25519VerificationKey2020;
import org.eclipse.tractusx.ssi.spi.did.PublicKey;

import java.net.URI;
import java.util.List;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
class DidDocument {

  @JsonProperty URI id;

  @JsonProperty("publicKey")
  List<PublicKey> publicKeys;

  @JsonProperty("verificationMethod")
  List<Ed25519VerificationKey2020> verificationMethods;
}




