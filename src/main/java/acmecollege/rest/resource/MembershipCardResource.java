/**
 * File:  StudentClubResource.java Course materials (23S) CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 * 
 * Updated by:  Group 01
 *   040878158, Adam , Jenah (as from ACSIS)
 *   studentId, Felipe, Barbosa (as from ACSIS)
 *   041070895, Chamini Savindya, Demuni (as from ACSIS)
 * 
 */
package acmecollege.rest.resource;

import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.USER_ROLE;
import static acmecollege.utility.MyConstants.MEMBERSHIP_CARD_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_PATH;

import acmecollege.entity.MembershipCard;
import acmecollege.ejb.ACMECollegeService;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(MEMBERSHIP_CARD_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MembershipCardResource {

    @EJB
    protected ACMECollegeService service;

    @Inject
    protected SecurityContext sc;

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addMembershipCard(MembershipCard newMembershipCard) {
        MembershipCard membershipCardWithId = service.persistMembershipCard(newMembershipCard);
        return Response.ok(membershipCardWithId).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getMembershipCards() {
        List<MembershipCard> membershipCards = service.getAllMembershipCards();
        return Response.ok(membershipCards).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getMembershipCardById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        MembershipCard membershipCard = service.getMembershipCardById(id);
        if (membershipCard != null) {
            return Response.ok(membershipCard).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateMembershipCard(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, MembershipCard updatedMembershipCard) {
        MembershipCard membershipCard = service.updateMembershipCard(id, updatedMembershipCard);
        if (membershipCard != null) {
            return Response.ok(membershipCard).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deleteMembershipCard(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        boolean isDeleted = service.deleteMembershipCardById(id);
        if (isDeleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
