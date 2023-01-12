package org.eclipse.tractusx.ssi.spi.did.resolver;

import org.eclipse.tractusx.ssi.spi.did.DidMethod;

public interface DidPublicKeyResolverRegistry {
    DidPublicKeyResolver get(DidMethod did);

    void register(DidPublicKeyResolverHandler resolver);

    void unregister(DidPublicKeyResolverHandler resolver);
}
