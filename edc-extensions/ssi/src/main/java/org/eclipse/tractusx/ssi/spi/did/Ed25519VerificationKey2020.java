package org.eclipse.tractusx.ssi.spi.did;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

import java.net.URI;

@Value
@Builder
public class Ed25519VerificationKey2020 implements DidVerificationMethod {
    @NonNull URI id;
    @NonNull URI controller;
    @NonNull String didVerificationMethodType = "Ed25519VerificationKey2020";

    @NonNull MultibaseString publicKeyMultibase;
}
