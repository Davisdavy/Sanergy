package com.moringa.sanergyapp.models;

import androidx.annotation.NonNull;

public class EmployeeId {
    public String empId;

    public <T extends EmployeeId> T withId(@NonNull final String id){
        this.empId = id;
        return (T) this;
    }
}
