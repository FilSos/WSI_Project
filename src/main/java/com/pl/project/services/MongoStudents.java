package com.pl.project.services;


import com.pl.project.models.StudentModel;
import java.util.Date;

public class MongoStudents {
    public  void addStudent(){
        MongoBase mongoBase = MongoBase.getInstance();
        StudentModel studentOne = new StudentModel();
        StudentModel studentTwo = new StudentModel();
        StudentModel studentThree = new StudentModel();
        StudentModel studentFour = new StudentModel();
        StudentModel studentFive = new StudentModel();
        studentOne.setIndex(115610L);
        studentOne.setName("Filip");
        studentOne.setSurname("Sosnowski");
        studentOne.setBirthday(new Date(1994,12,12));
        studentTwo.setIndex(133451L);
        studentTwo.setName("Filip");
        studentTwo.setSurname("Sochal");
        studentTwo.setBirthday(new Date(1994,3,15));
        studentThree.setIndex(112341L);
        studentThree.setName("Karol");
        studentThree.setSurname("Pawlak");
        studentThree.setBirthday(new Date(1994,4,25));
        studentFour.setIndex(112377L);
        studentFour.setName("Jan");
        studentFour.setSurname("Kowalski");
        studentFour.setBirthday(new Date(1994,1,11));
        studentFive.setIndex(115672L);
        studentFive.setName("Piotr");
        studentFive.setSurname("Sendrowski");
        studentFive.setBirthday(new Date(1993,11,19));


        mongoBase.createStudentAndAddToSubject(studentOne,"Matematyka");
        mongoBase.createStudentAndAddToSubject(studentTwo,"Polski");
        mongoBase.createStudentAndAddToSubject(studentThree,"Matematyka");
        mongoBase.createStudentAndAddToSubject(studentFour,"Polski");
        mongoBase.createStudentAndAddToSubject(studentFive,"Historia");
    }

}
