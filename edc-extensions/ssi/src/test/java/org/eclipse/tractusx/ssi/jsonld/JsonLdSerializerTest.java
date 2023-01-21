package org.eclipse.tractusx.ssi.jsonld;

import org.eclipse.tractusx.ssi.extensions.core.credentials.SerializedVerifiablePresentation;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.DanubTechMapper;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdSerializer;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdSerializerImpl;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonLdSerializerTest {

    DanubCredentialFactory credentialFactory;
    DanubTechMapper mapper;
    com.danubetech.verifiablecredentials.VerifiablePresentation toTest;
    com.danubetech.verifiablecredentials.VerifiablePresentation helperVP;
    SerializedVerifiablePresentation svp;
    JsonLdSerializer jsonLdSerializer;
    @BeforeEach
    public void setUp(){
        jsonLdSerializer = new JsonLdSerializerImpl(mapper);
    }

    @Test
    public void serializePresentationTestSuccess(){
        //given
        toTest = credentialFactory.getTestDanubVP();
        svp = new SerializedVerifiablePresentation(toTest.toJson());
        VerifiablePresentation expected = jsonLdSerializer.deserializePresentation(svp);
        //when
        Object result = jsonLdSerializer.serializePresentation(expected);
        //then
        Assertions.assertTrue(result.equals(expected));
    }

}
