package com.ecomcph.inc;

import com.ecomcph.inc.Repository.ConnectToDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class IncApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncApplication.class, args);



    }




}
