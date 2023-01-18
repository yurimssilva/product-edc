package org.eclipse.tractusx.ssi.spi.verifiable;

import java.net.URI;
import java.util.Date;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class Ed25519Proof {

  public static final String TYPE = "Ed25519Signature2020";

  @NonNull
  @Builder.Default
  String type = "Ed25519Signature2020";

  @NonNull
  @Builder.Default
  String proofPurpose = "assertionMethod";

  @NonNull
  Date created;
  @NonNull
  URI verificationMethod;
  @NonNull
  MultibaseString proofValue;
}
