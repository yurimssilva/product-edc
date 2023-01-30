package org.eclipse.tractusx.ssi.extensions.core.proof;

import org.eclipse.tractusx.ssi.extensions.core.base.MultibaseFactory;
import org.eclipse.tractusx.ssi.extensions.core.proof.hash.LinkedDataHasher;
import org.eclipse.tractusx.ssi.extensions.core.proof.transform.LinkedDataTransformer;
import org.eclipse.tractusx.ssi.extensions.core.proof.verify.LinkedDataSigner;
import org.eclipse.tractusx.ssi.extensions.core.proof.verify.LinkedDataVerifier;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;

import java.util.Date;

public class LinkedDataProofValidation {

  private final LinkedDataHasher hasher;
  private final LinkedDataTransformer transformer;
  private final LinkedDataVerifier verifier;
  private final LinkedDataSigner signer;

  public LinkedDataProofValidation(
      LinkedDataHasher hasher,
      LinkedDataTransformer transformer,
      LinkedDataVerifier verifier,
      LinkedDataSigner signer) {
    this.hasher = hasher;
    this.transformer = transformer;
    this.verifier = verifier;
    this.signer = signer;
  }

  public boolean checkProof(VerifiableCredential verifiableCredential) {
    // TODO Asser proof is linked data proof
    var transformedData = transformer.transform(verifiableCredential);
    var hashedData = hasher.hash(transformedData);
    var isProofed = verifier.verify(hashedData, verifiableCredential);

    return isProofed;
  }

  public Ed25519Proof createProof(
      VerifiableCredential verifiableCredential, Did verificationMethodId, byte[] signingKey) {
    var transformedData = transformer.transform(verifiableCredential);
    var hashedData = hasher.hash(transformedData);
    var signature = signer.sign(hashedData, signingKey);

    var proof =
        Ed25519Proof.builder()
            .created(new Date())
            .verificationMethod(verificationMethodId.toUri())
            .proofValueMultiBase(MultibaseFactory.create(signature))
            .build();

    return proof;
  }
}
