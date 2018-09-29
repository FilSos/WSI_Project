package com.pl.project.services;

import com.mongodb.MongoClient;
import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public void createStudentAndAddToSubject(StudentModel studentModel, String subjectName) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
        datastore.save(studentModel);
        subject.getStudentList().add(studentModel);
        datastore.save(subject);
    }

    public void createStudent(StudentModel studentModel) {
        datastore.save(studentModel);
    }

    //TODO REST dziala, sprawdzic JS
    public void deleteStudent(StudentModel studentModel) {
        List<SubjectModel> getQuerySubjectsList = datastore.find(SubjectModel.class).asList();
        List<GradeModel> getQueryGradesList = datastore.find(GradeModel.class).asList();
        Long index = studentModel.getIndex();
        for (int i = 0; i < getQuerySubjectsList.size(); i++) {
            for (int j = 0; j < getQuerySubjectsList.get(i).getStudentList().size(); j++) {
                if (getQuerySubjectsList.get(i).getStudentList().get(j).getIndex().equals(index)) {
                    for (int k = 0; k < getQuerySubjectsList.get(i).getGradeList().size(); k++) {
                        if (getQuerySubjectsList.get(i).getGradeList().get(k).getStudent().getIndex().equals(index)) {
                            getQuerySubjectsList.get(i).getGradeList().remove(getQuerySubjectsList.get(i).getGradeList().get(k));
                            datastore.save(getQuerySubjectsList.get(i));
                        }
                    }
                    getQuerySubjectsList.get(i).getStudentList().remove(getQuerySubjectsList.get(i).getStudentList().get(j));
                    datastore.save(getQuerySubjectsList.get(i));
                }
            }
        }

        for (int m = 0; m < getQueryGradesList.size(); m++) {
            if (getQueryGradesList.get(m).getStudent().getIndex().equals(index)) {
                ObjectId gradeId = getQueryGradesList.get(m).getId();
                datastore.delete(getQueryGradesList.get(m));
            }
        }
        datastore.delete(studentModel);
    }

    //TODO REST ok, sprawdzic JS
    public void addStudentToSubject(StudentModel studentModel, String subjectName) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        Query<StudentModel> getQueryStudent = datastore.find(StudentModel.class);
        SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
        StudentModel student = getQueryStudent.field("index").equal(studentModel.getIndex()).get();
        subject.getStudentList().add(student);
        datastore.save(subject);
    }

    //TODO REST ok, sprawdzic JS
    public void deleteStudentFromSubject(String subjectName, Long index) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
        for (int i = 0; i < subject.getStudentList().size(); i++) {
            if (subject.getStudentList().get(i).getIndex().equals(index)) {
                subject.getStudentList().remove(i);
                datastore.save(subject);
            }
        }
    }


    public void updateStudent(StudentModel student) {
        Query<StudentModel> updateQuery = datastore.createQuery(StudentModel.class).field("index").equal(student.getIndex());
        if (!student.getName().isEmpty()) {
            final UpdateOperations<StudentModel> updateName = datastore.createUpdateOperations(StudentModel.class).set("name", student.getName());
            datastore.update(updateQuery, updateName);
        }
        if (!student.getSurname().isEmpty()) {
            final UpdateOperations<StudentModel> updateSurname = datastore.createUpdateOperations(StudentModel.class).set("surname", student.getSurname());
            datastore.update(updateQuery, updateSurname);
        }
        if (!student.getBirthday().toString().isEmpty()) {
            final UpdateOperations<StudentModel> updateBirthday = datastore.createUpdateOperations(StudentModel.class).set("birthday", student.getBirthday());
            datastore.update(updateQuery, updateBirthday);
        }
    }

    public void addGrade(GradeModel gradeModel, Long index, String subjectName) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        Query<StudentModel> getQueryStudent = datastore.find(StudentModel.class);
        StudentModel student = getQueryStudent.field("index").equal(index).get();
        SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
        gradeModel.setStudent(student);
        datastore.save(gradeModel);
        subject.getGradeList().add(gradeModel);
        datastore.save(subject);
    }

    public void deleteGrade(GradeModel gradeModel, String subjectName, int id) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
        datastore.delete(gradeModel);
        subject.getGradeList().remove(id);
        datastore.save(subject);
    }

    //TODO REST ok, sprawdzic JS
    public void updateGrade(GradeModel gradeModel, Long index, String subjectName) {
        if (gradeModel.getStudent().getIndex().equals(index)) {
            Query<GradeModel> updateQuery = datastore.createQuery(GradeModel.class).field("id").equal(gradeModel.getId());
            UpdateOperations<GradeModel> update = datastore.createUpdateOperations(GradeModel.class).set("gradeValue", gradeModel.getGradeValue());
            datastore.update(updateQuery, update);
        }
    }

    public void addSubject(SubjectModel subjectModel) {
        List<StudentModel> studentsList = new ArrayList<>();
        List<GradeModel> gradeList = new ArrayList<>();
        subjectModel.setStudentList(studentsList);
        subjectModel.setGradeList(gradeList);
        datastore.save(subjectModel);
    }

    //TODO RESR dziala, sprawdzic JS
    public void deleteSubject(SubjectModel subjectModel) {
        List<GradeModel> gradeList = subjectModel.getGradeList();
        for (GradeModel grade : gradeList) {
            datastore.delete(grade);
        }
        datastore.delete(subjectModel);
    }

    //TODO REST dziala, sprawdzic JS
    public void updateSubject(SubjectModel subjectModel) {
        Query<SubjectModel> updateQuery = datastore.createQuery(SubjectModel.class).field("subjectName").equal(subjectModel.getSubjectName());
        UpdateOperations<SubjectModel> update = datastore.createUpdateOperations(SubjectModel.class).set("teacher", subjectModel.getTeacher());
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
        System.out.println("Zwrociło cala liste studentow");
        return datastore.find(StudentModel.class).asList();
    }

    public List<StudentModel> studentsListByDate(String before, String in, String after) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Query<StudentModel> studentModelQuery = datastore.createQuery(StudentModel.class);
        if (!in.equals("")) {
            Date inDate = format.parse(in);
            return studentModelQuery.field("birthday").equal(inDate).asList();
        } else if (!after.equals("")) {
            Date afterDate = format.parse(after);
            return studentModelQuery.field("birthday").greaterThan(afterDate).asList();
        } else if (!before.equals("")) {
            Date beforeDate = format.parse(before);
            return studentModelQuery.field("birthday").lessThan(beforeDate).asList();
        }
        return datastore.find(StudentModel.class).asList();
    }

    public StudentModel oneStudent(Long index) {
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

    public GradeModel studentSubjectSpecificGrade(Long index, String subjectName, int id) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        Query<StudentModel> getQueryStudent = datastore.find(StudentModel.class);
        SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
        StudentModel student = getQueryStudent.field("index").equal(index).get();
        List<GradeModel> gradesListOfStudent = subject.getGradesListOfStudent(student.getIndex());
        return gradesListOfStudent.get(id);
    }

    //TODO do ewentualnej poprawy, gdyz dziala
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
        } else {
            return null;
        }
    }

    public List<SubjectModel> subjectsList(String teacher) {
        final Query<SubjectModel> query = datastore.createQuery(SubjectModel.class);
        if (!teacher.equals("")) {
            query.field("teacher").equal(teacher);
            return query.asList();
        }
        return datastore.find(SubjectModel.class).asList();
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

    public GradeModel subjectSpecificGrade(String subject, int id) {
        Query<SubjectModel> getQuery = datastore.find(SubjectModel.class);
        SubjectModel querySubject = getQuery.field("subjectName").equal(subject).get();
        List<GradeModel> gradeList = querySubject.getGradeList();
        return gradeList.get(id);
    }

    public List<StudentModel> subjectStudents(String subject) {
        Query<SubjectModel> getQuery = datastore.find(SubjectModel.class);
        SubjectModel querySubject = getQuery.field("subjectName").equal(subject).get();
        return querySubject.getStudentList();
    }

    public StudentModel subjectSpecificStudent(String subject, Long index) {
        Query<SubjectModel> getQuery = datastore.find(SubjectModel.class);
        SubjectModel querySubject = getQuery.field("subjectName").equal(subject).get();
        List<StudentModel> studentList = querySubject.getStudentList();
        for (StudentModel s : studentList) {
            if (s.getIndex().equals(index)) {
                return s;
            }
        }
        return null;
    }


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
            double aboveValue = Double.parseDouble(above);
            gradeQuery.and(gradeQuery.criteria("gradeValue").greaterThan(aboveValue),
                    gradeQuery.criteria("student").equal(student));
            return gradeQuery.asList();
        } else if (!below.equals("")) {
            double belowValue = Double.parseDouble(below);
            gradeQuery.and(gradeQuery.criteria("gradeValue").lessThan(belowValue),
                    gradeQuery.criteria("student").equal(student));
            return gradeQuery.asList();
        }
        return null;
    }

    public GradeModel oneGrade(int id, String subjectName) {
        Query<SubjectModel> getQuerySubject = datastore.find(SubjectModel.class);
        SubjectModel subject = getQuerySubject.field("subjectName").equal(subjectName).get();
        return subject.getGradeList().get(id);
    }
}
