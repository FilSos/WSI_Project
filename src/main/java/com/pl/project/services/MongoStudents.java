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
        studentOne.setIndex(115610);
        studentOne.setName("Filip");
        studentOne.setSurname("Sosnowski");
        studentOne.setBirthday(new Date(1994,12,12));
        studentOne.setSubject(mongoBase.subjectsList().get(0));
        studentTwo.setIndex(133451);
        studentTwo.setName("Filip");
        studentTwo.setSurname("Sochal");
        studentTwo.setBirthday(new Date(1994,3,15));
        studentTwo.setSubject(mongoBase.subjectsList().get(0));
        studentThree.setIndex(112341);
        studentThree.setName("Karol");
        studentThree.setSurname("Pawlak");
        studentThree.setBirthday(new Date(1994,4,25));
        studentThree.setSubject(mongoBase.subjectsList().get(1));
        studentFour.setIndex(112377);
        studentFour.setName("Jan");
        studentFour.setSurname("Kowalski");
        studentFour.setBirthday(new Date(1994,1,11));
        studentFour.setSubject(mongoBase.subjectsList().get(1));
        studentFive.setIndex(115672);
        studentFive.setName("Piotr");
        studentFive.setSurname("Sendrowski");
        studentFive.setBirthday(new Date(1993,11,19));
        studentFive.setSubject(mongoBase.subjectsList().get(1));

        mongoBase.addStudent(studentOne);
        mongoBase.addStudent(studentTwo);
        mongoBase.addStudent(studentThree);
        mongoBase.addStudent(studentFour);
        mongoBase.addStudent(studentFive);
    }

}
