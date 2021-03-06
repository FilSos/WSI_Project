package com.pl.project.controllers;

import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import com.pl.project.services.MongoBase;
import com.pl.project.services.MongoGrades;
import com.pl.project.services.MongoStudents;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.text.ParseException;
import java.util.List;

@Path("/students")
public class Students {
    //Unlock only if adding first record, otherwise use mongoBase instance
    // MongoStudents mongoStudents = new MongoStudents();
    MongoGrades mongoGrades = new MongoGrades();
    MongoBase mongoBase = MongoBase.getInstance();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentsList(
            @DefaultValue("") @QueryParam("name") String name,
            @DefaultValue("") @QueryParam("surname") String surname,
            @DefaultValue("") @QueryParam("before") String before,
            @DefaultValue("") @QueryParam("in") String in,
            @DefaultValue("") @QueryParam("after") String after) throws ParseException {
        if ((!name.equals("") && !surname.equals("")) || (name.equals("") && !surname.equals("")) || (!name.equals("") && surname.equals(""))) {
            List<StudentModel> studentsList = mongoBase.studentsList(name, surname);
            return Response.status(Response.Status.OK).entity(studentsList).build();
        } else {
            List<StudentModel> studentsListByDate = mongoBase.studentsListByDate(before, in, after);
            GenericEntity<List<StudentModel>> wrapper = new GenericEntity<List<StudentModel>>(studentsListByDate){
            };
            return Response.status(Response.Status.OK).entity(wrapper).build();
        }
    }

    @GET
    @Path("/{index}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleStudent(@PathParam("index") Long index) {
        StudentModel oneStudent = mongoBase.oneStudent(index);
        return Response.status(Response.Status.OK).entity(oneStudent).build();
    }

    @GET
    @Path("/{index}/subjects")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentSubjects(@PathParam("index") int index) {
        List<SubjectModel> studentSubjectsList = mongoBase.studentSubjects(index);
        return Response.status(Response.Status.OK).entity(studentSubjectsList).build();
    }

    @GET
    @Path("/{index}/subjects/{subject}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentSpecificSubject(@PathParam("index") Long index, @PathParam("subject") String subjectName) {
        SubjectModel studentSpecificSubject = mongoBase.studentSpecificSubject(index, subjectName);
        return Response.status(Response.Status.OK).entity(studentSpecificSubject).build();
    }


    @GET
    @Path("/{index}/subjects/{subject}/grades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentGradesFromSubject(@PathParam("index") int index, @PathParam("subject") String subjectName) {
        List<GradeModel> studentGradesList = mongoBase.studentSubjectGrades(index, subjectName);
        return Response.status(Response.Status.OK).entity(studentGradesList).build();
    }


    @GET
    @Path("/{index}/subjects/{subject}/grades/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentSpecificGradeFromSubject(@PathParam("index") Long index,
                                                       @PathParam("subject") String subjectName,
                                                       @PathParam("id") int id) {
        GradeModel studentSpecificGrade = mongoBase.studentSubjectSpecificGrade(index, subjectName, id);
        return Response.status(Response.Status.OK).entity(studentSpecificGrade).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createStudent(StudentModel studentModel, @Context UriInfo uriInfo) {

        mongoBase.createStudent(studentModel);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(studentModel.getIndex()));
        return Response.created(builder.build()).build();
    }

    //Test records, unlock only if need it
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Response createTestStudents() {
//        mongoStudents.addStudent();
//        return Response.status(Response.Status.OK).build();
//
//    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateStudent(StudentModel studentModel, @Context UriInfo uriInfo) {
        mongoBase.updateStudent(studentModel);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(studentModel.getIndex()));
        return Response.created(builder.build()).build();

    }

    @DELETE
    @Path("/{index}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("index") Long index) {
        StudentModel deletedStudent = mongoBase.oneStudent(index);
        mongoBase.deleteStudent(deletedStudent);
        return Response.status(Response.Status.OK).build();
    }
}
