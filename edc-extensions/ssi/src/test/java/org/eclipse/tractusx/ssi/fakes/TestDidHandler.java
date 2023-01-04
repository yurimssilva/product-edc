package org.eclipse.tractusx.ssi.fakes;

import jakarta.ws.rs.NotFoundException;
import org.eclipse.tractusx.ssi.resolver.Did;
import org.eclipse.tractusx.ssi.resolver.DidPublicKeyResolverHandler;

import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestDidHandler implements DidPublicKeyResolverHandler {

    private static final String METHOD = "test";
    public static final Did DID_TEST_ROOT = new Did(METHOD, "root");

    private final Map<Did, PublicKey> didMap = new LinkedHashMap<>();

    public TestDidHandler() {
        initializeDids();
    }

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public PublicKey resolve(Did did) {
        if (didMap.containsKey(did))
            return didMap.get(did);

        throw new NotFoundException(did.toString());
    }


    private void initializeDids() {
        didMap.put(DID_TEST_ROOT, null);
    }

}
