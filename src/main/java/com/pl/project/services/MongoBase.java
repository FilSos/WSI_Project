package com.pl.project.services;

import com.mongodb.MongoClient;
import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import javax.management.remote.SubjectDelegationPermission;
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

    //TODO dziala, ale sypie errorami
    public void updateStudent(StudentModel student) {
        System.out.println("GET ALL VALUES:" + student.getIndex() + "\n" + "name: " + student.getName());
        Query<StudentModel> updateQuery = datastore.createQuery(StudentModel.class).field("index").equal(student.getIndex());
        if (!student.getName().isEmpty()) {
            System.out.println("WESZLO 1");
            final UpdateOperations<StudentModel> updateName = datastore.createUpdateOperations(StudentModel.class).set("name", student.getName());
            datastore.update(updateQuery, updateName);
        }

        if (!student.getSurname().isEmpty()) {
            System.out.println("WESZLO 2");
            final UpdateOperations<StudentModel> updateSurname = datastore.createUpdateOperations(StudentModel.class).set("surname", student.getSurname());
            datastore.update(updateQuery, updateSurname);
        }
        if (!student.getBirthday().toString().isEmpty()) {
            System.out.println("WESZLO 3");
            final UpdateOperations<StudentModel> updateBirthday = datastore.createUpdateOperations(StudentModel.class).set("birthday", student.getBirthday());
            datastore.update(updateQuery, updateBirthday);
        }
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
        List<StudentModel> studentsList = new ArrayList<>();
        List<GradeModel> gradeList = new ArrayList<>();
        subjectModel.setStudentList(studentsList);
        subjectModel.setGradeList(gradeList);
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

    public List<StudentModel> studentsList(String name, String surname) {
        Query<StudentModel> studentModelQuery = datastore.createQuery(StudentModel.class);
        if (!name.equals("") && !surname.equals("")) {
            studentModelQuery.and(studentModelQuery.criteria("name").containsIgnoreCase(name),
                    studentModelQuery.criteria("surname").containsIgnoreCase(surname));
            return studentModelQuery.asList();

        } else if (!name.equals("")) {
            return studentModelQuery.field("name").equal(name).asList();
        } else if (!surname.equals("")) {
            return studentModelQuery.field("surname").equal(surname).asList();
        }

        return datastore.find(StudentModel.class).asList();
    }

    //TODO data sie nie waliduje, sprawdz czemu
    public List<StudentModel> studentsListByDate(String before, String in, String after) {
        Query<StudentModel> studentModelQuery = datastore.createQuery(StudentModel.class);
        if (!in.equals("")) {
            return studentModelQuery.field("birthday").equal(in).asList();
        } else if (!after.equals("")) {
            return studentModelQuery.field("birthday").greaterThan(after).asList();
        } else if (!before.equals("")) {
            return studentModelQuery.field("birthday").lessThan(before).asList();
        }
        return datastore.find(StudentModel.class).asList();
    }

    public StudentModel oneStudent(int index) {
        System.out.println("Show me index: " + index);
        Query<StudentModel> getQuery = datastore.find(StudentModel.class);
        StudentModel studentModel = getQuery.field("index").equal(index).get();
        System.out.println("Show me the student: " + studentModel);
        return studentModel;
    }

    public List<GradeModel> studentSubjectGrades(int index, String subjectName) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        Query<StudentModel> getQueryStudent = datastore.find(StudentModel.class);
        SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
        StudentModel student = getQueryStudent.field("index").equal(index).get();
        return subject.getGradesListOfStudent(student.getIndex());
    }

    //TODO dziala, ale brzydko napisane - do poprawki
    public List<SubjectModel> studentSubjects(int index) {
        List<SubjectModel> subjectsList = new ArrayList<>();
        List<SubjectModel> getQuerySubjectsList = datastore.find(SubjectModel.class).asList();
        Query<StudentModel> getQueryStudent = datastore.find(StudentModel.class);
        StudentModel student = getQueryStudent.field("index").equal(index).get();
        Long studentIndex = student.getIndex();
        int studentIndexInt = studentIndex.intValue();
        for (SubjectModel subject : getQuerySubjectsList) {
            List<StudentModel> studentList = subject.getStudentList();
            for (StudentModel oneStudent : studentList) {
                Long oneStudentIndex = oneStudent.getIndex();
                int oneStudentIndexInt = oneStudentIndex.intValue();
                if (studentIndexInt == oneStudentIndexInt) {
                    subjectsList.add(subject);
                }
            }
        }
        return subjectsList;
    }

    public SubjectModel studentSpecificSubject(Long index, String subjectName) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        Query<StudentModel> getQueryStudent = datastore.find(StudentModel.class);
        StudentModel student = getQueryStudent.field("index").equal(index).get();
        if (index.equals(student.getIndex())) {
            SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
            return subject;
        }else{
            return null;
        }
    }

    //TODO sprawdzic czy dziala poprawnie
    public List<SubjectModel> subjectsList(String teacher, String subjectName) {
        final Query<SubjectModel> query = datastore.createQuery(SubjectModel.class);
        if (!teacher.equals("")) {
            query.field("teacher").equal(teacher);
        }

        if (!subjectName.equals("")) {
            return query.field("subjectName").equal(subjectName).asList();
        }

        return query.asList();
    }

    public SubjectModel oneSubject(String subject) {
        Query<SubjectModel> getQuery = datastore.find(SubjectModel.class);
        return getQuery.field("subjectName").equal(subject).get();
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

    //TODO sprawdzic dzialanie
    public List<GradeModel> studentSubjectGradesList(Long index, String subjectName) {
        final Query<SubjectModel> subjectQuery = datastore.createQuery(SubjectModel.class);
//        final Query<GradeModel> gradeQuery = datastore.createQuery(GradeModel.class);
//        final Query<StudentModel> studentQuery = datastore.createQuery(StudentModel.class);
        if (!"".equals(subjectName)) {
            SubjectModel subject = subjectQuery.field("subjectName").equal(subjectName).get();
            return subject.getGradesListOfStudent(index);
        } else {
            return datastore.find(GradeModel.class).asList();
        }

    }

    public List<GradeModel> studentAboveOrBelowGradesList(Long index, String above, String below) {
        final Query<GradeModel> gradeQuery = datastore.createQuery(GradeModel.class);
        final Query<StudentModel> studentQuery = datastore.createQuery(StudentModel.class);
        StudentModel student = studentQuery.field("index").equal(index).get();
        if (!above.equals("")) {
            gradeQuery.and(gradeQuery.criteria("gradeValue").greaterThan(above),
                    gradeQuery.criteria("referencedStudent").equal(student));
            return gradeQuery.asList();
        } else if (!below.equals("")) {
            gradeQuery.and(gradeQuery.criteria("gradeValue").lessThan(below),
                    gradeQuery.criteria("referencedStudent").equal(student));
            return gradeQuery.asList();
        }
        return null;
    }

    //TODO sprawdzic
    public GradeModel oneGrade(int id) {
        Query<GradeModel> getQuery = datastore.find(GradeModel.class);
        GradeModel gradeModel = getQuery.field("_id").equal(id).get();
        return gradeModel;
    }
}
