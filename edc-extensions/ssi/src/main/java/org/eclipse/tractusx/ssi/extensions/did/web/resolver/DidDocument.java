package org.eclipse.tractusx.ssi.extensions.did.web.resolver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.net.URI;
import java.util.List;

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
class Ed25519VerificationKey2020 implements PublicKey {

  @JsonProperty URI id;

  @JsonProperty String type;

  @JsonProperty String publicKeyMultibase;

  @JsonProperty URI controller;
}
