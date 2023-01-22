package org.eclipse.tractusx.ssi.spi.did;

import java.net.URI;

public interface PublicKey {
  URI getId();

  java.security.PublicKey getKey();
}
