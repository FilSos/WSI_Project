package com.pl.project.services;

import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;

import java.util.ArrayList;
import java.util.List;


public class MongoSubjects {

    public void addSubject() {
        MongoBase mongoBase = MongoBase.getInstance();

        SubjectModel subjectOne = new SubjectModel();
        SubjectModel subjectTwo = new SubjectModel();
        SubjectModel subjectThree = new SubjectModel();
        //TODO zmienic motode wyciagania ocen, gdyz w tej chwili podanie indexu bez nazwy przedmiotu i tak zwraca cala liste
        List<StudentModel> studentsList = mongoBase.studentsList("", "");
        List<GradeModel> gradeList = mongoBase.studentSubjectGradesList(115610L,"");

        subjectOne.setSubjectName("Matematyka");
        subjectOne.setTeacher("Jan Kowalski");
        subjectOne.setGradeList(gradeList);
        subjectOne.setStudentList(studentsList);

        subjectTwo.setSubjectName("Polski");
        subjectTwo.setTeacher("Krzysztof Kolumb");
        subjectTwo.setGradeList(gradeList);
        subjectTwo.setStudentList(studentsList);

        subjectThree.setSubjectName("Historia");
        subjectThree.setTeacher("Robert Szafran");
        subjectThree.setGradeList(gradeList);
        subjectThree.setStudentList(studentsList);

        mongoBase.addSubject(subjectOne);
        mongoBase.addSubject(subjectTwo);
        mongoBase.addSubject(subjectThree);
    }
}
