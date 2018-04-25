package com.pl.project.models;

public enum GradesValue {

    GRADE_ONE(2.0),GRADE_TWO(3.0),GRADE_THREE(3.5),GRADE_FOUR(4.0),GRADE_FIVE(4.5),GRADE_SIX(5.0);

    public Double value;
    GradesValue(Double v){
        value = v;
    }

}
