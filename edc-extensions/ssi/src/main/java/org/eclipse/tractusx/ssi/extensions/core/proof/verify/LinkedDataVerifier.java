package org.eclipse.tractusx.ssi.extensions.core.proof.verify;

import lombok.NonNull;
import org.bouncycastle.math.ec.rfc8032.Ed25519;
import org.eclipse.tractusx.ssi.extensions.core.proof.hash.HashedLinkedData;
import org.eclipse.tractusx.ssi.extensions.core.util.DidParser;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.Ed25519VerificationKey2020;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;

import java.net.URI;

public class LinkedDataVerifier {

    public final DidDocumentResolver didDocumentResolver;

    public LinkedDataVerifier(DidDocumentResolver didDocumentResolver) {
        this.didDocumentResolver = didDocumentResolver;
    }

    public boolean verify(HashedLinkedData hashedLinkedData, VerifiableCredential credential) {

        final @NonNull URI issuer = credential.getIssuer();
        final Did issuerDid = DidParser.parse(issuer);
        final DidDocument document = didDocumentResolver.resolve(issuerDid);

        final Did verifiacationMethodId = credential.getProof().getVerificationMethod();
        final Ed25519VerificationKey2020 key = document.getVerificationMethods().stream()
                .filter(v -> v.getId().equals(verifiacationMethodId.toUri()))
                .map(Ed25519VerificationKey2020.class::cast)
                .findFirst()
                .orElseThrow();

        final MultibaseString publicKey = key.getPublicKeyMultibase();
        final MultibaseString signature = credential.getProof().getProofValue();

        final boolean signatureValid = Ed25519.verify(
                signature.getDecoded(), 0,
                publicKey.getDecoded(), 0,
                hashedLinkedData.getValue(),
                0, hashedLinkedData.getValue().length);


        return signatureValid;
    }
}
