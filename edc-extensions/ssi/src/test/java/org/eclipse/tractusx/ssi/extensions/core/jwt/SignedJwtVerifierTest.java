package org.eclipse.tractusx.ssi.extensions.core.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.tractusx.ssi.extensions.core.jsonld.DanubCredentialFactory;
import org.eclipse.tractusx.ssi.extensions.core.base.Base58Bitcoin;
import org.eclipse.tractusx.ssi.extensions.core.credentials.SerializedVerifiablePresentation;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.DanubTechMapper;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdSerializer;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdSerializerImpl;
import org.eclipse.tractusx.ssi.extensions.core.util.DidParser;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.Ed25519VerificationKey2020;
import org.eclipse.tractusx.ssi.spi.did.PublicKey;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.net.URI;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class SignedJwtVerifierTest {

    SignedJwtVerifier signedJwtVerifier;
    JsonLdSerializer jsonLdSerializer;
    @Mock
    DidDocumentResolver didDocumentResolver;
    @Mock
    DidDocument didDocument;
    @Mock
    Did didMock;

    @BeforeEach
    public void init(){
        didDocumentResolver = Mockito.mock(DidDocumentResolver.class);
        didDocument = Mockito.mock(DidDocument.class);
        signedJwtVerifier = new SignedJwtVerifier(didDocumentResolver);
        jsonLdSerializer = new JsonLdSerializerImpl();
    }

    @Test
    @SneakyThrows
    public void verifyJwtSuccess(){
        // given
        try (MockedStatic<DidParser> didParserMockedStatic = Mockito.mockStatic(DidParser.class)) {
            KeyPair keyPair = getKeyPair();
            List<PublicKey> publicKeys = getPublicKeyList(keyPair.getPublic());

            didMock = Mockito.mock(Did.class);
            didParserMockedStatic.when(() -> DidParser.parse(any(String.class)))
                    .thenReturn(didMock);

            doReturn(didDocument).when(didDocumentResolver).resolve(any(Did.class));
            doReturn(publicKeys).when(didDocument).getPublicKeys();

            SignedJWT toTest = SignedJwtFactory.createTestJwt(
                    "someIssuer",
                    "",
                    "someAudience",
                    getTestPresentation(),
                    (ECPrivateKey) keyPair.getPrivate()
            );
        // when
            Boolean verify = signedJwtVerifier.verify(toTest);
        // then
            assertTrue(verify);
        }
    }

    @SneakyThrows
    @Test
    public void verifTestParseException(){
        // given
        SignedJWT signedJWTMock = Mockito.mock(SignedJWT.class);
        assertNotNull(signedJWTMock);
        doThrow(ParseException.class).when(signedJWTMock).getJWTClaimsSet();
        String expectedMessage = "JOSEException";
        // when
        JOSEException exception = Assertions.assertThrows(JOSEException.class,
                () -> signedJwtVerifier.verify(signedJWTMock));
        // then
        Assertions.assertTrue(exception.toString().contains(expectedMessage));
    }

    @Test
    @SneakyThrows
    public void verifTestJoseException(){
        // given
        String exceptionMessage = "com.nimbusds.jose.JOSEException";
        KeyPair keyPair = getKeyPair();
        List<PublicKey> publicKeys = getPublicKeyList(keyPair.getPublic());
        SignedJWT toTest = Mockito.mock(SignedJWT.class);
        JWTClaimsSet claimsSetMock = Mockito.mock(JWTClaimsSet.class);
        didMock = Mockito.mock(Did.class);
        doReturn(didDocument).when(didDocumentResolver).resolve(any(Did.class));
        doReturn(publicKeys).when(didDocument).getPublicKeys();
        doThrow(JOSEException.class).when(toTest).verify(any(ECDSAVerifier.class));
        doReturn(claimsSetMock).when(toTest).getJWTClaimsSet();
        doReturn("").when(claimsSetMock).getIssuer();
        try (MockedStatic<DidParser> didParserMockedStatic = Mockito.mockStatic(DidParser.class)) {
            didParserMockedStatic.when(() -> DidParser.parse(any(String.class)))
                    .thenReturn(didMock);
            // when
            JOSEException exception = Assertions.assertThrows(JOSEException.class,
                    () -> signedJwtVerifier.verify(toTest));
            // then
            Assertions.assertTrue(exception.toString().contains(exceptionMessage));
        }
    }

    @SneakyThrows
    private KeyPair getKeyPair(){
        // Add BC as Provider
        Security.addProvider(new BouncyCastleProvider());
        // Generate Keypair
        ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("secp256r1");
        KeyPairGenerator keyPairGenerator = null;
        keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
        keyPairGenerator.initialize(ecGenSpec, new SecureRandom());

        KeyPair pair = keyPairGenerator.generateKeyPair();

        ECPrivateKey privateKey = (ECPrivateKey) pair.getPrivate();
        ECPublicKey publicKey = (ECPublicKey) pair.getPublic();

        return pair;
    }

    @SneakyThrows
    private List<PublicKey> getPublicKeyList(java.security.@NotNull PublicKey publicKey){
        List<PublicKey> pks = new ArrayList<>();
        byte[] encodedPublicKey = null;
        if ("X.509".equals(publicKey.getFormat())) {
            encodedPublicKey = publicKey.getEncoded();
        }
        MultibaseString mbs = Base58Bitcoin.create(encodedPublicKey);
        org.eclipse.tractusx.ssi.spi.did.PublicKey pk = Ed25519VerificationKey2020
                .builder()
                .publicKeyMultibase(mbs)
                .id(new URI("someweburl.com"))
                .controller(new URI("somecontrolleruri.com"))
                .build();
        pks.add(pk);
        return pks;
    }

    private SerializedVerifiablePresentation getTestPresentation(){
        SerializedVerifiablePresentation presentation;
        com.danubetech.verifiablecredentials.VerifiablePresentation dtVp
                = DanubCredentialFactory.getTestDanubVP();
        VerifiablePresentation cxPresentation = DanubTechMapper.map(dtVp);
        presentation = jsonLdSerializer.serializePresentation(cxPresentation);
        return presentation;
    }
}
