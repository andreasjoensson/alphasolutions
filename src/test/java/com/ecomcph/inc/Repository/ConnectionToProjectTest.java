package com.ecomcph.inc.Repository;

import com.ecomcph.inc.Models.ProjectModel;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionToProjectTest {

    ConnectionToProject conn = new ConnectionToProject();

    @Test
    void getDaysToWork() throws SQLException {
        assertEquals(conn.getDaysToWork(51), 4);
    }

    @Test
    void updateProject() {
        conn.updateProject("junitTestSuccess","2021-06-20",25000,30,18);
        assertEquals(conn.printSpecificProject(18).getName(),"junitTestSuccess");
    }
}