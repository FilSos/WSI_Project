package com.pl.project.controllers;


import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import com.pl.project.services.MongoBase;
import com.pl.project.services.MongoSubjects;
import org.bson.types.ObjectId;

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

    @GET
    @Path("/{id}/students")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectStudents(@PathParam("id") ObjectId id) {
        List<StudentModel> studentsOnSubject = mongoSubjects.studentsOnSubject(id);
        return Response.status(Response.Status.OK).entity(studentsOnSubject).build();
    }

    @GET
    @Path("/{id}/students/{id2}/grades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectStudentGrades(@PathParam("id") ObjectId id,@PathParam("id2") ObjectId id2) {
        List<StudentModel> studentsOnSubject = mongoSubjects.studentsOnSubject(id);
        return Response.status(Response.Status.OK).entity(studentsOnSubject).build();
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
