package org.eclipse.tractusx.ssi.util;

import jakarta.ws.rs.NotFoundException;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.DidMethodIdentifier;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidPublicKeyResolverHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestDidHandler implements DidPublicKeyResolverHandler {

    private static final DidMethod METHOD = new DidMethod("test");
    public static final Did DID_TEST_OPERATOR = new Did(METHOD, new DidMethodIdentifier("operator"));

    private final Map<Did, byte[]> didMap = new LinkedHashMap<>();

    public TestDidHandler() {
        initializeDids();
    }

    @Override
    public DidMethod getSupportedMethod() {
        return METHOD;
    }

    @Override
    public byte[] resolve(Did did) {
        if (didMap.containsKey(did))
            return didMap.get(did);

        throw new NotFoundException(did.toString());
    }


    private void initializeDids() {
        didMap.put(DID_TEST_OPERATOR, KeyResourceLoader.readPublicKey());
    }

}
