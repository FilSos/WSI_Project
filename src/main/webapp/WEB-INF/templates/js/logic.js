"use strict";
var rootURL = "http://localhost:9080/";

var studentList;
var studentEditor;
var studentDetails;

var StudentViewModel = function() {
  var self = this;

  this.index = ko.observable();
  this.name = ko.observable();
  this.surname = ko.observable();
  this.birthday = ko.observable();

//sprawdz czy istnieje student o takim indexie i zaciagnij jego dane jesli tak
  this.isEdit = ko.computed(function() {
    if(this.index())
      return true;

    return false
  }, this);
};

var ListViewModel = function() {
  var self = this;
  this.list = ko.observableArray();
  this.parentId = ko.observable();

  this.indexFilter = ko.observable();
  this.nameFilter = ko.observable();
  this.surnameFilter = ko.observable();
  //TODO do zmiany, poszukania
  this.BirthDateFromFilter = ko.observable();
  this.BirthDateToFilter = ko.observable();

  this.NameFilter = ko.observable();
  this.LeadTeacherFilter = ko.observable();
  this.ECTSFilter = ko.observable();
}

var ErrorViewModel = function() {
  this.Message = ko.observable();
  this.ExceptionMessage = ko.observable();
}

$(document).ready(function() {

  studentList = new ListViewModel();
  ko.applyBindings(studentList, $("#student_list")[0]);

  $('.tableHeaderFilter').keypress(function(e){
    if(e.which == 13){
        $(this).blur();
    }
  });

  $(".studentFilter").focusout(function(){
    GetStudents();
  });
});

function GetDataFromAPI(controllerName, method, vm) {
  if(!method) {
    method = "";
  }

  var parameters = "";
  parameters = AddParameter(parameters, "name", vm.FirstnameFilter());
  parameters = AddParameter(parameters, "surname", vm.LastnameFilter());
  parameters = AddParameter(parameters, "index", vm.IndexFilter());

  $.ajax({
    url: rootURL + controllerName + method,
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

function ClearFilter(vm) {
  vm.IndexFilter("");
  vm.FirstnameFilter("");
  vm.LastnameFilter("");

  vm.NameFilter("");
  vm.LeadTeacherFilter("");
  vm.ECTSFilter("");
}

function GetStudents() {
  GetDataFromAPI('students', null, studentList);
}

function MapStudentVM(vm1, vm2) {
  vm1.Id(vm2.Id());
  vm1.Index(vm2.Index());
  vm1.Grades(vm2.Grades());
  vm1.BirthDate(vm2.BirthDate());
  vm1.FirstName(vm2.FirstName());
  vm1.LastName(vm2.LastName());
}
