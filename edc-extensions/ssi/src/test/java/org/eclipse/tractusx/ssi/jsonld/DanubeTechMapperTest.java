package org.eclipse.tractusx.ssi.jsonld;

import org.eclipse.tractusx.ssi.extensions.core.jsonLd.DanubTechMapper;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DanubeTechMapperTest {

    DanubTechMapper mapper;


    /**
     * DanubTech Verifiable Presentation to Verifiable Credential and vice versa
     */

    @Test
    public void mapVerifiableCredentialDtPresSuccess(){
        // given

        // when
        // Input is VP output is dtVP

        // then

    }

    @Test
    public void mapVerifiableCredentialToDtPresFail(){

    }

    @Test
    public void mapDtPresToVerifiablePresentationSuccess(){
        // given
        com.danubetech.verifiablecredentials.VerifiablePresentation toTest;
        toTest = DanubCredentialFactory.getTestDanubVP();
        // when
        VerifiablePresentation result = mapper.map(toTest);
        //then
        Assertions.assertEquals(result.getHolder(), toTest.getHolder());
        Assertions.assertEquals(result.getTypes(), toTest.getTypes());
        Assertions.assertEquals(result.getId(), toTest.getId());
        Assertions.assertEquals(result.getProof(), toTest.getLdProof());
        Assertions.assertEquals(result.getVerifiableCredentials().get(0).getId(), toTest.getVerifiableCredential().getId());
    }
    
    @Test
    public void mapDtPresToVerifiableCredentialFail(){

    }

    /**
     * DanubTech to Verifiable Credential and vice versa
     */
    @Test
    public void mapDtCredToVerifiableCredentialSuccess(){
        //given

        //when

        //then

    }

    @Test
    public void mapDtCredToVerifiableCredentialFail(){

    }

    @Test
    public void mapVerifiableCredentialDtCredSuccess(){

    }

    @Test
    public void mapVerifiableCredentialDtCredFail(){

    }

    /**
     * Status and EDMapperTest
     */
    @Test
    public void mapVerifiableCredentialStatusToDtCredStatusSuccess(){

    }

    @Test
    public void mapVerifiableCredentialStatusToDtCredStatusFail(){

    }

    @Test
    public void mapLdProofToEd25519ProofTestSuccess(){

    }

    @Test
    public void mapLdProofToEd25519ProofTestFail(){

    }

}
