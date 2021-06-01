package com.ecomcph.inc.Repository;

import com.ecomcph.inc.Models.Employee;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;

public class ConnectionToEmployees {
    private ConnectToDatabase Connection = new ConnectToDatabase();


    public void writeToEmployees(String name, int age, String eMail, String position, int monthlySalary, int isProjectLeader) {
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;


        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            String SQL = "INSERT INTO Medarbejder (navn, alder, eMail, månedsløn, stilling,erProjektleder) VALUE (?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setString(3,eMail);
            ps.setInt(4,monthlySalary);
            ps.setString(5, position);
            ps.setInt(6,isProjectLeader);
            ps.executeUpdate();
        }
        catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
    }



    public ArrayList<Employee> printAllFromEmployee(){
        ArrayList<Employee> dataFromDB = new ArrayList();
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;

        Employee medarbejder = null;
        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            //Lav specifik SQL Query til at hent medarbejder fra tabel med unik ID
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Medarbejder");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                //Indsætter alt gyldigt data fra SQL tabellen i vores Employee model
                medarbejder = new Employee(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getInt(6));
                dataFromDB.add(medarbejder);
            }

    }
        catch (SQLException | PropertyVetoException throwables) {
            //Catcher evt exceptions
            throwables.printStackTrace();
        }
        //Returnere en ArrayList med alle vores Employee modeller.
        return dataFromDB;
    }

    public ArrayList<Employee> printEmployeesFromTaskID(int taskID){
        //Laver en tom ArrayList, som skal indeholde alle Employees fra en specifik opgave
        ArrayList<Employee> specificEmployees = new ArrayList();

        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;

        Employee medarbejder = null;

        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            //Lav specifik SQL Query til at hent medarbejder fra tabel med unik ID
            PreparedStatement stmt = connection.prepareStatement("select * from Medarbejder m inner join delopgavemedarbejder am on m.medarbejderId = am.medarbejderId inner join delopgaver a on am.delOpgaveId = a.delOpgaveId where a.delOpgaveId = ?");
            stmt.setInt(1,taskID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                //Indsætter alt data fra SQL i vores Employee Model
                medarbejder = new Employee(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getInt(6));
                specificEmployees.add(medarbejder);
            }
        }
        catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
        return specificEmployees;
    }
        public Employee queryFromEmployee(int id) {
            //Creates a connection to the database.
            Connection connection = null;
            ComboPooledDataSource dataSource;

            Employee medarbejder = null;

        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            //Lav specifik SQL Query til at hent medarbejder fra tabel med unik ID
            String SQL = "SELECT * FROM Medarbejder WHERE medarbejderId = VALUE (?)";

            PreparedStatement stmt = connection.prepareStatement(SQL);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            medarbejder = new Employee(rs.getInt("id"), rs.getString("navn"),rs.getInt("alder"),rs.getString("eMail"),
                    rs.getString("stilling"),rs.getInt("månedsløn"));
        } catch (SQLException | PropertyVetoException e) {
            //Printer evt exception
            System.out.println(e.getMessage());
        }
        //Returnere Employee objekt
        return medarbejder;
    }

}