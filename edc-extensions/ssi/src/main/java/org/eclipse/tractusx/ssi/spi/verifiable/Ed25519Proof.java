package org.eclipse.tractusx.ssi.spi.verifiable;

import lombok.Value;
import org.eclipse.tractusx.ssi.spi.did.Did;

import java.util.Date;

@Value
public class Ed25519Proof implements Proof {
    String type = "Ed25519Signature2020";
    Date created;
    String proofPurpose = "assertionMethod";
    Did verificationMethod;
    String proofValue;
}
