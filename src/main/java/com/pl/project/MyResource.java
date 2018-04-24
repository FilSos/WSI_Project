package com.pl.project;

import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import com.pl.project.repositories.GradesRepository;
import com.pl.project.repositories.MongoRepository.MongoBase;
import com.pl.project.repositories.MongoRepository.MongoStudents;
import com.pl.project.repositories.StudentsRepository;
import com.pl.project.repositories.SubjectsRepository;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Path("/project")
public class MyResource {

//    GradesRepository gradesRepository = new GradesRepository();
//    StudentsRepository studentsRepository = new StudentsRepository();
//    SubjectsRepository subjectsRepository = new SubjectsRepository();
//    @GET
//    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
//    @Path("/students")
//    public List<StudentModel> getStudentsList() {
//        List<StudentModel> studentsList = studentsRepository.getStudentsList();
//        return studentsList;
//    }
//    @GET
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Path("/grades")
//    public List<GradeModel> getGrades() {
//       return gradesRepository.getGradesList();
//    }
//    @GET
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Path("/subjects")
//    public List<SubjectModel> getSubjects() {
//       return subjectsRepository.getSubjectsList();
//    }
////    @GET
////    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    //TODO dokonczyc, zrobic opcjonalne sciezki, switch z wyborami w zleznosci od tego ile id jest podanych
////    @Path("/students/{id}{grades:(/grades/[^/]+?)?}{subjects:(/subjects/[^/]+?)?}")
////    public Response getSingleStudent(@PathParam("id") int id, @PathParam("id2") int id2) {
////        StudentModel oneStudent = studentsRepository.getStudent(id);
////        GradeModel oneGrade = gradesRepository.getGradesList().get(id2);
//
//
////        return Response.status(Response.Status.CREATED)
////                .entity(oneStudent)
////                .entity(oneGrade)
////                .build();
////
////    }
//    @GET
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Path("/grades/{id}")
//    public GradeModel getSingleGrade(@PathParam("id") int id) {
//        GradeModel oneGrade = gradesRepository.getGradesList().get(id);
//        return oneGrade;
//    }
//    @GET
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Path("/subjects/{id}")
//    public SubjectModel getSingleSubject(@PathParam("id") int id) {
//        SubjectModel oneSubject = subjectsRepository.getSubjectsList().get(id);
//        return oneSubject;
//
//    }
//
////    @GET
////    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
////    @Path("/subject/{id :(/id)?}")
////    public List<SubjectModel> getStudentSubject(@PathParam("id") int id) {
////        SubjectModel oneSubject = subjectsRepository.getSubjectsList().get(id);
////        return oneSubject;
////
////    }
//
//
//    @POST
//    @Path("/new_student")
//    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
//    public Response createStudent() {
//        mongoBase.connect();
//        StudentModel studentOne = new StudentModel();
//        studentOne.setIndex(114562);
//        studentOne.setName("Test");
//        studentOne.setSurname("Test2");
//        studentOne.setBirthday(new Date(1994));
//        mongoBase.addStudent(studentOne);
//        return Response.status(Response.Status.CREATED).build();
//    }
//    @POST
//    @Path("/new_grade")
//    public GradeModel createGrade(GradeModel newGrade) {
//        gradesRepository.createGrade(newGrade);
//        return newGrade;
//    }
//    @POST
//    @Path("/new_subject")
//    public SubjectModel createSubject(SubjectModel newSubject) {
//        subjectsRepository.createSubject(newSubject);
//        return newSubject;
//    }
//
//    @PUT
//    @Path("/update_student")
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    public Response.ResponseBuilder updateStudent (StudentModel student) {
//        MongoStudents mongoStudents = new MongoStudents();
//        mongoStudents.addStudent();
//        return Response.status(Response.Status.CREATED);
//
//    }
//    @PUT
//    @Path("/update_grade")
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    public GradeModel updateGrade (GradeModel grade) {
//        GradeModel updatedGrade = gradesRepository.getGrade(grade.getId());
//        return updatedGrade;
//    }
////    @PUT
////    @Path("/update_subject")
////    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
////    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
////    public SubjectModel updateSubject (SubjectModel subject) {
////        SubjectModel updatedSubject = subjectsRepository.getSubject(subject.getId());
////        return updatedSubject;
////    }
//
//
//    @DELETE
//    @Path("/delete_student")
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    public StudentModel deleteStudent(StudentModel deletedStudent) {
//        System.out.println(deletedStudent);
//        studentsRepository.deleteStudent(deletedStudent);
//        System.out.println("TUTAJ PUSTO:" + studentsRepository.getStudent(1));
//        return deletedStudent;
//    }
//    @DELETE
//    @Path("/delete_grade")
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    public GradeModel deleteGrade(GradeModel deletedGrade) {
//        gradesRepository.deleteGrade(deletedGrade);
//        return deletedGrade;
//    }
//    @DELETE
//    @Path("/delete_subject")
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    public SubjectModel deleteSubject(SubjectModel deletedSubject) {
//        subjectsRepository.deleteSubject(deletedSubject);
//        return deletedSubject;
//    }

}