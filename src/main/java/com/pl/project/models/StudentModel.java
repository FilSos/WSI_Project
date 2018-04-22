package com.pl.project.models;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;


@Entity("students")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

//@Indexes(
//        @Index(value = "name", fields = @Field("name"))
////        @Index(value = "surname", fields = @Field("surname"))
////        @Index(value = "birthday", fields = @Field("birthday"))
//)
public class StudentModel {
    @Id
    //@XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId id;
    @XmlTransient
    private long index;
    private String name;
    private String surname;
    private Date birthday;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "id=" + id +
                ", index=" + index +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    public StudentModel() {
    }
}
