package com.pl.project.services;

import com.mongodb.MongoClient;
import com.pl.project.models.GradeModel;
import com.pl.project.models.StudentModel;
import com.pl.project.models.SubjectModel;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.List;

public class MongoBase {

    private Morphia morphia;
    private Datastore datastore;

    private MongoBase(){
        morphia = new Morphia();

        // tell Morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.map(StudentModel.class);
        morphia.map(GradeModel.class);
        morphia.map(SubjectModel.class);
        //morphia.getMapper().getOptions().isStoreEmpties() = true;
        datastore = morphia.createDatastore(new MongoClient("localhost",8004),"WSI");
        morphia.mapPackage("com.pl.project.models");
        //datastore.getDB().dropDatabase();
        datastore.ensureIndexes();
    }
    private  static class MongoBaseHandler{
     private static final MongoBase mongoBase = new MongoBase();
    }
    public static MongoBase getInstance(){
        return MongoBaseHandler.mongoBase;
    }

    public void addStudent(StudentModel studentModel){
        datastore.save(studentModel);
    }

    public void deleteStudent(StudentModel studentModel){
        datastore.delete(studentModel);
    }
    public void addGrade(GradeModel gradeModel){
        datastore.save(gradeModel);
    }
    public void deleteGrade(GradeModel gradeModel){
        datastore.delete(gradeModel);
    }
    public void addSubject(SubjectModel subjectModel){
        datastore.save(subjectModel);
    }
    public void deleteSubject(SubjectModel subjectModel){
        datastore.delete(subjectModel);
    }
    //TODO zastanowic sie jak podawac przedmioty oraz oceny
    public List<StudentModel> studentsList(){
       return datastore.find(StudentModel.class).asList();
    }
    public List<SubjectModel> subjectsList(){
        return datastore.find(SubjectModel.class).asList();
    }
    public List<GradeModel> gradesList() {
        return datastore.find(GradeModel.class).asList();
    }

}
