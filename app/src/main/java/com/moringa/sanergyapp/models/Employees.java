package com.moringa.sanergyapp.models;

public class Employees extends EmployeeId{
    String emp_name,emp_title, image;

    public Employees(String emp_name, String emp_title, String image) {
        this.emp_name = emp_name;
        this.emp_title = emp_title;
        this.image = image;
    }

    public Employees(){

    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_title() {
        return emp_title;
    }

    public void setEmp_title(String emp_title) {
        this.emp_title = emp_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
