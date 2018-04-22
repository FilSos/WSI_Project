package com.pl.project.repositories;

import com.pl.project.models.StudentModel;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
public class StudentsRepository {
    List<StudentModel> studentsList;
    public StudentsRepository(){
        studentsList = new ArrayList<>();
        StudentModel studentOne = new StudentModel();
        StudentModel studentTwo = new StudentModel();
        StudentModel studentThree = new StudentModel();
        StudentModel studentFour = new StudentModel();
        StudentModel studentFive = new StudentModel();
        studentOne.setIndex(11561);
        studentOne.setName("Filip");
        studentOne.setSurname("Sosnowski");
        //studentOne.setBirthday(LocalDate.of(1994,2,14));
        studentOne.setBirthday(new Date(1994));
        studentTwo.setIndex(112342);
        studentTwo.setName("Filip");
        studentTwo.setSurname("Sochal");
        //studentTwo.setBirthday(LocalDate.of(1994,2,14));
        studentTwo.setBirthday(new Date(1994));

        //studentThree.setId(2);
//        studentThree.setName("Karol");
//        studentThree.setSurname("Pawlak");
//        studentThree.setBirthday(LocalDate.of(1994,2,14));
//        //studentFour.setId(3);
//        studentFour.setName("Jan");
//        studentFour.setSurname("Kowalski");
//        studentFour.setBirthday(LocalDate.of(1994,2,14));
//        //studentFive.setId(4);
//        studentFive.setName("Piotr");
//        studentFive.setSurname("Sendrowski");
//        studentFive.setBirthday(LocalDate.of(1994,2,14));
        studentsList.add(studentOne);
        studentsList.add(studentTwo);
//        studentsList.add(studentThree);
//        studentsList.add(studentFour);
//        studentsList.add(studentFive);

    }

    public List<StudentModel> getStudentsList(){
        return studentsList;
    }

    public StudentModel getStudent(long id){
        for(StudentModel s: studentsList){
            if(s.getIndex()== id){
                return s;
            }
        }
    return null;
    }

    public void createStudent( StudentModel newStudent){
        studentsList.add(newStudent);
    }

    public void deleteStudent(StudentModel deletedStudent) {
        studentsList.remove(deletedStudent);
    }
}
