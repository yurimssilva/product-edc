package org.eclipse.tractusx.ssi.spi.verifiable.presentation;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;

import java.net.URI;
import java.util.List;


@Value
@Builder
public class VerifiablePresentation {
    @NonNull URI id;
    @NonNull List<String> types;
    @NonNull URI holder;
    @NonNull List<VerifiableCredential> verifiableCredentials;
    Ed25519Proof proof;
}
