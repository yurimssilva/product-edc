package org.eclipse.tractusx.ssi.spi.did;

import lombok.NonNull;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

import java.net.URI;

public interface DidVerificationMethod {
    URI getId();
}
