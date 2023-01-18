package org.eclipse.tractusx.ssi.spi.did;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

import java.net.URI;

@Value
@Builder
public class Ed25519VerificationKey2020 implements PublicKey {
    public static final String TYPE = "Ed25519VerificationKey2020";

    @NonNull URI id;
    @NonNull URI controller;
    @Builder.Default
    @NonNull String didVerificationMethodType = TYPE;
    @NonNull MultibaseString publicKeyMultibase;
}
