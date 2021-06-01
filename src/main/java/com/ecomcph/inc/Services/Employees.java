package com.ecomcph.inc.Services;

import com.ecomcph.inc.Models.Employee;
import com.ecomcph.inc.Repository.ConnectToDatabase;
import com.ecomcph.inc.Repository.ConnectionToEmployees;

import java.sql.*;
import java.util.ArrayList;

public class Employees {
    ConnectionToEmployees connectionToEmployees = new ConnectionToEmployees();
    ArrayList<Employee> listOfEmployees = new ArrayList<>();

    public ArrayList<Employee> showEmployeeInArraylist(){
listOfEmployees = connectionToEmployees.printAllFromEmployee();
        return listOfEmployees;
    }

    public void createEmployee(String name, int age, String email, String position, int monthlySalary){
      connectionToEmployees.writeToEmployees(name,age,email,position,monthlySalary,0);
    }

    public ArrayList<Employee> getEmployeesFromTasks(int taskID){
        return connectionToEmployees.printEmployeesFromTaskID(taskID);
    }

}
