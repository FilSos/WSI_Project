"use strict";
var rootURL = "http://localhost:9080/";

var studentList;
var subjectList;

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


var ListViewModel = function () {
    var self = this;
    this.list = ko.observableArray();
};

var ErrorViewModel = function () {
    this.Message = ko.observable();
    this.ExceptionMessage = ko.observable();
};

$(document).ready(function () {

    studentList = new ListViewModel();
    subjectList = new ListViewModel();
    ko.applyBindings(studentList, $("#student_list")[0]);
    ko.applyBindings(subjectList, $("#courses_list")[0]);
});


function getDataFromAPI(controllerName, method, vm) {
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

function getStudents() {
    getDataFromAPI('getStudentsList', 'students', studentList);
}

function getSubjects() {
    getDataFromAPI('getSubjectsList', 'subjects', subjectList);
}


function MapStudentVM(vm1, vm2) {
    vm1.Id(vm2.Id());
    vm1.Index(vm2.Index());
    vm1.Grades(vm2.Grades());
    vm1.BirthDate(vm2.BirthDate());
    vm1.FirstName(vm2.FirstName());
    vm1.LastName(vm2.LastName());
}
