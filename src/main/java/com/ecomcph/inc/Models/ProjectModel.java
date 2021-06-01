package com.ecomcph.inc.Models;

public class ProjectModel {
    private int ProjectId;
    private String name;
    private String deadline;
    private int projectIncome;
    private int projectCost;
    private String status;

    public ProjectModel(int projectId, String name, String deadline, int projectIncome, int projectCost,String status) {
        this.ProjectId = projectId;
        this.name = name;
        this.deadline = deadline;
        this.projectIncome = projectIncome;
        this.projectCost = projectCost;
        this.status = status;
    }

    public int getProjectId() { return ProjectId; }

    public void setProjectId(int projectId) { this.ProjectId = projectId; }

    public String getDeadline() {
        return deadline;
    }

    public int getProjectIncome() {
        return projectIncome;
    }

    public String getName() {
        return name;
    }

    public int getProjectCost() {
        return projectCost;
    }

    public String getStatus() {
        return status;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProjectIncome(int projectIncome) {
        this.projectIncome = projectIncome;
    }

    public void setProjectCost(int projectCost) {
        this.projectCost = projectCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}