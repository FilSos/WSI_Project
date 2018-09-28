package com.pl.project.controllers;

import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import com.pl.project.services.MongoBase;
import com.pl.project.services.MongoSubjects;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
        List<SubjectModel> subjectsList = mongoBase.subjectsList(teacher, subjectName);
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
    @Path("/{subject}/grades/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectSpecificGrade(@PathParam("subject") String subjectName,
                                            @PathParam("id") int id) {
        GradeModel specificGrade = mongoBase.subjectSpecificGrade(subjectName, id);
        return Response.status(Response.Status.OK).entity(specificGrade).build();
    }


    @GET
    @Path("/{subject}/students")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectStudents(@PathParam("subject") String subjectName) {
        List<StudentModel> studentsList = mongoBase.subjectStudents(subjectName);
        return Response.status(Response.Status.OK).entity(studentsList).build();
    }

    @GET
    @Path("/{subject}/students/{index}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectSpecificStudent(@PathParam("subject") String subjectName,
                                              @PathParam("index") Long index) {
        StudentModel specificStudent = mongoBase.subjectSpecificStudent(subjectName, index);
        return Response.status(Response.Status.OK).entity(specificStudent).build();
    }

    @GET
    @Path("/{subject}/students/{index}/grades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectSpecificStudentGrades(@PathParam("subject") String subjectName,
                                                    @PathParam("index") Long index) {
        List<GradeModel> gradesList = mongoBase.studentSubjectGradesList(index, subjectName);
        return Response.status(Response.Status.OK).entity(gradesList).build();
    }

    @GET
    @Path("/{subject}/students/{index}/grades/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectSpecificStudentSpecificGrade(@PathParam("subject") String subjectName,
                                                           @PathParam("index") Long index,
                                                           @PathParam("id") int id) {
        GradeModel specificGrade = mongoBase.studentSubjectSpecificGrade(index, subjectName, id);
        return Response.status(Response.Status.OK).entity(specificGrade).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSubject(SubjectModel subjectModel, @Context UriInfo uriInfo) {
        //mongoSubjects.addSubject();
        mongoBase.addSubject(subjectModel);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(subjectModel.getSubjectName());
        return Response.created(builder.build()).build();
    }

    @POST
    @Path("/{subject}/students")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addStudentToSubject(StudentModel studentModel,@PathParam("subject") String subjectName, @Context UriInfo uriInfo) {
        //mongoStudents.addStudent();
        mongoBase.addStudentToSubject(studentModel,subjectName);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(studentModel.getIndex()));
        return Response.created(builder.build()).build();
    }


    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateSubject(SubjectModel subjectModel, @Context UriInfo uriInfo) {
        mongoBase.updateSubject(subjectModel);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(subjectModel.getSubjectName());
        return Response.created(builder.build()).build();

    }

    @DELETE
    @Path("/{subject}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("subject") String subjectName) {
        SubjectModel deletedSubject = mongoBase.oneSubject(subjectName);
        mongoBase.deleteSubject(deletedSubject);
        return Response.status(Response.Status.OK).build();
    }
}
