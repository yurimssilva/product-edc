package org.eclipse.tractusx.ssi.extensions.did.web.resolver;

import org.eclipse.tractusx.ssi.extensions.core.exception.SsiException;
import org.eclipse.tractusx.ssi.extensions.did.web.util.Constants;
import org.eclipse.tractusx.ssi.extensions.did.web.util.DidWebParser;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;

import java.net.URI;

public class DidWebDocumentResolver implements DidDocumentResolver {

    @Override
    public DidMethod getSupportedMethod() {
        return Constants.DID_WEB_METHOD;
    }

    @Override
    public DidDocument resolve(Did did) {
        if (!did.getMethod().equals(Constants.DID_WEB_METHOD))
            throw new SsiException("Handler can only handle the following methods:" + Constants.DID_WEB_METHOD);

        final URI url = DidWebParser.parse(did);

        return DidDocument.builder()
                .did(did)
                .build();
    }

}
