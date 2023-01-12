package org.eclipse.tractusx.ssi.extensions.core.controller;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.nimbusds.jwt.SignedJWT;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.tractusx.ssi.extensions.core.credentials.SerializedJwtPresentationFactory;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;

import java.util.List;

/**
 * Internet facing API for other connector to request verifiable credentials
 */
@Produces({MediaType.APPLICATION_JSON})
@Path("/ssi")
public class VerifiablePresentationController {

    private final VerifiableCredentialWallet credentialStore;
    private final SerializedJwtPresentationFactory presentationFactory;

    public VerifiablePresentationController(VerifiableCredentialWallet verifiableCredentialStore, SerializedJwtPresentationFactory presentationFactory) {
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
                final SignedJWT membershipPresentation = presentationFactory.createPresentation(List.of(membershipCredential), audience);
                return membershipPresentation.getParsedString();
            default:
                throw new BadRequestException();
        }
    }
}
