package com.pl.project.services;

import com.mongodb.MongoClient;
import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.ArrayList;
import java.util.List;

public class MongoBase {

    private Morphia morphia;
    private Datastore datastore;

    private MongoBase() {
        morphia = new Morphia();

        // tell Morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.map(StudentModel.class);
        morphia.map(GradeModel.class);
        morphia.map(SubjectModel.class);
        //morphia.getMapper().getOptions().isStoreEmpties() = true;
        datastore = morphia.createDatastore(new MongoClient("localhost", 8004), "WSI");
        morphia.mapPackage("com.pl.project.models");
        //datastore.getDB().dropDatabase();
        datastore.ensureIndexes();
    }

    private static class MongoBaseHandler {
        private static final MongoBase mongoBase = new MongoBase();
    }

    public static MongoBase getInstance() {
        return MongoBaseHandler.mongoBase;
    }

    public void addStudent(StudentModel studentModel) {
        datastore.save(studentModel);
    }

    public void deleteStudent(StudentModel studentModel) {
        datastore.delete(studentModel);
    }

    public void updateStudent(StudentModel studentModel) {
        Query<StudentModel> updateQuery = datastore.createQuery(StudentModel.class).field("_id").equal(studentModel.getIndex());
        UpdateOperations<StudentModel> update = datastore.createUpdateOperations(StudentModel.class).set("name", "Nowy");
        datastore.update(updateQuery, update);
    }

    public void addGrade(GradeModel gradeModel) {
        datastore.save(gradeModel);
    }

    public void deleteGrade(GradeModel gradeModel) {
        datastore.delete(gradeModel);
    }

    public void updateGrade(GradeModel gradeModel) {
        Query<GradeModel> updateQuery = datastore.createQuery(GradeModel.class).field("_id").equal(gradeModel.getId());
        UpdateOperations<GradeModel> update = datastore.createUpdateOperations(GradeModel.class).set("gradeValue", 3.5D);
        datastore.update(updateQuery, update);
    }

    public void addSubject(SubjectModel subjectModel) {
        datastore.save(subjectModel);
    }

    public void deleteSubject(SubjectModel subjectModel) {
        datastore.delete(subjectModel);
    }

    public void updateSubject(SubjectModel subjectModel) {
        Query<SubjectModel> updateQuery = datastore.createQuery(SubjectModel.class).field("_id").equal(subjectModel.getId());
        UpdateOperations<SubjectModel> update = datastore.createUpdateOperations(SubjectModel.class).set("subjectName", "Przyroda");
        datastore.update(updateQuery, update);
    }

    public List<StudentModel> studentsList() {
        return datastore.find(StudentModel.class).asList();
    }

    public List<GradeModel> studentSubjectGrades(int index, String subjectName) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        Query<StudentModel> getQueryStudent = datastore.find(StudentModel.class);
        SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
        StudentModel student = getQueryStudent.field("index").equal(index).get();
        return subject.getGradesListOfStudent(student.getIndex());
    }

    public List<SubjectModel> studentSubjects(int index) {
        List<SubjectModel> subjectsList = new ArrayList<>();
        List<SubjectModel> getQuerySubjectsList = datastore.find(SubjectModel.class).asList();
        Query<StudentModel> getQueryStudent = datastore.find(StudentModel.class);
        StudentModel student = getQueryStudent.field("index").equal(index).get();
        //TODO wyciagniecie z kazdego studenta na kazdym przedmiocie indeksow, nastepnie porownanie ich z tym co maja przedmioy przypisane
        for (SubjectModel subject : getQuerySubjectsList) {
            List<StudentModel> studentList = subject.getStudentList();
            Long studentIndex = student.getIndex();
            int studentIndexInt = studentIndex.intValue();
            if (subject.getStudentList().contains(student)) {
                System.out.println("show me element: " + subject.getStudentList());
               // subjectsList.add(subject);
            }
        }
        return subjectsList;
    }


    public List<SubjectModel> subjectsList() {
        return datastore.find(SubjectModel.class).asList();
    }

    public SubjectModel oneSubject(String subject) {
        Query<SubjectModel> getQuery = datastore.find(SubjectModel.class);
        SubjectModel subjectModel = getQuery.field("subjectName").equal(subject).get();
        return subjectModel;
    }

    public List<GradeModel> subjectGrades(String subject) {
        Query<SubjectModel> getQuery = datastore.find(SubjectModel.class);
        SubjectModel querySubject = getQuery.field("subjectName").equal(subject).get();
        return querySubject.getGradeList();
    }

    public List<StudentModel> subjectStudents(String subject) {
        Query<SubjectModel> getQuery = datastore.find(SubjectModel.class);
        SubjectModel querySubject = getQuery.field("subjectName").equal(subject).get();
        return querySubject.getStudentList();
    }


    public List<GradeModel> gradesList() {
        return datastore.find(GradeModel.class).asList();
    }

}
