package com.pl.project.controllers;

import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
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
    public Response getSubjectsList(@DefaultValue("") @QueryParam("teacher") String teacher,
                                    @DefaultValue("") @QueryParam("subjectName") String subjectName) {
        List<SubjectModel> subjectsList = mongoBase.subjectsList(teacher,subjectName);
        return Response.status(Response.Status.OK).entity(subjectsList).build();
    }

    @GET
    @Path("/{subject}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleSubject(@PathParam("subject") String subject) {
        SubjectModel oneSubject = mongoBase.oneSubject(subject);
        return Response.status(Response.Status.OK).entity(oneSubject).build();
    }

    @GET
    @Path("/{subject}/grades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectGrades(@PathParam("subject") String subjectName) {
        List<GradeModel> gradesList = mongoBase.subjectGrades(subjectName);
        return Response.status(Response.Status.OK).entity(gradesList).build();
    }

    @GET
    @Path("/{subject}/students")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectStudents(@PathParam("subject") String subjectName) {
        List<StudentModel> studentsList = mongoBase.subjectStudents(subjectName);
        return Response.status(Response.Status.OK).entity(studentsList).build();
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
    //TODO sprawdzic dzialanie
    @DELETE
    @Path("/delete_subject/{subject}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("subject") String subjectName) {
        SubjectModel deletedSubject = mongoBase.oneSubject(subjectName);
        mongoBase.deleteSubject(deletedSubject);
        return Response.status(Response.Status.OK).build();
    }
}
