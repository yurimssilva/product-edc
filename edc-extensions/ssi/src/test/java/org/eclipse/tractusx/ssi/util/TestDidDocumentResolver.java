package org.eclipse.tractusx.ssi.util;

import jakarta.ws.rs.NotFoundException;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.DidMethodIdentifier;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestDidDocumentResolver implements DidDocumentResolver {

    private static final DidMethod METHOD = new DidMethod("test");
    public static final Did DID_TEST_OPERATOR = new Did(METHOD, new DidMethodIdentifier("operator"));

    private final Map<Did, DidDocument> didMap = new LinkedHashMap<>();

    public TestDidDocumentResolver() {
        initializeDids();
    }

    @Override
    public DidMethod getSupportedMethod() {
        return METHOD;
    }

    @Override
    public DidDocument resolve(Did did) {
        if (didMap.containsKey(did))
            return didMap.get(did);

        throw new NotFoundException(did.toString());
    }

    private void initializeDids() {
        final DidDocument operatorDocument = DidDocument
                .builder()
                .did(DID_TEST_OPERATOR)
                .build();

        didMap.put(DID_TEST_OPERATOR, operatorDocument);
    }
}
