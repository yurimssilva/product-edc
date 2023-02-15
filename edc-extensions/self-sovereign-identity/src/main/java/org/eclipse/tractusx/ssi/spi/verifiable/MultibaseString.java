package org.eclipse.tractusx.ssi.spi.verifiable;

import lombok.EqualsAndHashCode;
import lombok.NonNull;


public interface MultibaseString {
  @NonNull
  byte[] getDecoded();

  @NonNull
  String getEncoded();

  default MultibaseString getInstance(String instance){
    return MultibaseFactory.create(instance);
  }
}
