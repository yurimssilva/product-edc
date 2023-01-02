package org.eclipse.tractusx.ssi.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.tractusx.ssi.document.DidDocument;

@Produces({MediaType.APPLICATION_JSON})
@Path("./well-known/did.json")
public class DidWebDocumentController {

    @GET
    public DidDocument request() {
        return new DidDocument(); // TODO
    }
}
