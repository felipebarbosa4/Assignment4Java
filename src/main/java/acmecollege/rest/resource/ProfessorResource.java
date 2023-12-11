package acmecollege.rest.resource;

import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.PROFESSOR_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_PATH;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmecollege.ejb.ACMECollegeService;
import acmecollege.entity.Professor;

@Path(PROFESSOR_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessorResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMECollegeService service;

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getProfessors() {
        LOG.debug("retrieving all professors ...");
        List<Professor> professors = service.getAllProfessors();
        return Response.ok(professors).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getProfessorById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("try to retrieve specific professor " + id);
        Professor professor = service.getProfessorById(id);
        return Response.status(professor == null ? Status.NOT_FOUND : Status.OK).entity(professor).build();
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addProfessor(Professor newProfessor) {
        Professor newProfessorWithIdTimestamps = service.persistProfessor(newProfessor);
        return Response.ok(newProfessorWithIdTimestamps).build();
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateProfessor(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, Professor updatedProfessor) {
        LOG.debug("Updating professor with ID: " + id);
        Professor updated = service.updateProfessorById(id, updatedProfessor);
        return updated != null ? Response.ok(updated).build() : Response.status(Status.NOT_FOUND).build();
    }

    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deleteProfessor(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("Attempting to delete professor with ID: " + id);
        boolean isDeleted = service.deleteProfessorById(id);
        return isDeleted ? Response.status(Status.NO_CONTENT).build() : Response.status(Status.NOT_FOUND).build();
    }

    // Additional methods as required
}
