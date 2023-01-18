package org.eclipse.tractusx.ssi.spi.did;

import java.net.URI;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

@Value
@Builder
public class Ed25519VerificationKey2020 implements PublicKey {
  public static final String TYPE = "Ed25519VerificationKey2020";

  @NonNull URI id;
  @NonNull URI controller;
  @Builder.Default @NonNull String didVerificationMethodType = TYPE;
  @NonNull MultibaseString publicKeyMultibase;
}
