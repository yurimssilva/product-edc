package org.eclipse.tractusx.ssi.spi.verifiable.credential;

import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;

import java.net.URI;
import java.util.Date;
import java.util.List;

@Value
public class VerifiableCredential {
    @NonNull List<String> types; // TODO must be at least one
    @NonNull URI issuer;

    /**
     * This specification defines the <a href="https://www.w3.org/TR/vc-data-model/#issuance-date">issuanceDate</a> property for expressing the date and time when a credential becomes valid.
     */
    @NonNull Date issuanceDate;
    Date expirationDate;
    VerifiableCredentialStatus status;
    Ed25519Proof proof;
}
