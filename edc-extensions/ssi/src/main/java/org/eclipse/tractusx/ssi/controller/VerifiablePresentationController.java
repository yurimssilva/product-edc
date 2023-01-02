package org.eclipse.tractusx.ssi.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.tractusx.ssi.credentials.VerifiableCredential;
import org.eclipse.tractusx.ssi.credentials.VerifiablePresentation;
import org.eclipse.tractusx.ssi.credentials.VerifiablePresentationImpl;
import org.eclipse.tractusx.ssi.store.CredentialStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Internet facing API for other connector to request verifiable credentials
 */
@Produces({MediaType.APPLICATION_JSON})
@Path("/ssi")
public class VerifiablePresentationController {

    private final CredentialStore credentialStore;

    public VerifiablePresentationController(CredentialStore credentialStore) {
        this.credentialStore = credentialStore;
    }

    @GET
    @Path("/verifiable-presentation")
    public VerifiablePresentation request(@QueryParam("credential") List<String> requestedCredentialTypes) {

        // TODO Don't just give out credentials to anybody. There should be some check here for the requestor

        final List<VerifiableCredential> credentials = new ArrayList<>();

        for (var requestedCredentialType : requestedCredentialTypes) {
            if (credentials.stream().anyMatch(c -> c.getTypes().contains(requestedCredentialType))) {
                continue;
            }

            final List<VerifiableCredential> matchingCredential = credentialStore.Get(requestedCredentialType);
            if (matchingCredential.isEmpty()) {
                return null; // TODO handle if requested credential cannot be provided
            }
            credentials.add(matchingCredential.get(0)); // TODO always get first?
        }

        return new VerifiablePresentationImpl(credentials); // TODO Presentation should be created by an factory, that does all this signature and audience stuff, etc.
    }
}
