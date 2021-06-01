package com.ecomcph.inc.Repository;

import com.ecomcph.inc.Models.ProjectTask;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;

public class ConnectionToTask {
    ConnectToDatabase Connection = new ConnectToDatabase();
    ConnectionToProject conn = new ConnectionToProject();

    public int getLatestTaskID(){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        int generatedKey = 0;

        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            String SQL ="select MAX(delOpgaveId) AS id from delopgaver;";
            PreparedStatement statement = connection.prepareStatement(SQL);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
               generatedKey = rs.getInt(1);
            }

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
        return generatedKey;
    }

    public int writeToTask(String delOpgaveNavn, String [] medarbejdere, String deadline, String taskDescription){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        String generatedColumns[] = { "ID" };
        long id = 0;

        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            String SQL ="INSERT INTO `delopgaver`(delopgaveNavn, antalMedarbejdere, deadline, opgaveBeskrivelse) VALUE (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(SQL,generatedColumns);

            statement.setString(1,delOpgaveNavn);
            statement.setInt(2,medarbejdere.length);
            statement.setDate(3, Date.valueOf(deadline));
            statement.setString(4,taskDescription);

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
        return (int) id;
    }

    public int assignEmployeesToTask(int taskID, String [] medarbejdere){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        int generatedKey = 0;
        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            for(int i = 0; i < medarbejdere.length; i++) {
                String SQL = "INSERT INTO delopgavemedarbejder(delOpgaveId, medarbejderId) VALUES (?,?)";
                PreparedStatement statement = connection.prepareStatement(SQL);
                statement.setInt(1,taskID);
                statement.setInt(2,Integer.parseInt(medarbejdere[i]));
                statement.executeUpdate();
            }
        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
        return generatedKey;
    }

    public ArrayList <Integer> findTasksForSpecificProject(int projectID){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        ArrayList <Integer> Tasks = new ArrayList<>();

        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            String SQL= "select m.delOpgaveId from delopgaver m inner join ProjectDelopgave am on m.delOpgaveId = am.delOpgaveId inner join Project a on am.projektId = a.projektId where a.projektId = ?";
            //PreparedStatement statement = connection.prepareStatement("select m.delOpgaveId from delOpgaver m inner join ProjectDelopgave am on m.delOpgaveId = am.delOpgaveId inner join Project a on am.projektId = a.projektId where a.projektId = 9");
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1,projectID);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Tasks.add(rs.getInt(1));
            }

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
        return Tasks;
    }

    public ArrayList <ProjectTask> getTaskObjectsFromID(ArrayList <Integer> tasksID){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        ProjectTask delOpgave = null;
        ArrayList <ProjectTask> OpgaveObjects = new ArrayList<>();

        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            for(int i = 0 ; i < tasksID.size(); i++){
                String SQL ="SELECT * FROM delopgaver WHERE delOpgaveId =?";
                PreparedStatement stmt = connection.prepareStatement(SQL);
                stmt.setInt(1,tasksID.get(i));
                ResultSet rs = stmt.executeQuery();

while(rs.next()){
    delOpgave = new ProjectTask(rs.getInt(1),rs.getString(2),rs.getString(4),rs.getString(5));
}
                OpgaveObjects.add(delOpgave);
            }
        }

        catch(SQLException | PropertyVetoException e){
            System.out.println(e.getMessage());
        }
        return OpgaveObjects;
    }



    public ArrayList <ProjectTask> getAllTasks(){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        ProjectTask delOpgave = null;
        ArrayList <ProjectTask> OpgaveObjects = new ArrayList<>();

        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
                String SQL ="SELECT * FROM delopgaver";
                PreparedStatement stmt = connection.prepareStatement(SQL);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    delOpgave = new ProjectTask(rs.getInt(1),rs.getString(2),rs.getString(4),rs.getString(5));
                }
                OpgaveObjects.add(delOpgave);
        }

        catch(SQLException | PropertyVetoException e){
            System.out.println(e.getMessage());
        }
        return OpgaveObjects;
    }

    public void deleteTask(int taskID){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        int projektID = 0;
        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            String sqlStatement = "select m.projektId from Project m inner join ProjectDelopgave am on m.projektId = am.projektId inner join delopgaver a on am.delOpgaveId = a.delOpgaveId where a.delOpgaveId = ?";
            PreparedStatement stmtTo = connection.prepareStatement(sqlStatement);
            stmtTo.setInt(1,taskID);
            ResultSet rs = stmtTo.executeQuery();

            while(rs.next()){
                projektID = rs.getInt(1);
            }

            conn.subtractProjectCost(projektID,taskID);

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
    }
    //Metode til at returnere en delOpgave Objekt udfra et ID.
    public ProjectTask queryFromTask(int id){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        ProjectTask delOpgave = null;

        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM delopgaver WHERE delOpgaveId = ?";
            PreparedStatement stmt = connection.prepareStatement(SQL);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
        }

        catch(SQLException | PropertyVetoException e){
            System.out.println(e.getMessage());
        }
        return delOpgave;
    }
    public void updateTask(String delOpgaveNavn, String [] medarbejdere, String deadline, String taskDescription,int delOpgaveID){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            String query ="UPDATE delOpgave SET delopgaveNavn = ?, antalMedarbejdere = ?, deadline = ?,opgaveBeskrivelse =? WHERE delOpgaveId = ?";
            PreparedStatement preparedstmt = connection.prepareStatement(query);
            preparedstmt.setString(1, delOpgaveNavn);
            preparedstmt.setInt(2,medarbejdere.length);
            preparedstmt.setString(3,deadline);
            preparedstmt.setString(4,taskDescription);
            preparedstmt.setInt(5,delOpgaveID);
            preparedstmt.executeUpdate();
            connection.close();
        }catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
