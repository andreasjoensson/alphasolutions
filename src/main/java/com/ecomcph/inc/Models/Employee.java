package com.ecomcph.inc.Models;

public class Employee {

    private int EmployeeId;
    private String name;
    private int age;
    private String email;
    private String position;
    private int monthlySalary;
    private boolean isProjectLeader;

    public Employee(int EmployeeId, String name, int age, String email, String position, int monthlySalary) {
        this.EmployeeId = EmployeeId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.position = position;
        this.monthlySalary = monthlySalary;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }
    public String getName() {
        return name;
    }
    public int getAge () {
        return age;
    }
    public int getMonthlySalary () {
        return monthlySalary;
    }

    public String getEmail () {
        return email;
    }

    public String getPosition () {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlder(int alder) {
        this.age = alder;
    }

    public void seteMail(String email) {
        this.email = email;
    }

    public void setEmployeeId(int employeeId) {
        this.EmployeeId = employeeId;
    }

    public void setMonthlySalary(int monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public void setStilling(String position) {
        this.position = position;
    }
}
