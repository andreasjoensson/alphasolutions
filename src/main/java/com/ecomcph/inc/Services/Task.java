package com.ecomcph.inc.Services;

import com.ecomcph.inc.Models.ProjectTask;
import com.ecomcph.inc.Repository.ConnectionToTask;

import java.util.ArrayList;

public class Task {
    //Opretter instans af repository laget.
    private ConnectionToTask connectionToTask = new ConnectionToTask();

    //Opretter opgave og returnere ID på den ny indsatte opgave
    public int addTask(String opgaveNavn, String deadline, String[] medarbejdere, String taskDescription) {
        int newlyInsertedID = connectionToTask.writeToTask(opgaveNavn,medarbejdere,deadline, taskDescription);
        return newlyInsertedID;
    }

    //Sørger for at skabe forbindelsen mellem den enkelte opgave, og hvilken medarbejder som har ansvar for den.
    //Begge id bliver puttet ind i en many to many relationship tabel, så forbindelsen er skabt.
    public void assignTaskToEmployee(int id, String [] employees) {
        connectionToTask.assignEmployeesToTask(id,employees);
    }

    //Finder alle opgaver som er tilknyttet til et specifikt projektID, og sørger for at returnere det som Model objekter.
    public ArrayList <ProjectTask> printTasksFromProject(int projectId){
       ArrayList <ProjectTask> tasks = connectionToTask.getTaskObjectsFromID(connectionToTask.findTasksForSpecificProject(projectId));
       return tasks;
    }

    public ArrayList <Integer> findSpecificTasksFromProjectID(int projectID){
        return connectionToTask.findTasksForSpecificProject(projectID);
    }

    //Finder den specifikke task, og sørger for at slette det.
    public void deleteSpecificTask(int taskID){
        connectionToTask.deleteTask(taskID);
    }
}
