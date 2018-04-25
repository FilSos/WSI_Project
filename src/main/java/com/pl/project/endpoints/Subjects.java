package com.pl.project.endpoints;


import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import com.pl.project.repositories.MongoRepository.MongoBase;
import com.pl.project.repositories.MongoRepository.MongoSubjects;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("/subjects")
public class Subjects {

MongoSubjects mongoSubjects = new MongoSubjects();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectsList() {
        List<SubjectModel> subjectsList = MongoBase.getInstance().subjectsList();
        return Response.status(Response.Status.OK).entity(subjectsList).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleSubject(@PathParam("id") int id) {
        SubjectModel oneSubject = MongoBase.getInstance().subjectsList().get(id);
        return Response.status(Response.Status.OK).entity(oneSubject).build();
    }

    @POST
    @Path("/add_subject")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSubject() {
        MongoBase mongoBase = MongoBase.getInstance();
        Date date = new Date();
        mongoSubjects.addSubject();
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/delete_subject/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("id") int id) {
        MongoBase mongoBase = MongoBase.getInstance();
        SubjectModel deletedSubject = mongoBase.subjectsList().get(id);
        mongoBase.deleteSubject(deletedSubject);
        return Response.status(Response.Status.OK).build();
    }
}
