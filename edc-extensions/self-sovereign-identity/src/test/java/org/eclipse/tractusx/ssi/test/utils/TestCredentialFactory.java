package org.eclipse.tractusx.ssi.test.utils;

import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredentialType;

import java.net.URI;
import java.util.Date;
import java.util.List;

public class TestCredentialFactory {

    public static VerifiableCredential generateCredential(TestIdentity issuer, String credentialType) {
        VerifiableCredential verifiableCredential = VerifiableCredential.builder()
                .id(URI.create(TestDidFactory.createRandom() + "#credential"))
                .issuer(issuer.getDid().toUri())
                .issuanceDate(new Date())
                .expirationDate(new Date(2025, 1, 1))
                .types(List.of(VerifiableCredentialType.VERIFIABLE_CREDENTIAL, credentialType))
                .build();

        return verifiableCredential;
    }

}
