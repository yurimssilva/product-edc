package org.eclipse.tractusx.ssi.resolver;

public interface DidPublicKeyResolver {
    DidPublicKeyResolverHandler resolve(Did did);

    void registerHandler(DidPublicKeyResolverHandler resolver);
}
