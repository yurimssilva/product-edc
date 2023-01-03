package org.eclipse.tractusx.ssi.resolver;

import jakarta.ws.rs.NotFoundException;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class DidPublicKeyResolverImpl implements DidPublicKeyResolver {

    private List<DidPublicKeyResolverHandler> handlers = new ArrayList<>();

    @Override
    public PublicKey resolve(Did did) {
        return handlers.stream()
                .filter(h -> h.canHandle(did))
                .map(h -> h.resolve(did))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void registerHandler(DidPublicKeyResolverHandler resolver) {
        if (!handlers.contains(resolver))
            handlers.add(resolver);
    }
}
