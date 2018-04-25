package com.pl.project.repositories.MongoRepository;

import com.pl.project.models.SubjectModel;

public class MongoSubjects {

    public void addSubject() {
        MongoBase mongoBase = MongoBase.getInstance();

        SubjectModel subjectOne = new SubjectModel();
        SubjectModel subjectTwo = new SubjectModel();
        SubjectModel subjectThree = new SubjectModel();

        subjectOne.setName("Matematyka");
        subjectOne.setTeacher("Jan Kowalski");

        subjectTwo.setName("Polski");
        subjectTwo.setTeacher("Krzysztof Kolumb");

        subjectThree.setName("Historia");
        subjectThree.setTeacher("Robert Szafran");

        mongoBase.addSubject(subjectOne);
        mongoBase.addSubject(subjectTwo);
        mongoBase.addSubject(subjectThree);
    }
}
