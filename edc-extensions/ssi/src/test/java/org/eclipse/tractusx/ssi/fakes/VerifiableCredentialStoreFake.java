package org.eclipse.tractusx.ssi.fakes;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import foundation.identity.jsonld.JsonLDUtils;
import org.eclipse.tractusx.ssi.store.VerifiableCredentialStore;

import java.net.URI;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VerifiableCredentialStoreFake implements VerifiableCredentialStore {

    private VerifiableCredential membershipCredential;

    @Override
    public VerifiableCredential GetMembershipCredential() {
        return membershipCredential;
    }

    public void prepareMembershipCredential() {
//        var subject = CredentialSubject.builder()
//                .type("MembershipCredential")
//                .claims(Map.of("memberOf", "MembershipCredential",
//                        "status", "Active",
//                        "startTime", new Date().toString()))
//                .build();
//        membershipCredential = VerifiableCredential.builder()
//                .id(URI.create("urn:test:bpn/1"))
//                .contexts(List.of(URI.create("https://www.w3.org/2018/credentials/v1"), URI.create("https://raw.githubusercontent.com/catenax-ng/product-core-schemas/main/businessPartnerData")))
//                .types(List.of("MembershipCredential", "VerifiableCredential"))
//                .issuanceDate(new Date())
//                .expirationDate(new Date(2024,1,1))
//                .issuer(URI.create("urn:test:bpn"))
//                .credentialSubject(subject)
//                .build();

        Map<String, Object> claims = new LinkedHashMap<>();
        Map<String, Object> degree = new LinkedHashMap<String, Object>();
        degree.put("name", "Bachelor of Science and Arts");
        degree.put("type", "BachelorDegree");
        claims.put("college", "Test University");
        claims.put("degree", degree);

        CredentialSubject credentialSubject = CredentialSubject.builder()
                .id(URI.create("did:example:ebfeb1f712ebc6f1c276e12ec21"))
                .claims(claims)
                .build();
        VerifiableCredential verifiableCredential = VerifiableCredential.builder()
                .context(VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_EXAMPLES_V1)
                .type("UniversityDegreeCredential")
                .id(URI.create("http://example.edu/credentials/3732"))
                .issuer(URI.create("did:example:76e12ec712ebc6f1c221ebfeb1f"))
                .issuanceDate(JsonLDUtils.stringToDate("2019-06-16T18:56:59Z"))
                .expirationDate(JsonLDUtils.stringToDate("2019-06-17T18:56:59Z"))
                .credentialSubject(credentialSubject)
                .build();


        membershipCredential = verifiableCredential;
    }
}
