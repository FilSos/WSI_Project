package com.pl.project.controllers;

import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import com.pl.project.services.MongoBase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;


public class Grades {


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/students/{id}/subjects/{id2}/grades")
    public Response getGradesList(@PathParam("id") int id,@PathParam("id2") int id2) {
        StudentModel student = MongoBase.getInstance().studentsList().get(id);
        SubjectModel subject = MongoBase.getInstance().subjectsList().get(id2);
        GradeModel gradesList = MongoBase.getInstance().gradesList().get(0);
        return Response.status(Response.Status.OK).entity(gradesList).build();
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
        //mongoSubjects.addSubject();
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
