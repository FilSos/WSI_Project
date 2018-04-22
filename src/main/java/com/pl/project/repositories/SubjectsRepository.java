package com.pl.project.repositories;


import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SubjectsRepository {

    List<SubjectModel> subjectsList;
    StudentsRepository studentsRepository = new StudentsRepository();
//TODO uzyskiwanie calej listy ocen a nie pojedynczego elementu
    public SubjectsRepository(){
        List<StudentModel> listOne = new ArrayList<>();
        List<StudentModel> listTwo = new ArrayList<>();
        List<StudentModel> listThree = new ArrayList<>();
        List<StudentModel> studentsList = studentsRepository.studentsList;
        listOne.add(studentsList.get(0));
//        listOne.add(studentsList.get(1));
//        listOne.add(studentsList.get(2));
//        listTwo.add(studentsList.get(0));
//        listTwo.add(studentsList.get(3));
//        listTwo.add(studentsList.get(4));
//        listThree.add(studentsList.get(1));
//        listThree.add(studentsList.get(2));
//        listThree.add(studentsList.get(4));
        subjectsList = new ArrayList<>();
        SubjectModel subjectOne = new SubjectModel();
        SubjectModel subjectTwo = new SubjectModel();
        SubjectModel subjectThree = new SubjectModel();
        GradesRepository gradesRepository = new GradesRepository();
        //subjectOne.setId(0);
        subjectOne.setName("Programowanie");
        subjectOne.setTeacher("Jan Kowalski");
        subjectOne.setStudents(listOne);
        subjectOne.setGrades(gradesRepository.gradesList.get(0));
       // subjectTwo.setId(1);
        subjectTwo.setName("Kodowanie");
        subjectTwo.setTeacher("Krzysztof Kolumb");
        subjectTwo.setStudents(listTwo);
        subjectTwo.setGrades(gradesRepository.gradesList.get(1));
       // subjectThree.setId(2);
        subjectThree.setName("Stackoverflowowanie");
        subjectThree.setTeacher("Robert Szafran");
        subjectThree.setStudents(listThree);
        subjectThree.setGrades(gradesRepository.gradesList.get(2));

        subjectsList.add(subjectOne);
        subjectsList.add(subjectTwo);
        subjectsList.add(subjectThree);

    }

    public List<SubjectModel> getSubjectsList(){
        return subjectsList;
    }

    public SubjectModel getSubject(long id){
        for(SubjectModel s: subjectsList){
            if(s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }

    public void createSubject( SubjectModel newSubject){
        subjectsList.add(newSubject);
    }

    public void deleteSubject(SubjectModel deletedSubject) {
        subjectsList.remove(deletedSubject);
    }
}
