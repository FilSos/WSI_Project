package com.pl.project.models;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


@Entity("subjects")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@ToString
public class SubjectModel {
    @Id
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId id;
    private String subjectName;
    private String teacher;
    @Reference
    private List<StudentModel> studentList;
    @Reference
    private List<GradeModel> gradeList;

    @XmlTransient
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<StudentModel> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentModel> studentList) {
        this.studentList = studentList;
    }

    public List<GradeModel> getGradeList() {
        return gradeList;
    }

    public List<GradeModel> getGradesListOfStudent(Long index) {
        List<GradeModel> GradesList = new ArrayList<>();
        for (GradeModel Grade : gradeList) {
            if (Grade.getStudent().getIndex().equals(index)) {
                GradesList.add(Grade);
            }
        }
        return GradesList;
    }


    public void setGradeList(List<GradeModel> gradeList) {
        this.gradeList = gradeList;
    }
}
