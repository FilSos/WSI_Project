package com.pl.project.repositories;

import com.pl.project.models.GradeModel;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
public class GradesRepository {

    List<GradeModel> gradesList;


    public GradesRepository(){
        gradesList = new ArrayList<>();
        GradeModel gradeModel = new GradeModel();
        StudentsRepository studentRepository = new StudentsRepository();
        GradeModel gradeOne = new GradeModel();
        GradeModel gradeTwo = new GradeModel();
        GradeModel gradeThree = new GradeModel();
        GradeModel gradeFour = new GradeModel();
        GradeModel gradeFive = new GradeModel();
        gradeOne.setDate(LocalDate.of(1994,2,14));
        gradeOne.setGrade(3.0);
        gradeOne.setStudentId(0);
        gradeOne.setSubjectId(0);
        gradesList.add(gradeOne);
        gradeTwo.setDate(LocalDate.of(1994,2,14));
        gradeTwo.setGrade(3.5);
        gradeTwo.setStudentId(1);
        gradeTwo.setSubjectId(1);
        gradesList.add(gradeTwo);
        gradeThree.setDate(LocalDate.of(1994,2,14));
        gradeThree.setGrade(3.5);
        gradeThree.setStudentId(2);
        gradeThree.setSubjectId(2);
        gradesList.add(gradeThree);
        gradeFour.setDate(LocalDate.of(1994,2,14));
        gradeFour.setGrade(4.0);
        gradeFour.setStudentId(3);
        gradeFour.setSubjectId(3);
        gradesList.add(gradeFour);
        gradeFive.setDate(LocalDate.of(1994,2,14));
        gradeFive.setGrade(5.0);
        gradeFive.setStudentId(4);
        gradeFive.setSubjectId(4);
        gradesList.add(gradeFive);
    }

    public List<GradeModel> getGradesList(){
        return gradesList;
    }

    public GradeModel getGrade(ObjectId id){
        for(GradeModel g: gradesList){
            if(g.getId().equals(id)){
                return g;
            }
        }
        return null;
    }

    public void createGrade(GradeModel newGrade){
        gradesList.add(newGrade);
    }

    public void deleteGrade(GradeModel deletedGrade) {
        gradesList.remove(deletedGrade);
    }
}
