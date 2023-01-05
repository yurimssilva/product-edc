package org.eclipse.tractusx.ssi.resolver;

import java.security.PublicKey;

public interface DidPublicKeyResolver {
    byte[] resolve(Did did);

    void registerHandler(DidPublicKeyResolverHandler resolver);
}
