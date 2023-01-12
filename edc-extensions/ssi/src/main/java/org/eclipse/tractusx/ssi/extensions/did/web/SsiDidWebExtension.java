package org.eclipse.tractusx.ssi.extensions.did.web;

import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.edc.web.spi.WebService;
import org.eclipse.tractusx.ssi.extensions.did.web.controler.DidWebDocumentController;
import org.eclipse.tractusx.ssi.extensions.did.web.settings.DidWebSettings;

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
    }
}
