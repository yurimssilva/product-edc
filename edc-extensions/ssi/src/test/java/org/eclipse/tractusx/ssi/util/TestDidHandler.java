package org.eclipse.tractusx.ssi.util;

import jakarta.ws.rs.NotFoundException;
import org.eclipse.tractusx.ssi.extensions.core.resolver.DidImpl;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidPublicKeyResolverHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestDidHandler implements DidPublicKeyResolverHandler {

    private static final String METHOD = "test";
    public static final DidImpl DID_TEST_OPERATOR = new DidImpl(METHOD, "operator");

    private final Map<DidImpl, byte[]> didMap = new LinkedHashMap<>();

    public TestDidHandler() {
        initializeDids();
    }

    @Override
    public String getMethod() {
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
