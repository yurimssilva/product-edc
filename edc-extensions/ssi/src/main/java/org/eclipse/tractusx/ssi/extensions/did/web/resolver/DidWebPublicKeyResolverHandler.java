package org.eclipse.tractusx.ssi.extensions.did.web.resolver;

import org.eclipse.tractusx.ssi.extensions.core.exception.SsiException;
import org.eclipse.tractusx.ssi.extensions.did.web.util.Constants;
import org.eclipse.tractusx.ssi.extensions.did.web.util.DidWebParser;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidPublicKeyResolverHandler;

import java.net.URI;
import java.util.ConcurrentModificationException;

public class DidWebPublicKeyResolverHandler implements DidPublicKeyResolverHandler {

    @Override
    public DidMethod getSupportedMethod() {
        return Constants.DID_WEB_METHOD;
    }

    @Override
    public byte[] resolve(Did did) {
        if (!did.getMethod().equals(Constants.DID_WEB_METHOD))
            throw new SsiException("Handler can only handle did:" + Constants.DID_WEB_METHOD);

        final URI url = DidWebParser.parse(did);


        return new byte[0];
    }

}
