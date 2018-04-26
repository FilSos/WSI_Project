package com.pl.project.controllers;

import com.pl.project.models.StudentModel;
import com.pl.project.services.MongoBase;
import com.pl.project.services.MongoStudents;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("/students")
public class Students {

    MongoStudents mongoStudents = new MongoStudents();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentsList() {
        List<StudentModel> studentsList = MongoBase.getInstance().studentsList();
        return Response.status(Response.Status.OK).entity(studentsList).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleStudent(@PathParam("id") int id) {
        StudentModel oneStudent = MongoBase.getInstance().studentsList().get(id);
        return Response.status(Response.Status.OK).entity(oneStudent).build();
    }

    @POST
    @Path("/add_student")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createStudent() {
        MongoBase mongoBase = MongoBase.getInstance();
        Date date = new Date();
        mongoStudents.addStudent();
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/delete_student/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("id") int id) {
        MongoBase mongoBase = MongoBase.getInstance();
        StudentModel deletedStudent = mongoBase.studentsList().get(id);
        mongoBase.deleteStudent(deletedStudent);
        return Response.status(Response.Status.OK).build();
    }

}
