package org.eclipse.tractusx.ssi.spi.verifiable.presentation;

import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;

import java.util.List;

public interface VerifiablePresentation {
    List<VerifiableCredential> getVerifiableCredential();
}
