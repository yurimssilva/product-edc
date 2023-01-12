package org.eclipse.tractusx.ssi.spi.did.resolver;

import org.eclipse.tractusx.ssi.spi.did.Did;

public interface DidPublicKeyResolver {
    byte[] resolve(Did did);

    void registerHandler(DidPublicKeyResolverHandler resolver);
}
