package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;

@Value
@Builder
public class VerifiableCredential {
  @NonNull URI id;
  @NonNull List<String> types; // TODO must be at least one
  @NonNull URI issuer;
  @NonNull URI holder;

  /**
   * This specification defines the <a
   * href="https://www.w3.org/TR/vc-data-model/#issuance-date">issuanceDate</a> property for
   * expressing the date and time when a credential becomes valid.
   */
  @NonNull Date issuanceDate;

  Date expirationDate;
  VerifiableCredentialStatus status;
  Ed25519Proof proof;

  @NonNull @Builder.Default public Map<String, Object> claims = new HashMap<>();
}