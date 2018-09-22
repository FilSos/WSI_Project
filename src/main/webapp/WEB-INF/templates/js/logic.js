"use strict";
var rootURL = "http://localhost:9080/";

var studentList;
var subjectList;
var studentGradesFromSubjects;
var studentsOnCourse;

var studentNew;
var studentEdit;
var subjectEdit;

var StudentViewModel = function () {
    var self = this;

    this.index = ko.observable();
    this.name = ko.observable();
    this.surname = ko.observable();
    this.birthday = ko.observable();

};

var SubjectViewModel = function () {
    var self = this;

    // this.id = ko.observable();
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

    studentNew = new StudentViewModel();
    studentEdit = new StudentViewModel();
    subjectEdit = new SubjectViewModel();

    ko.applyBindings(studentList, $("#student_list")[0]);
    ko.applyBindings(subjectList, $("#courses_list")[0]);
    ko.applyBindings(studentGradesFromSubjects, $("#student_grades")[0]);
    ko.applyBindings(studentsOnCourse, $("#students_on_course")[0]);
    ko.applyBindings(studentNew, $("#add_student")[0]);
    ko.applyBindings(studentEdit, $("#edit_student")[0]);
    ko.applyBindings(subjectEdit, $("#add_subject")[0]);

    $('#student_form').submit(function (e) {
        getStudents();
        window.location.href = '#student_list';
    });
    $('#edit_student_form').submit(function (e) {
        getStudents();
        window.location.href = '#student_list';
    });

    $('#subject_form').submit(function (e) {
        getSubjects();
        window.location.href = '#courses_list';
    });
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
        },
        error: function (error) {
            var errorVM = new ErrorViewModel();
            var obj = JSON.parse(error.responseText);
            ko.mapping.fromJS(obj, {}, errorVM);
            alert(errorVM.Message() + "\n" + errorVM.ExceptionMessage());
        }
    });
}

function Update(controllerName, method, vm) {
    if (!method) {
        method = "";
    }

    var obj = ko.mapping.toJS(vm);
    var json = JSON.stringify(obj);

    $.ajax({
        url: rootURL + method,
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

function Delete(method) {
    $.ajax({
        url: rootURL + method,
        method: "DELETE",
        async: false,
        "contentType": "application/json",
        success: function (data) {
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

function createStudent() {
    Create("createStudent", 'students/add_student', studentNew);
}

function updateStudent() {
    Update("updateStudent", 'students/update_student', studentEdit);
}

function getStudentEdit(index) {
    var vm = studentList.list()[index];
    mapStudentVM(studentEdit, vm);
}

function DeleteStudent(index) {
    Delete("students/delete_student/" + index);
    getStudents();

}

function clearStudentForm() {
    mapStudentVM(studentNew, new StudentViewModel());
}

function mapStudentVM(vm1, vm2) {
    vm1.index(vm2.index());
    vm1.birthday(vm2.birthday());
    vm1.name(vm2.name());
    vm1.surname(vm2.surname());
}

function getSubjects() {
    getDbData('getSubjectsList', 'subjects', subjectList);
}

function createEditSubject() {
    // if(subjectEdit.id) {
    //     Update("updateSubject", '/subjects/update_subject', subjectEdit, subjectEdit.id());
    // }
    // else {
    Create("createSubject", '/subjects/add_subject', subjectEdit);
    //}
}

function deleteSubject(subjectName) {
    Delete("delete_subject/" + subjectName);
    getSubjects();

}

function clearSubjectForm() {
    mapSubjectVM(subjectEdit, new SubjectViewModel());
}

function mapSubjectVM(vm1, vm2) {
    //vm1.id(vm2.id());
    vm1.subjectName(vm2.subjectName());
    vm1.teacher(vm2.teacher());
    // vm1.studentList(vm2.studentList());
    // vm1.gradeList(vm2.gradeList());
}

function getStudentsOnSubject(subjectName) {
    getDbData('getSubjectStudents', 'subjects/' + subjectName + '/students', studentsOnCourse);
    // studentsOnCourse.subjectName(subjectName);
}

function getStudentsGrades(index) {
    getDbData('getStudentSubjects', 'students/' + index + '/subjects', studentGradesFromSubjects);
    //studentGradesFromSubjects.studentIndex(index);
}


