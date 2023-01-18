package org.eclipse.tractusx.ssi.extensions.core.jsonLd;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;
import info.weboftrust.ldsignatures.LdProof;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.PackagePrivate;
import org.eclipse.tractusx.ssi.extensions.core.base.MultibaseFactory;
import org.eclipse.tractusx.ssi.spi.verifiable.Ed25519Proof;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredentialStatus;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;

@PackagePrivate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DanubTechMapper {

  @NonNull
  public static com.danubetech.verifiablecredentials.VerifiablePresentation map(
      VerifiablePresentation presentation) {

    final List<com.danubetech.verifiablecredentials.VerifiableCredential> dtCredentials = presentation
        .getVerifiableCredentials().stream()
        .map(DanubTechMapper::map)
        .collect(Collectors.toList());

    // TODO Throw Exception if more or less than one

    com.danubetech.verifiablecredentials.VerifiablePresentation.Builder<? extends com.danubetech.verifiablecredentials.VerifiablePresentation.Builder<?>> builder = com.danubetech.verifiablecredentials.VerifiablePresentation
        .builder();

    return builder
        .defaultContexts(true)
        .forceContextsArray(true)
        .forceTypesArray(true)
        .id(presentation.getId())
        .types(presentation.getTypes())
        .holder(presentation.getHolder())
        .verifiableCredential(dtCredentials.get(0))
        .ldProof(null) // set to null, as presentation will be used within JWT
        .build();
  }

  @NonNull
  public static VerifiablePresentation map(
      com.danubetech.verifiablecredentials.VerifiablePresentation dtPresentation) {

    Objects.requireNonNull(dtPresentation);
    Objects.requireNonNull(dtPresentation.getVerifiableCredential());

    List<VerifiableCredential> credentials = List.of(map(dtPresentation.getVerifiableCredential()));

    return VerifiablePresentation.builder()
        .id(dtPresentation.getId())
        .types(dtPresentation.getTypes())
        .verifiableCredentials(credentials)
        .holder(dtPresentation.getHolder())
        .proof(null) // dtPresentation.getProof() is always null
        .build();
  }

  @NonNull
  @SneakyThrows
  public static com.danubetech.verifiablecredentials.VerifiableCredential map(
      VerifiableCredential credential) {
    CredentialSubject subject = CredentialSubject.builder().properties(credential.getClaims()).build();

    return com.danubetech.verifiablecredentials.VerifiableCredential.builder()
        .defaultContexts(true)
        .forceContextsArray(true)
        .forceTypesArray(true)
        .id(credential.getId())
        .types(credential.getTypes())
        .issuer(credential.getIssuer())
        .credentialSubject(subject)
        .build();
    // .credentialStatus(credential.getStatus())
  }

  @NonNull
  public static VerifiableCredential map(
      com.danubetech.verifiablecredentials.VerifiableCredential dtCredential) {
    return VerifiableCredential.builder()
        .id(dtCredential.getId())
        .types(dtCredential.getTypes())
        .issuer(dtCredential.getIssuer())
        .issuanceDate(dtCredential.getIssuanceDate())
        .proof(map(dtCredential.getLdProof()))
        .build();
  }

  private static CredentialStatus map(VerifiableCredentialStatus credentialStatus) {

    return CredentialStatus.builder().defaultContexts(true).build();
    // .types(credentialStatus)
  }

  private static Ed25519Proof map(LdProof dtProof) {
    if (dtProof == null)
      return null;

    if (!Ed25519Proof.TYPE.equals(dtProof.getType())) {
      throw new RuntimeException(
          String.format(
              "Proof not supported: %s. Supported Proofs: %s",
              dtProof.getType(), Ed25519Proof.TYPE));
    }

    return Ed25519Proof.builder()
        .created(dtProof.getCreated())
        .type(dtProof.getType())
        .proofPurpose(dtProof.getProofPurpose())
        .proofValue(MultibaseFactory.create(dtProof.getProofValue()))
        .verificationMethod(dtProof.getVerificationMethod())
        .build();
  }
}
