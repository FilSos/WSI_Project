package com.pl.project.services;

import com.pl.project.models.GradeModel;

import java.util.Date;

public class MongoGrades {

    public void addGrade(){
        MongoBase mongoBase = MongoBase.getInstance();
        GradeModel gradeOne = new GradeModel();
        GradeModel gradeTwo = new GradeModel();
        GradeModel gradeThree = new GradeModel();
        GradeModel gradeFour = new GradeModel();
        GradeModel gradeFive = new GradeModel();
        GradeModel gradeSix = new GradeModel();
        gradeOne.setDate(new Date());
        gradeOne.setGradeValue(3.0);
        gradeOne.setStudent(mongoBase.oneStudent(115610));
        gradeTwo.setDate(new Date());
        gradeTwo.setGradeValue(3.5);
        gradeTwo.setStudent(mongoBase.oneStudent(133451));
        gradeThree.setDate(new Date());
        gradeThree.setGradeValue(4.0);
        gradeThree.setStudent(mongoBase.oneStudent(133451));
        gradeFour.setDate(new Date());
        gradeFour.setGradeValue(4.5);
        gradeFour.setStudent(mongoBase.oneStudent(112341));
        gradeFive.setDate(new Date());
        gradeFive.setGradeValue(5.0);
        gradeFive.setStudent(mongoBase.oneStudent(112377));
        gradeSix.setDate(new Date());
        gradeSix.setGradeValue(5.0);
        gradeSix.setStudent(mongoBase.oneStudent(115672));

        mongoBase.addGrade(gradeOne);
        mongoBase.addGrade(gradeTwo);
        mongoBase.addGrade(gradeThree);
        mongoBase.addGrade(gradeFour);
        mongoBase.addGrade(gradeFive);
        mongoBase.addGrade(gradeSix);
    }
}
