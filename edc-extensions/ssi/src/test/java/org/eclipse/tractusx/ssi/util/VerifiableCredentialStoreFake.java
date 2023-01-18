package org.eclipse.tractusx.ssi.util;

import com.danubetech.verifiablecredentials.CredentialSubject;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VerifiableCredentialStoreFake implements VerifiableCredentialWallet {

    private VerifiableCredential membershipCredential;

    private final SsiSettings settings;

    public VerifiableCredentialStoreFake(SsiSettings settings) {
        this.settings = settings;
    }

    @Override
    public String getIdentifier() {
        return "Test";
    }

    @Override
    public VerifiableCredential getMembershipCredential() {
        return membershipCredential;
    }

    public void prepareMembershipCredential() {
//        Map<String, Object> claims = new LinkedHashMap<>();
//        claims.put("type", "MembershipCredential");
//        claims.put("status", "active");
//        CredentialSubject credentialSubject = CredentialSubject.builder()
//                .id(URI.create("did:example:ebfeb1f712ebc6f1c276e12ec21"))
//                .claims(claims)
//                .build();
//
//        membershipCredential = VerifiableCredential.builder()
//                .id(URI.create("urn:test:bpn/1"))
//                .contexts(List.of(URI.create("https://www.w3.org/2018/credentials/v1"), URI.create("https://www.w3.org/2018/credentials/examples/v1")))
//                .types(List.of("MembershipCredential", "VerifiableCredential"))
//                .issuanceDate(new Date())
//                .expirationDate(Date.from(Instant.now().plusSeconds(300 /* Five Minutes */)))
//                .issuer(URI.create(TestDidDocumentResolver.DID_TEST_OPERATOR.toString()))
//                .credentialSubject(credentialSubject)
//                .build();
    }
}
