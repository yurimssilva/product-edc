package org.eclipse.tractusx.ssi.util;

import jakarta.ws.rs.NotFoundException;
import org.eclipse.tractusx.ssi.extensions.core.base.MultibaseFactory;
import org.eclipse.tractusx.ssi.spi.did.*;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDidDocumentResolver implements DidDocumentResolver {

    private static final DidMethod METHOD = new DidMethod("test");
    public static final Did DID_TEST_OPERATOR = new Did(METHOD, new DidMethodIdentifier("operator"));

    public Map<Did, List<DidVerificationMethod>> verificationMethodList = new HashMap<>();

    @Override
    public DidMethod getSupportedMethod() {
        return METHOD;
    }

    public void registerVerificationMethod(Did did, Did keyId, byte[] publicKey) {
        if (!verificationMethodList.containsKey(did)) {
            verificationMethodList.putIfAbsent(did, new ArrayList<>());
        }

        Ed25519VerificationKey2020 verificationKey = Ed25519VerificationKey2020.builder()
                .id(keyId.toUri())
                .controller(URI.create("did:test:example"))
                .publicKeyMultibase(MultibaseFactory.create(publicKey)).build();

        verificationMethodList.get(did)
                .add(verificationKey);
    }

    @Override
    public DidDocument resolve(Did did) {

        if (did.equals(DID_TEST_OPERATOR))
            return getOperatorDid();

        throw new NotFoundException(did.toString());
    }

    private DidDocument getOperatorDid() {
        final DidDocument operatorDocument = DidDocument
                .builder()
                .id(DID_TEST_OPERATOR.toString())
                .verificationMethods(verificationMethodList.get(DID_TEST_OPERATOR))
                .build();

        return operatorDocument;
    }
}
