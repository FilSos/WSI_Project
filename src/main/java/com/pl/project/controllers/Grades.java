package com.pl.project.controllers;

import com.pl.project.models.GradeModel;
import com.pl.project.services.MongoBase;
import com.pl.project.services.MongoGrades;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/grades")
public class Grades {

    //Unlock only if adding first record, otherwise use mongoBase instance
    //MongoGrades mongoGrades = new MongoGrades();
    MongoBase mongoBase = MongoBase.getInstance();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getGradesList(@DefaultValue("") @QueryParam("index") Long index,
                                  @DefaultValue("") @QueryParam("subject") String subject,
                                  @DefaultValue("") @QueryParam("above") String above,
                                  @DefaultValue("") @QueryParam("below") String below) {
        if (!subject.equals("") || (above.equals("") && below.equals(""))) {
            List<GradeModel> gradesList = mongoBase.studentSubjectGradesList(index, subject);
            return Response.status(Response.Status.OK).entity(gradesList).build();
        } else {
            List<GradeModel> gradesList = mongoBase.studentAboveOrBelowGradesList(index, above, below);
            return Response.status(Response.Status.OK).entity(gradesList).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleGrade(@PathParam("id") int id) {
        GradeModel oneGrade = mongoBase.oneGrade(id);
        return Response.status(Response.Status.OK).entity(oneGrade).build();
    }
    //TODO sprawdzic dzialanie
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createGrade(GradeModel gradeModel, @Context UriInfo uriInfo) {
        //mongoGrades.addGrade();
        mongoBase.addGrade(gradeModel);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(gradeModel.getId().toString());
        return Response.created(builder.build()).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response.ResponseBuilder updateGrade(GradeModel gradeModel) {
        mongoBase.updateGrade(gradeModel);
        return Response.status(Response.Status.OK);

    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteGrade(@PathParam("id") int id) {
        GradeModel deletedGrade = mongoBase.oneGrade(id);
        mongoBase.deleteGrade(deletedGrade);
        return Response.status(Response.Status.OK).build();
    }
}
