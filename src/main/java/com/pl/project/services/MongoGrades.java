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
        gradeOne.setStudent(mongoBase.oneStudent(115610L));
        gradeTwo.setDate(new Date());
        gradeTwo.setGradeValue(3.5);
        gradeTwo.setStudent(mongoBase.oneStudent(133451L));
        gradeThree.setDate(new Date());
        gradeThree.setGradeValue(4.0);
        gradeThree.setStudent(mongoBase.oneStudent(133451L));
        gradeFour.setDate(new Date());
        gradeFour.setGradeValue(4.5);
        gradeFour.setStudent(mongoBase.oneStudent(112341L));
        gradeFive.setDate(new Date());
        gradeFive.setGradeValue(5.0);
        gradeFive.setStudent(mongoBase.oneStudent(112377L));
        gradeSix.setDate(new Date());
        gradeSix.setGradeValue(5.0);
        gradeSix.setStudent(mongoBase.oneStudent(115672L));

        mongoBase.addGrade(gradeOne,115610L,"Matematyka");
        mongoBase.addGrade(gradeTwo,133451L,"Polski");
        mongoBase.addGrade(gradeThree,133451L,"Historia");
        mongoBase.addGrade(gradeFour,112341L,"Matematyka");
        mongoBase.addGrade(gradeFive,112377L,"Historia");
        mongoBase.addGrade(gradeSix,115672L,"Polski");
    }
}
