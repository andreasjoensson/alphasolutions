package com.ecomcph.inc.Repository;

import com.ecomcph.inc.Models.ProjectModel;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class ConnectionToProject {

    //Add a project to the database
    public int writeToProject(String name, String deadline, int projectIncome) {
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;

        //Opretter en variabel for at opbevare det ny indsatte id.
        String generatedColumns[] = { "ID" };

        //Opretter long variabel
        long id = 0;

        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Project (navn, deadline, projektIndtjening,pStatus) VALUE (?,?,?,?)",generatedColumns);

            //Vi indsætter alle værdier, med en prepareStatement for at undgå SQL injection
            ps.setString(1,name);
            ps.setString(2, deadline);
            ps.setInt(3, projectIncome);
            ps.setString(4, "0");

            //Vi køre ovenstående command, og indsætter en ny række i tabellen
            ps.executeUpdate();

            //Efterfølgende henter vi den nye generedeKey, som er det nyeste indsat projektID.
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
        //Vi returnere det ny indsatte ID
        return (int) id;
    }

    public ArrayList<ProjectModel> printAllFromProjects(){
        ArrayList<ProjectModel> projectsFromDB = new ArrayList();
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        ProjectModel project = null;

        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            //Vi indhenter alle projekter fra tabellen ved brug af nedestående SQL Query
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Project");
            //Vi køre ovenstående kommando
            ResultSet rs = stmt.executeQuery();

            //Vi indhenter nu data, og indsætter det i vores Model.
            //Derefter tilføjer vi hver enkel Model i vores ArrayList
            while(rs.next()){
               project = new ProjectModel(rs.getInt("projektId"),rs.getString("navn"), rs.getString("deadline"),rs.getInt("projektIndtjening"),rs.getInt("projektOmkostninger"),rs.getString("Pstatus"));
               projectsFromDB.add(project);
            }

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
        //Returnere vores ArrayList
        return projectsFromDB;
    }


    public ProjectModel printSpecificProject(int id){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        ProjectModel project = null;
        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            //Henter et specifikt projekt som passer med det unikke ID parameter.
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Project WHERE projektId = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                project = new ProjectModel(rs.getInt("projektId"),rs.getString("navn"),rs.getString("deadline"),rs.getInt("projektIndtjening"),rs.getInt("projektOmkostninger"),rs.getString("pStatus"));
            }
        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
        //Returnere det enkelte objekt
        return project;
    }

    public void assignTaskToProject(int projektId, int taskID){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            //Indsætter ProjektID og TaskID i en many to many tabel, som sørger for at koble dem begge sammen.
                PreparedStatement statement = connection.prepareStatement("INSERT INTO projectdelopgave(projektId, delOpgaveId) VALUES (?,?)");
                statement.setInt(1,projektId);
                statement.setInt(2,taskID);
                statement.executeUpdate();
        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getDaysToWork(int taskID) throws SQLException {
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        long days = 0;
        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            String deadline = "";
            //Henter den nuværende deadline fra en delOpgave.
            PreparedStatement stmtTo = connection.prepareStatement("SELECT deadline FROM delopgaver WHERE delOpgaveId = ?");
            stmtTo.setInt(1,taskID);
            ResultSet rsTo = stmtTo.executeQuery();

            //Ændre deadline variablen til den nuværende deadline fra delopgaven.
            while(rsTo.next()){
                deadline = rsTo.getString(1);
            }

            String insertTime = "";
            //Finder det præcise tidspunkt delOpgaven er blevet oprettet.
            PreparedStatement stmt = connection.prepareStatement("SELECT creationTime FROM delopgaver WHERE delOpgaveId = ?");
            stmt.setInt(1,taskID);
            ResultSet rs = stmt.executeQuery();

            //Da creationTime er formatteret med både dato og tid, er det derfor nødvendigt at lave en Regular Expression(regEx) på vores String
            //Vi er nød til at gøre dette, fordi vi kun har behov for datoen.
            while(rs.next()){
                String creationTime = rs.getString(1);
                String[] splited = creationTime.split("\\s+");
                insertTime = splited[0];
            }

            //Parser datoen
            LocalDate dateBefore = LocalDate.parse(insertTime);
            LocalDate dateAfter = LocalDate.parse(deadline);

            //Udregner dagene ind i mellem tiden delOpgaven blev oprettet og hvornår deadline er.
            days = ChronoUnit.DAYS.between(dateBefore, dateAfter);

        }
        catch(Exception e){
            e.printStackTrace();
        }

        //Returnere det antal dage, som er nødvendigt at bruge for at nå opgavens deadline.
        return (int) days;
    }

    public int getProjectCost(int projectID) throws SQLException {
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        int pastProjectCost = 0;

try{
    dataSource = ConnectToDatabase.getDataSource();
    connection = dataSource.getConnection();
    //Finder det forrige projektsOmkostninger
    PreparedStatement stmt = connection.prepareStatement("SELECT projektOmkostninger FROM Project WHERE projektId = ?");
    stmt.setInt(1,projectID);
    ResultSet rs = stmt.executeQuery();

    while(rs.next()){
        pastProjectCost += rs.getInt(1);
    }
}
catch (Exception e){
    e.printStackTrace();
}
        return pastProjectCost;
    }

    public void subtractProjectCost(int projektId, int taskID) throws SQLException {
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;

        //Denne metode benyttes kun såfremt at en delOpgave slettes.
        //Hvis en delOpgave slettes, skal projektOmkostningerne minimeres siden der frafalder nogle medarbejdere.
        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            int projektOmkostninger = 0;
            int forrigeProjektOmkostninger = getProjectCost(projektId);

            //Finder de enkelte medarbejderes månedsløn, fra en specifik delOpgave
            PreparedStatement stmtTre = connection.prepareStatement("select m.månedsløn from Medarbejder m inner join delopgavemedarbejder am on m.medarbejderId = am.medarbejderId inner join delopgaver a on am.delOpgaveId = a.delOpgaveId where a.delOpgaveId = ?");
            stmtTre.setInt(1,taskID);

            ResultSet rsTre = stmtTre.executeQuery();

            while(rsTre.next()){
                projektOmkostninger += rsTre.getInt(1);
            }

            int days = getDaysToWork(taskID);

            //Tager udgangspunkt i en normal arbejdsmåned, ved 8 timers arbejdsdage.
            int hourlyWage = (projektOmkostninger/148);
            int dailyWage = hourlyWage * 8;

            int omkostning = forrigeProjektOmkostninger - (days * dailyWage);

            PreparedStatement stmtFire = connection.prepareStatement("UPDATE Project SET projektOmkostninger = ? WHERE projektId = ?");
            stmtFire.setInt(1,omkostning);
            stmtFire.setInt(2,projektId);

            stmtFire.executeUpdate();

            String deleteTaskSQL = "DELETE FROM delopgaver WHERE delOpgaveId=?";
            PreparedStatement stmtFem = connection.prepareStatement(deleteTaskSQL);

            stmtFem.setInt(1,taskID);
            stmtFem.executeUpdate();

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateProjectCost(int projektId, int taskID) throws SQLException {
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        try{
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            int projektOmkostninger = 0;
            int forrigeProjektOmkostninger = getProjectCost(projektId);

            PreparedStatement stmtTre = connection.prepareStatement("select m.månedsløn from Medarbejder m inner join delopgavemedarbejder am on m.medarbejderId = am.medarbejderId inner join delopgaver a on am.delOpgaveId = a.delOpgaveId where a.delOpgaveId =?");
            stmtTre.setInt(1,taskID);
            ResultSet rsTre = stmtTre.executeQuery();

            while(rsTre.next()){
                    projektOmkostninger += rsTre.getInt(1);
            }

            int days = getDaysToWork(taskID);

            int hourlyWage = (projektOmkostninger/148);

            int dailyWage = hourlyWage * 8;

            int omkostning = forrigeProjektOmkostninger + (days * dailyWage);

            PreparedStatement stmtFire = connection.prepareStatement("UPDATE Project SET projektOmkostninger = ? WHERE projektId = ?");
            stmtFire.setInt(1,omkostning);
            stmtFire.setInt(2,projektId);
            stmtFire.executeUpdate();

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
    }
    public void deleteProject(int projectID){
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;
        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            String updateProject = "DELETE FROM Project WHERE projektId=?";
            PreparedStatement stmt = connection.prepareStatement(updateProject);
            stmt.setInt(1,projectID);
            stmt.executeUpdate();

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateProject(String name, String deadline, int projectIncome, int pStatus, int projektID) {
        //Creates a connection to the database.
        Connection connection = null;
        ComboPooledDataSource dataSource;

        try {
            dataSource = ConnectToDatabase.getDataSource();
            connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("UPDATE project" +
                    " SET navn = ?, deadline = ?, projektIndtjening = ?, pStatus = ? " +
                    "WHERE projektId = ?");
            stmt.setString(1,name);
            stmt.setString(2,deadline);
            stmt.setInt(3,projectIncome);
            stmt.setInt(4,pStatus);
            stmt.setInt(5,projektID);
            stmt.executeUpdate();

        } catch (SQLException | PropertyVetoException throwables) {
            throwables.printStackTrace();
        }
    }
}

