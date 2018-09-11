package com.pl.project.controllers;

import com.pl.project.models.SubjectModel;
import com.pl.project.services.MongoBase;
import com.pl.project.services.MongoSubjects;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/subjects")
public class Subjects {

    //Unlock only if adding first record, otherwise use mongoBase instance
    //MongoSubjects mongoSubjects = new MongoSubjects();
    MongoBase mongoBase = MongoBase.getInstance();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectsList() {
        List<SubjectModel> subjectsList = mongoBase.subjectsList();
        return Response.status(Response.Status.OK).entity(subjectsList).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleSubject(@PathParam("index") int id) {
        SubjectModel oneSubject = mongoBase.subjectsList().get(id);
        return Response.status(Response.Status.OK).entity(oneSubject).build();
    }

    @POST
    @Path("/add_subject")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSubject(SubjectModel subjectModel) {
        mongoBase.addSubject(subjectModel);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/update_subject")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response.ResponseBuilder updateSubject(SubjectModel subjectModel) {
        mongoBase.updateSubject(subjectModel);
        return Response.status(Response.Status.OK);

    }

    @DELETE
    @Path("/delete_subject/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("index") int id) {
        SubjectModel deletedSubject = mongoBase.subjectsList().get(id);
        mongoBase.deleteSubject(deletedSubject);
        return Response.status(Response.Status.OK).build();
    }
}
