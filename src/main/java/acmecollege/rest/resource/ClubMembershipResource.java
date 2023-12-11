package acmecollege.rest.resource;

import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.CLUB_MEMBERSHIP_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_PATH;

import acmecollege.entity.ClubMembership;
import acmecollege.ejb.ACMECollegeService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(CLUB_MEMBERSHIP_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClubMembershipResource {

    @EJB
    protected ACMECollegeService service;

    @Inject
    protected SecurityContext sc;

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addClubMembership(ClubMembership newClubMembership) {
        ClubMembership clubMembershipWithId = service.persistClubMembership(newClubMembership);
        return Response.ok(clubMembershipWithId).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getClubMemberships() {
        List<ClubMembership> clubMemberships = service.getAllClubMemberships();
        return Response.ok(clubMemberships).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getClubMembershipById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        ClubMembership clubMembership = service.getClubMembershipById(id);
        if (clubMembership != null) {
            return Response.ok(clubMembership).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateClubMembership(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, ClubMembership updatedClubMembership) {
        ClubMembership clubMembership = service.updateClubMembership(id, updatedClubMembership);
        if (clubMembership != null) {
            return Response.ok(clubMembership).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deleteClubMembership(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        boolean isDeleted = service.deleteClubMembershipById(id);
        if (isDeleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
