package org.eclipse.tractusx.ssi.fakes;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.ToJwtConverter;
import org.eclipse.tractusx.ssi.resolver.Did;
import org.eclipse.tractusx.ssi.setting.SsiSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

public class VerifiableCredentialStoreFakeTest {

    private VerifiableCredentialStoreFake verifiableCredentialStoreFake;

    @BeforeEach
    public void setup() {
        final SsiSettings settings = new SsiSettings(new Did("test", "123"), new byte[0], new byte[0]);
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
