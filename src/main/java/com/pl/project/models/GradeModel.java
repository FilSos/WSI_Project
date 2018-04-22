package com.pl.project.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Date;

@Entity("grades")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class GradeModel {
    @Id
    //@XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId id;
    private Double grade;
    private LocalDate date;
    private int studentId;
    private int subjectId;

    @XmlTransient
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "GradeModel{" +
                "id=" + id +
                ", grade=" + grade +
                ", date=" + date +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                '}';
    }

    public GradeModel(ObjectId id, Double grade, LocalDate date, int studentId, int subjectId) {
        this.id = id;
        this.grade = grade;
        this.date = date;
        this.studentId = studentId;
        this.subjectId = subjectId;
    }
}

