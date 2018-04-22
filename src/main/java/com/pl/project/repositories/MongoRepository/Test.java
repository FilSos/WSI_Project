package com.pl.project.repositories.MongoRepository;

import com.mongodb.MongoClient;
import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.List;

public class Test {

    public static void main(String[] args) {

        MongoBase mongoBase = new MongoBase();
       StudentModel studentModel = new StudentModel();
        studentModel.setName("test");
        studentModel.setSurname("test2");
       final  Morphia morphia = new Morphia();
        //morphia.getMapper().getOptions().isStoreEmpties() = true;
        morphia.mapPackage("com.pl.project.models");

       final Datastore datastore = morphia.createDatastore(new MongoClient("localhost",8004),"WSI");
        datastore.getDB().dropDatabase();
        datastore.ensureIndexes();
        datastore.save(studentModel);
        //List<GradeModel> studentModels = datastore.find(GradeModel.class).asList();
        System.out.println(studentModel);
    }
}
