package com.pl.project.repositories.MongoRepository;


import com.pl.project.models.StudentModel;


import java.time.LocalDate;
import java.util.Date;

public class MongoStudents {
    public  void addStudent(){
        StudentModel studentOne = new StudentModel();
        StudentModel studentTwo = new StudentModel();
        StudentModel studentThree = new StudentModel();
        StudentModel studentFour = new StudentModel();
        StudentModel studentFive = new StudentModel();
        studentOne.setIndex(114562);
        studentOne.setName("Filip");
        studentOne.setSurname("Sosnowski");
        studentOne.setBirthday(new Date(1994,12,12));
//        studentTwo.setIndex(133451);
//        studentTwo.setName("Filip");
//        studentTwo.setSurname("Sochal");
//        studentTwo.setBirthday(LocalDate.of(1994,3,15));
//        studentThree.setIndex(112341);
//        studentThree.setName("Karol");
//        studentThree.setSurname("Pawlak");
//        studentThree.setBirthday(LocalDate.of(1994,4,25));
//        studentFour.setIndex(112377);
//        studentFour.setName("Jan");
//        studentFour.setSurname("Kowalski");
//        studentFour.setBirthday(LocalDate.of(1994,1,11));
//        studentFive.setIndex(115672);
//        studentFive.setName("Piotr");
//        studentFive.setSurname("Sendrowski");
//        studentFive.setBirthday(LocalDate.of(1993,11,19));

        MongoBase mongoBase = new MongoBase();
        mongoBase.addStudent(studentOne);
        mongoBase.addStudent(studentTwo);
        mongoBase.addStudent(studentThree);
        mongoBase.addStudent(studentFour);
        mongoBase.addStudent(studentFive);
    }
}
