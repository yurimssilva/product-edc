package org.eclipse.tractusx.ssi.spi.did.resolver;

import org.eclipse.tractusx.ssi.spi.did.DidMethod;

public interface DidDocumentResolverRegistry {
    DidDocumentResolver get(DidMethod did);

    void register(DidDocumentResolver resolver);

    void unregister(DidDocumentResolver resolver);
}
