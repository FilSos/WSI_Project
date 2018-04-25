package com.pl.project.repositories.MongoRepository;

import com.pl.project.models.GradeModel;
import com.pl.project.models.GradesValue;

import java.util.Date;

public class MongoGrades {

    public void addGrade(int studentId, int subjectId){
        MongoBase mongoBase = MongoBase.getInstance();
        GradeModel gradeOne = new GradeModel();
        GradeModel gradeTwo = new GradeModel();
        GradeModel gradeThree = new GradeModel();
        GradeModel gradeFour = new GradeModel();
        GradeModel gradeFive = new GradeModel();
        gradeOne.setDate(new Date());
        gradeOne.setGradeValue(GradesValue.GRADE_THREE);
        gradeOne.setStudent(mongoBase.studentsList().get(studentId));
        gradeOne.setSubject(mongoBase.subjectsList().get(subjectId));
        gradeTwo.setDate(new Date());
        gradeTwo.setGradeValue(GradesValue.GRADE_FIVE);
        gradeTwo.setStudent(mongoBase.studentsList().get(studentId));
        gradeTwo.setSubject(mongoBase.subjectsList().get(subjectId));
        gradeThree.setDate(new Date());
        gradeThree.setGradeValue(GradesValue.GRADE_FOUR);
        gradeThree.setStudent(mongoBase.studentsList().get(studentId));
        gradeThree.setSubject(mongoBase.subjectsList().get(subjectId));
        gradeFour.setDate(new Date());
        gradeFour.setGradeValue(GradesValue.GRADE_ONE);
        gradeFour.setStudent(mongoBase.studentsList().get(studentId));
        gradeFour.setSubject(mongoBase.subjectsList().get(subjectId));
        gradeFive.setDate(new Date());
        gradeFive.setGradeValue(GradesValue.GRADE_TWO);
        gradeFive.setStudent(mongoBase.studentsList().get(studentId));
        gradeFive.setSubject(mongoBase.subjectsList().get(subjectId));

        mongoBase.addGrade(gradeOne);
        mongoBase.addGrade(gradeTwo);
        mongoBase.addGrade(gradeThree);
        mongoBase.addGrade(gradeFour);
        mongoBase.addGrade(gradeFive);
    }
}
