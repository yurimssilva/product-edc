package org.eclipse.tractusx.ssi.spi.verifiable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.tractusx.ssi.extensions.core.base.MultibaseFactory;

import java.net.URI;
import java.util.Date;

@Value
@Builder
@ToString
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
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
  String proofValue;
  MultibaseString proofValueMultiBase;
  public MultibaseString getProofValueMultiBase(){
    return MultibaseFactory.create(proofValue);
  }
}
