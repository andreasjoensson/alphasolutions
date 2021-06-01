package com.ecomcph.inc.Models;

public class ProjectTask {
    private int id;
    private String name;
    private String [] employees;
    private String deadline;
    private String taskDescription;


    public ProjectTask(int id, String name, String deadline, String taskDescription) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.taskDescription = taskDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String[] getEmployees() {
        return employees;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getName() {
        return name;
    }

    public void setEmployees(String [] employees) {
        this.employees = employees;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setName(String name) {
        this.name = name;
    }
}
