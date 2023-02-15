package org.eclipse.tractusx.ssi.extensions.core.testUtils;

import jakarta.ws.rs.NotFoundException;
import org.eclipse.tractusx.ssi.extensions.core.base.Base58Bitcoin;
import org.eclipse.tractusx.ssi.extensions.core.resolver.did.DidDocumentResolverRegistryImpl;
import org.eclipse.tractusx.ssi.spi.did.*;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolverRegistry;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDidDocumentResolver implements DidDocumentResolver {

    public static DidDocumentResolverRegistry withRegistry() {
        final DidDocumentResolverRegistry registry = new DidDocumentResolverRegistryImpl();
        registry.register(new TestDidDocumentResolver());
        return registry;
    }

    private static final DidMethod METHOD = new DidMethod("test");
    public static final Did DID_TEST_OPERATOR = new Did(METHOD, new DidMethodIdentifier("operator"));

    public Map<Did, List<PublicKey>> verificationMethodList = new HashMap<>();

    @Override
    public DidMethod getSupportedMethod() {
        return METHOD;
    }

    public void registerVerificationMethod(Did did, Did keyId, byte[] publicKey) {
        if (!verificationMethodList.containsKey(did)) {
            verificationMethodList.putIfAbsent(did, new ArrayList<>());
        }

        Ed25519VerificationKey2020 verificationKey =
                Ed25519VerificationKey2020.builder()
                        .id(keyId.toUri())
                        .controller(URI.create("did:test:example"))
                        .publicKeyMultibase(Base58Bitcoin.create(publicKey).getEncoded())
                        .build();

        verificationMethodList.get(did).add(verificationKey);
    }

    @Override
    public DidDocument resolve(Did did) {

        if (did.equals(DID_TEST_OPERATOR)) return getOperatorDid();

        throw new NotFoundException(did.toString());
    }

    private DidDocument getOperatorDid() {
        final DidDocument operatorDocument =
                DidDocument.builder()
                        .id(DID_TEST_OPERATOR.toUri())
                        .publicKeys(verificationMethodList.get(DID_TEST_OPERATOR))
                        .build();

        return operatorDocument;
    }
}
