package org.eclipse.tractusx.ssi.extensions.did.web;

import okhttp3.OkHttpClient;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.edc.web.spi.WebService;
import org.eclipse.tractusx.ssi.extensions.core.resolver.did.DidDocumentResolverRegistryImpl;
import org.eclipse.tractusx.ssi.extensions.did.web.controler.DidWebDocumentController;
import org.eclipse.tractusx.ssi.extensions.did.web.resolver.DidWebDocumentResolver;
import org.eclipse.tractusx.ssi.extensions.did.web.settings.DidWebSettings;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolverRegistry;

public class SsiDidWebExtension implements ServiceExtension {
    public static final String EXTENSION_NAME = "SSI DID Web Extension";

    public static final String API_DID_WEB_CONTEXT = "didweb";

    @Inject
    private WebService webService;

    @Override
    public String name() {
        return EXTENSION_NAME;
    }

    @Override
    public void start() {

    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        final Monitor monitor = context.getMonitor();

        final DidWebSettings settings = new DidWebSettings();

        final DidWebDocumentController didWebDocumentController = new DidWebDocumentController(settings, monitor);
        webService.registerResource(API_DID_WEB_CONTEXT, didWebDocumentController);

        final OkHttpClient client = new OkHttpClient();
        final DidDocumentResolver didWebDocumentResolver = new DidWebDocumentResolver(client);
        final DidDocumentResolverRegistry didDocumentResolverRegistry = context.getService(DidDocumentResolverRegistry.class);
        didDocumentResolverRegistry.register(didWebDocumentResolver);
    }
}
