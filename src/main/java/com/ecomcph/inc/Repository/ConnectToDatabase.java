package com.ecomcph.inc.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

 public class ConnectToDatabase {
     public static ComboPooledDataSource getDataSource() throws PropertyVetoException
     {
         ComboPooledDataSource cpds = new ComboPooledDataSource();
         cpds.setJdbcUrl("");
         cpds.setUser("");
         cpds.setPassword("");

         // Optional Settings
         cpds.setInitialPoolSize(5);
         cpds.setMinPoolSize(5);
         cpds.setAcquireIncrement(5);
         cpds.setMaxPoolSize(20);
         cpds.setMaxStatements(100);

         return cpds;
     }
 }