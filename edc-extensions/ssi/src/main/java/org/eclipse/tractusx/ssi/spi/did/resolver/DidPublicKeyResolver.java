package org.eclipse.tractusx.ssi.spi.did.resolver;

import org.eclipse.tractusx.ssi.extensions.core.resolver.DidImpl;

public interface DidPublicKeyResolver {
    byte[] resolve(DidImpl did);

    void registerHandler(DidPublicKeyResolverHandler resolver);
}
