package org.eclipse.tractusx.ssi.spi.did;

import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

import java.net.URI;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

@Value
@Builder
public class Ed25519VerificationKey2020 implements PublicKey {
  public static final String TYPE = "Ed25519VerificationKey2020";
  @NonNull URI id;
  @NonNull URI controller;
  @Builder.Default @NonNull String didVerificationMethodType = TYPE;
  @NonNull MultibaseString publicKeyMultibase;

  @Override
  @SneakyThrows
  public java.security.PublicKey getKey() {
    X509EncodedKeySpec spec =
            new X509EncodedKeySpec(publicKeyMultibase.getDecoded());
    KeyFactory kf = KeyFactory.getInstance("ECDSA", "BC");
    return kf.generatePublic(spec);
  }
}
