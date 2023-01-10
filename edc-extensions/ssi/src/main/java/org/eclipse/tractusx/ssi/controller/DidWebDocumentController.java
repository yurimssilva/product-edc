package org.eclipse.tractusx.ssi.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Produces({MediaType.APPLICATION_JSON})
@Path("./well-known/did.json")
public class DidWebDocumentController {

    @GET
    public String request() {
        String didDoc = getClass().getResource("webdid/did-document.json").getFile();
        return didDoc; // TODO
    }
}
