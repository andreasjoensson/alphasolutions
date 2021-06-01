package com.ecomcph.inc.Services;

import com.ecomcph.inc.Models.Employee;
import com.ecomcph.inc.Models.ProjectModel;
import com.ecomcph.inc.Repository.ConnectionToProject;
import com.ecomcph.inc.Repository.ConnectionToTask;

import java.sql.SQLException;
import java.util.ArrayList;

public class Project {
    private ConnectionToProject projectConnection = new ConnectionToProject();

    public int createProject(String projectName, String projectDeadline, int projectRevenue) {
            return projectConnection.writeToProject(projectName,projectDeadline,projectRevenue);
    }

    public void deleteProject(int projectID){
        projectConnection.deleteProject(projectID);
    }

    public void assignTaskToProject(int taskId, int projectId) throws SQLException {
        projectConnection.assignTaskToProject(projectId,taskId);
        projectConnection.updateProjectCost(projectId,taskId);
    }

    public ArrayList<ProjectModel> showProjectInArraylist(){
        ArrayList<ProjectModel> listOfProjects = new ArrayList<>();
        listOfProjects = projectConnection.printAllFromProjects();
        return listOfProjects;
    }
    public ProjectModel showSpecificProject(int id){
        ProjectModel project = null;
        project = projectConnection.printSpecificProject(id);
        return project;
    }

    public void updateProject(String name, String deadline, int projectRevenue, int status, int projectID){
        projectConnection.updateProject(name,deadline,projectRevenue,status,projectID);
    }
}
