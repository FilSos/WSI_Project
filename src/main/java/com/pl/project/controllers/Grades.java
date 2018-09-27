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

    //TODO najpewniej przerzucic do jednej z hierarchicznej metod zeby zachowac ciaglosc
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
//Unused method
//    @GET
//    @Path("/{id}")
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Response getSingleGrade(@PathParam("id") int id) {
//        GradeModel oneGrade = mongoBase.oneGrade(id);
//        return Response.status(Response.Status.OK).entity(oneGrade).build();
//    }
}
