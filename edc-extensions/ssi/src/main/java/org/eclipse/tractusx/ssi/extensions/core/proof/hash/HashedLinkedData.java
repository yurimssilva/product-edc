package org.eclipse.tractusx.ssi.extensions.core.proof.hash;

import lombok.NonNull;
import lombok.Value;

@Value
public class HashedLinkedData {
  @NonNull byte[] value;
}
