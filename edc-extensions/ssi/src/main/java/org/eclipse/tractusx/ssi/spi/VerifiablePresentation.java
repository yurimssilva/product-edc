package org.eclipse.tractusx.ssi.spi;

import java.util.List;

public interface VerifiablePresentation {
    List<VerifiableCredential> getVerifiableCredential();
}
