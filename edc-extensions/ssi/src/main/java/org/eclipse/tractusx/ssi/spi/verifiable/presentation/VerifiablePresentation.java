package org.eclipse.tractusx.ssi.spi.verifiable.presentation;

import lombok.Getter;
import lombok.NonNull;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;

import java.util.List;


public class VerifiablePresentation {

    public VerifiablePresentation(List<VerifiableCredential> verifiableCredentials) {
        this.types = List.of(VerifiablePresentationType.VERIFIABLE_PRESENTATION);
        this.verifiableCredentials = verifiableCredentials;
        this.proof = null;
    }

    public VerifiablePresentation(List<VerifiableCredential> verifiableCredentials, Ed25519Proof proof) {
        this.types = List.of(VerifiablePresentationType.VERIFIABLE_PRESENTATION);
        this.verifiableCredentials = verifiableCredentials;
        this.proof = proof;
    }

    public VerifiablePresentation(List<String> types, List<VerifiableCredential> verifiableCredentials) {
        this.types = types;
        this.verifiableCredentials = verifiableCredentials;
        this.proof = null;
    }

    public VerifiablePresentation(List<String> types, List<VerifiableCredential> verifiableCredentials, Ed25519Proof proof) {
        this.types = types;
        this.verifiableCredentials = verifiableCredentials;
        this.proof = proof;
    }

    @Getter
    @NonNull List<String> types;
    @Getter
    @NonNull List<VerifiableCredential> verifiableCredentials;
    @Getter
    Ed25519Proof proof;
}
