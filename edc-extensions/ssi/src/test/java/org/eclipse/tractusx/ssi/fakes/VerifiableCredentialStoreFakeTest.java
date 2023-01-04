package org.eclipse.tractusx.ssi.fakes;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.ToJwtConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VerifiableCredentialStoreFakeTest {

    private VerifiableCredentialStoreFake verifiableCredentialStoreFake;

    @BeforeEach
    public void setup() {
        verifiableCredentialStoreFake = new VerifiableCredentialStoreFake(settings);
    }

    @Test
    public void testSerializableMembershipCredential() {
        verifiableCredentialStoreFake.prepareMembershipCredential();

        final VerifiableCredential membershipCredential = verifiableCredentialStoreFake.GetMembershipCredential();
        final JwtVerifiableCredential jwtMembershipCredential = ToJwtConverter.toJwtVerifiableCredential(membershipCredential, "http://localhost");

        Assertions.assertNotNull(jwtMembershipCredential.getCompactSerialization());
    }
}
