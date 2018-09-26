package com.pl.project.controllers;

import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import com.pl.project.services.MongoBase;
import com.pl.project.services.MongoStudents;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
//TODO resolver do bledu empty beans
@Path("/students")
public class Students {
    //Unlock only if adding first record, otherwise use mongoBase instance
    //MongoStudents mongoStudents = new MongoStudents();
    MongoBase mongoBase = MongoBase.getInstance();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentsList(
            @DefaultValue("") @QueryParam("name") String name,
            @DefaultValue("") @QueryParam("surname") String surname,
            @DefaultValue("") @QueryParam("before") String before,
            @DefaultValue("") @QueryParam("in") String in,
            @DefaultValue("") @QueryParam("after") String after) {
        if ((!name.equals("") && !surname.equals("")) || (name.equals("") && !surname.equals("")) || (!name.equals("") && surname.equals(""))) {
            List<StudentModel> studentsList = mongoBase.studentsList(name, surname);
            return Response.status(Response.Status.OK).entity(studentsList).build();
        } else {
            List<StudentModel> studentsListByDate = mongoBase.studentsListByDate(before, in, after);
            return Response.status(Response.Status.OK).entity(studentsListByDate).build();
        }
    }

    @GET
    @Path("/{index}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleStudent(@PathParam("index") int index) {
        StudentModel oneStudent = mongoBase.oneStudent(index);
        return Response.status(Response.Status.OK).entity(oneStudent).build();
    }
    //TODO poprawic implementacje
    @GET
    @Path("/{index}/subjects")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentSubjects(@PathParam("index") int index) {
        List<SubjectModel> studentSubjectsList = mongoBase.studentSubjects(index);
        return Response.status(Response.Status.OK).entity(studentSubjectsList).build();
    }
    //TODO zaimplementowac
    @GET
    @Path("/{index}/subjects/{subject}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentSpecificSubject(@PathParam("index") int index) {
        List<SubjectModel> studentSubjectsList = mongoBase.studentSubjects(index);
        return Response.status(Response.Status.OK).entity(studentSubjectsList).build();
    }


    @GET
    @Path("/{index}/subjects/{subject}/grades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentGradesFromSubject(@PathParam("index") int index, @PathParam("subject") String subjectName) {
        List<GradeModel> studentGradesList = mongoBase.studentSubjectGrades(index, subjectName);
        return Response.status(Response.Status.OK).entity(studentGradesList).build();
    }
    //TODO Zaimplementowac
    @GET
    @Path("/{index}/subjects/{subject}/grades/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentSpecificGradeFromSubject(@PathParam("index") int index, @PathParam("subject") String subjectName) {
        List<GradeModel> studentGradesList = mongoBase.studentSubjectGrades(index, subjectName);
        return Response.status(Response.Status.OK).entity(studentGradesList).build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createStudent(StudentModel studentModel, @Context UriInfo uriInfo) {
        mongoBase.addStudent(studentModel);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(studentModel.getIndex()));
        return Response.created(builder.build()).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response.ResponseBuilder updateStudent(StudentModel studentModel) {
        mongoBase.updateStudent(studentModel);
        return Response.status(Response.Status.OK);

    }


    @DELETE
    @Path("/{index}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("index") int index) {
        StudentModel deletedStudent = mongoBase.oneStudent(index);
        mongoBase.deleteStudent(deletedStudent);
        return Response.status(Response.Status.OK).build();
    }

}
