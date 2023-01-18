package org.eclipse.tractusx.ssi.spi.did;

import java.net.URI;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DidDocument {
  @NonNull URI id;

  @NonNull List<PublicKey> publicKeys;
}
