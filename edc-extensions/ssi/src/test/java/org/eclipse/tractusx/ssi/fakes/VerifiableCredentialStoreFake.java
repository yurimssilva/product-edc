package org.eclipse.tractusx.ssi.fakes;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import org.eclipse.tractusx.ssi.setting.SsiSettings;
import org.eclipse.tractusx.ssi.store.VerifiableCredentialStore;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VerifiableCredentialStoreFake implements VerifiableCredentialStore {

    private VerifiableCredential membershipCredential;

    private final SsiSettings settings;

    public VerifiableCredentialStoreFake(SsiSettings settings) {
        this.settings = settings;
    }

    @Override
    public VerifiableCredential GetMembershipCredential() {
        return membershipCredential;
    }

    public void prepareMembershipCredential() {
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("type", "MembershipCredential");
        claims.put("status", "active");
        CredentialSubject credentialSubject = CredentialSubject.builder()
                .id(URI.create("did:example:ebfeb1f712ebc6f1c276e12ec21"))
                .claims(claims)
                .build();

        membershipCredential = VerifiableCredential.builder()
                .id(URI.create("urn:test:bpn/1"))
                .contexts(List.of(URI.create("https://www.w3.org/2018/credentials/v1"), URI.create("https://www.w3.org/2018/credentials/examples/v1")))
                .types(List.of("MembershipCredential", "VerifiableCredential"))
                .issuanceDate(new Date())
                .expirationDate(Date.from(Instant.now().plusSeconds(300 /* Five Minutes */)))
                .issuer(URI.create(TestDidHandler.DID_TEST_ROOT.toString()))
                .credentialSubject(credentialSubject)
                .build();
    }
}
