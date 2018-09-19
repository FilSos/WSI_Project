"use strict";
var rootURL = "http://localhost:9080/";

var studentList;
var subjectList;
var studentGradesFromSubjects;
var studentsOnCourse;

var studentEdit;

var StudentViewModel = function () {
    var self = this;

    this.index = ko.observable();
    this.name = ko.observable();
    this.surname = ko.observable();
    this.birthday = ko.observable();

//sprawdz czy istnieje student o takim indexie i zaciagnij jego dane jesli tak
    this.isEdit = ko.computed(function () {
        if (this.index())
            return true;

        return false
    }, this);
};

var SubjectViewModel = function () {
    var self = this;

    this.id = ko.observable();
    this.subjectName = ko.observable();
    this.teacher = ko.observable();
    this.studentList = ko.observable();
    this.gradeList = ko.observable();
};

var GradeViewModel = function () {
    var self = this;

    this.id = ko.observable();
    this.gradeValue = ko.observable();
    this.date = ko.observable();
    this.studentData = ko.observable();
};

var StudentGradesFromSubjectsViewModel = function () {
    var self = this;

    this.studentIndex = ko.observable();
    this.gradeData = new GradeViewModel();
    this.subjectViewModel = new SubjectViewModel();
    this.subjectList = new ListViewModel();
    this.gradesList = new ListViewModel();
};
var StudentsOnCourseViewModel = function () {
    var self = this;

    this.studentData = new StudentViewModel();
    this.studentsList = new ListViewModel();
    this.subjectName = ko.observable();
};


var ListViewModel = function () {
    var self = this;
    this.list = ko.observableArray();
    this.parentName = ko.observable();
    this.parentId = ko.observable();
};

var ErrorViewModel = function () {
    this.Message = ko.observable();
    this.ExceptionMessage = ko.observable();
};

$(document).ready(function () {

    studentList = new ListViewModel();
    subjectList = new ListViewModel();
    studentGradesFromSubjects = new ListViewModel();
    studentsOnCourse = new ListViewModel();
    ko.applyBindings(studentList, $("#student_list")[0]);
    ko.applyBindings(subjectList, $("#courses_list")[0]);
    ko.applyBindings(studentGradesFromSubjects, $("#student_grades")[0]);
    ko.applyBindings(studentsOnCourse, $("#students_on_course")[0]);
});


function getDbData(controllerName, method, vm) {
    if (!method) {
        method = "";
    }

    $.ajax({
        url: rootURL + method,
        method: "GET",
        async: false,
        "accept": "application/json",
        success: function (data) {
            ko.mapping.fromJS(data, {}, vm.list);
        },
        error: function (error) {
            var errorVM = new ErrorViewModel();
            var obj = JSON.parse(error.responseText);
            ko.mapping.fromJS(obj, {}, errorVM);
            alert(errorVM.Message() + "\n" + errorVM.ExceptionMessage());
        }
    });
}

function Create(controllerName, method, vm) {
    if (!method) {
        method = "";
    }

    var obj = ko.mapping.toJS(vm);
    var json = JSON.stringify(obj);

    $.ajax({
        url: rootURL + method,
        method: "POST",
        async: false,
        data: json,
        contentType: "application/json",
        success: function (data) {
            alert("Create success!");
        },
        error: function (error) {
            var errorVM = new ErrorViewModel();
            var obj = JSON.parse(error.responseText);
            ko.mapping.fromJS(obj, {}, errorVM);
            alert(errorVM.Message() + "\n" + errorVM.ExceptionMessage());
        }
    });
}

function Update(controllerName, method, vm, id) {
    if (!method) {
        method = "";
    }

    var obj = ko.mapping.toJS(vm);
    var json = JSON.stringify(obj);

    $.ajax({
        url: protocol + serverAddress + port + apiPath + controllerName + method + "/" + id,
        method: "PUT",
        async: false,
        data: json,
        contentType: "application/json",
        success: function (data) {
            alert("Update success!");
        },
        error: function (error) {
            var errorVM = new ErrorViewModel();
            var obj = JSON.parse(error.responseText);
            ko.mapping.fromJS(obj, {}, errorVM);
            alert(errorVM.Message() + "\n" + errorVM.ExceptionMessage());
        }
    });
}

function getStudents() {
    getDbData('getStudentsList', 'students', studentList);
}

function getSubjects() {
    getDbData('getSubjectsList', 'subjects', subjectList);
}

function getStudentsOnSubject(subjectName) {
    getDbData('getSubjectStudents', 'subjects/' + subjectName + '/students', studentsOnCourse);
    // studentsOnCourse.subjectName(subjectName);
}

function getStudentsGrades(index) {
    getDbData('getStudentSubjects', 'students/' + index + '/subjects', studentGradesFromSubjects);
    //studentGradesFromSubjects.studentIndex(index);
}

function createEditStudent() {
    if (studentEdit.index()) {
        Update("updateStudent", 'students/update_student', studentEdit, studentEdit.index());
    }
    else {
        Create("createStudent", 'students/add_student', studentEdit);
    }
}


function MapStudentVM(vm1, vm2) {
    vm1.Id(vm2.Id());
    vm1.Index(vm2.Index());
    vm1.Grades(vm2.Grades());
    vm1.BirthDate(vm2.BirthDate());
    vm1.FirstName(vm2.FirstName());
    vm1.LastName(vm2.LastName());
}
