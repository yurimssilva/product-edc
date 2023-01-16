package org.eclipse.tractusx.ssi.spi.verifiable;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.did.Did;

import java.util.Date;

@Value
@Builder
public class Ed25519Proof {

    @NonNull
    @Builder.Default
    String type = "Ed25519Signature2020";

    @NonNull
    @Builder.Default
    String proofPurpose = "assertionMethod";

    @NonNull
    Date created;
    @NonNull
    Did verificationMethod;
    @NonNull
    MultibaseString proofValue;
}
