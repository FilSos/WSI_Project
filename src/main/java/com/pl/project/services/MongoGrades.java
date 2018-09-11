package com.pl.project.services;

import com.pl.project.models.GradeModel;
import com.pl.project.models.GradesValue;

import java.util.Date;

public class MongoGrades {

    public void addGrade(){
        MongoBase mongoBase = MongoBase.getInstance();
        GradeModel gradeOne = new GradeModel();
        GradeModel gradeTwo = new GradeModel();
        GradeModel gradeThree = new GradeModel();
        GradeModel gradeFour = new GradeModel();
        GradeModel gradeFive = new GradeModel();
        gradeOne.setDate(new Date());
        gradeOne.setGradeValue(3.0);
        gradeOne.setStudent(mongoBase.studentsList().get(0));
        gradeTwo.setDate(new Date());
        gradeTwo.setGradeValue(3.5);
        gradeTwo.setStudent(mongoBase.studentsList().get(0));
        gradeThree.setDate(new Date());
        gradeThree.setGradeValue(4.0);
        gradeThree.setStudent(mongoBase.studentsList().get(1));
        gradeFour.setDate(new Date());
        gradeFour.setGradeValue(4.5);
        gradeFour.setStudent(mongoBase.studentsList().get(1));
        gradeFive.setDate(new Date());
        gradeFive.setGradeValue(5.0);
        gradeFive.setStudent(mongoBase.studentsList().get(2));

        mongoBase.addGrade(gradeOne);
        mongoBase.addGrade(gradeTwo);
        mongoBase.addGrade(gradeThree);
        mongoBase.addGrade(gradeFour);
        mongoBase.addGrade(gradeFive);
    }
}
