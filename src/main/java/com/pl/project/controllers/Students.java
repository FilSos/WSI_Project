package com.pl.project.controllers;

import com.pl.project.models.StudentModel;
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
    public Response getStudentsList() {
        List<StudentModel> studentsList = MongoBase.getInstance().studentsList();
        return Response.status(Response.Status.OK).entity(studentsList).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleStudent(@PathParam("index") int id) {
        StudentModel oneStudent = MongoBase.getInstance().studentsList().get(id);
        return Response.status(Response.Status.OK).entity(oneStudent).build();
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
    @Path("/delete_student/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("index") int id) {
        StudentModel deletedStudent = mongoBase.studentsList().get(id);
        mongoBase.deleteStudent(deletedStudent);
        return Response.status(Response.Status.OK).build();
    }

}
