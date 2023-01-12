package org.eclipse.tractusx.ssi.spi.did.resolver;

import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;

public interface DidDocumentResolver {

    DidMethod getSupportedMethod();
    DidDocument resolve(Did did);
}
