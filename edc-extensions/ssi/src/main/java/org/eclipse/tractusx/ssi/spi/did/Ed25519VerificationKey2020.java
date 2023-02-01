package org.eclipse.tractusx.ssi.spi.did;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.tractusx.ssi.extensions.core.base.MultibaseFactory;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

import java.net.URI;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ed25519VerificationKey2020 implements PublicKey {
  public static final String TYPE = "Ed25519VerificationKey2020";
  @NonNull URI id;
  @NonNull URI controller;
  @Builder.Default @NonNull String didVerificationMethodType = TYPE;
  @NonNull String publicKeyMultibase;

  @Override
  @SneakyThrows
  public java.security.PublicKey getKey() {
    X509EncodedKeySpec spec =
            new X509EncodedKeySpec(getMultibaseString().getDecoded());
    KeyFactory kf = KeyFactory.getInstance("ECDSA", "BC");
    return kf.generatePublic(spec);
  }

  @Override
  public MultibaseString getMultibaseString() {
    MultibaseString publicKeyMultibase = MultibaseFactory.create(getPublicKeyMultibase());
    return publicKeyMultibase;
  }
}
