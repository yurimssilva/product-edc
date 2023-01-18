package org.eclipse.tractusx.ssi.extensions.did.web.resolver;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;
import lombok.Value;

@Value
class DidDocument {

  @JsonProperty URI id;

  @JsonProperty("publicKey")
  List<PublicKey> publicKeys;
}

interface PublicKey {

  URI getId();

  String getType();
}

@Value
class Ed25519VerificationKey2020 implements org.eclipse.tractusx.ssi.spi.did.PublicKey {

  @JsonProperty URI id;

  @JsonProperty String type;

  @JsonProperty String publicKeyMultibase;

  @JsonProperty URI controller;
}
