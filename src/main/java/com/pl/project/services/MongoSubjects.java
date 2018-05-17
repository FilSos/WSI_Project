package com.pl.project.services;

import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

public class MongoSubjects {

    public void addSubject() {
        MongoBase mongoBase = MongoBase.getInstance();

        SubjectModel subjectOne = new SubjectModel();
        SubjectModel subjectTwo = new SubjectModel();
        SubjectModel subjectThree = new SubjectModel();

        subjectOne.setName("Matematyka");
        subjectOne.setTeacher("Jan Kowalski");

        subjectTwo.setName("Polski");
        subjectTwo.setTeacher("Krzysztof Kolumb");

        subjectThree.setName("Historia");
        subjectThree.setTeacher("Robert Szafran");

        mongoBase.addSubject(subjectOne);
        mongoBase.addSubject(subjectTwo);
        mongoBase.addSubject(subjectThree);
    }
    //TODO Zamienic na DTO i powycinac adnotacjami z object mappera @JsonIgnore rzeczy ktorych nie chemy miec podczas wyswietlania np. przedmiot
    public List<StudentModel> studentsOnSubject(ObjectId id){

        List<StudentModel> studentsList = MongoBase.getInstance().studentsList();

        List<StudentModel> studentsOnSubject = studentsList.stream().filter(student -> id.equals(student.getSubject().getId())).collect(Collectors.toList());
        return studentsOnSubject;
    }

//    public List<GradeModel> studentOnSubjectGrades(ObjectId id, ObjectId id2){
//
//        List<GradeModel> gradesList = MongoBase.getInstance().gradesList();
//
//        List<GradeModel> studentOnSubjectGrades = gradesList.stream().filter(grade ->
//        {
//            id.equals(grade.getSubject().getId()),
//            id2.equals(grade.getStudent().getId()).collect(Collectors.toList())});
//        return studentOnSubjectGrades;
//    }
}
