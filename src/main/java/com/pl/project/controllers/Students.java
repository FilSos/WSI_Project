package com.pl.project.controllers;

import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import com.pl.project.services.MongoBase;
import com.pl.project.services.MongoStudents;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @GET
    @Path("/{index}/{subject}/grades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentGradesFromSubject(@PathParam("index") int index, @PathParam("subject") String subjectName) {
        List<GradeModel> studentGradesList = mongoBase.studentSubjectGrades(index, subjectName);
        return Response.status(Response.Status.OK).entity(studentGradesList).build();
    }

    @GET
    @Path("/{index}/subjects")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentSubjects(@PathParam("index") int index) {
        List<SubjectModel> studentSubjectsList = mongoBase.studentSubjects(index);
        return Response.status(Response.Status.OK).entity(studentSubjectsList).build();
    }


    @POST
    @Path("/add_student")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createStudent(StudentModel studentModel) {
        mongoBase.addStudent(studentModel);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/update_student")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response.ResponseBuilder updateStudent(StudentModel studentModel) {
        mongoBase.updateStudent(studentModel);
        return Response.status(Response.Status.OK);

    }


    @DELETE
    @Path("/delete_student/{index}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("index") int index) {
        StudentModel deletedStudent = mongoBase.oneStudent(index);
        mongoBase.deleteStudent(deletedStudent);
        return Response.status(Response.Status.OK).build();
    }

}
