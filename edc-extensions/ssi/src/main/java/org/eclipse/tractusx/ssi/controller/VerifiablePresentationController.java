package org.eclipse.tractusx.ssi.controller;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentation;
import org.eclipse.tractusx.ssi.credentials.SerializedJwtPresentationFactory;
import org.eclipse.tractusx.ssi.store.VerifiableCredentialStore;

/**
 * Internet facing API for other connector to request verifiable credentials
 */
@Produces({MediaType.APPLICATION_JSON})
@Path("/ssi")
public class VerifiablePresentationController {

    private final VerifiableCredentialStore credentialStore;
    private final SerializedJwtPresentationFactory presentationFactory;

    public VerifiablePresentationController(VerifiableCredentialStore verifiableCredentialStore, SerializedJwtPresentationFactory presentationFactory) {
        this.credentialStore = verifiableCredentialStore;
        this.presentationFactory = presentationFactory;
    }

    @GET
    @Path("/verifiable-presentation/{requestedCredentialType}")
    public String request(@PathParam("requestedCredentialType") String requestedCredentialType) {

        // TODO Don't just give out credentials to anybody. There should be some check here for the requestor
        final String audience = "TODO";

        switch (requestedCredentialType) {
            case "MembershipCredential": // TODO Magic string
                final VerifiableCredential membershipCredential = credentialStore.GetMembershipCredential();
                final SerializedJwtPresentation membershipPresentation = presentationFactory.createPresentation(membershipCredential, audience);
                return membershipPresentation.getValue();
            default:
                throw new BadRequestException();
        }
    }
}
