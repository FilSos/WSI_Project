package com.pl.project.repositories.MongoRepository;

import com.mongodb.MongoClient;
import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoBase {

    protected Morphia morphia;
    protected Datastore datastore;

    public void connect(){

    }

    public void addStudent(StudentModel studentModel){
        morphia = new Morphia();
        //morphia.getMapper().getOptions().isStoreEmpties() = true;
        datastore = morphia.createDatastore(new MongoClient("localhost",8004),"WSI");
        //TODO Jaka sciezka powinna byv tutaj podana?
        morphia.mapPackage("com.pl.project.models");
        datastore.ensureIndexes();
        datastore.save(studentModel);
    }
    public void addGrade(GradeModel gradeModel){
        datastore.save(gradeModel);
    }
    public void addSubject(SubjectModel subjectModel){
        datastore.save(subjectModel);
    }

}
