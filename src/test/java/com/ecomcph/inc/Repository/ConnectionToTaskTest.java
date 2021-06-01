package com.ecomcph.inc.Repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionToTaskTest {
ConnectionToTask conn = new ConnectionToTask();

    @Test
    void writeToTask() {
       String [] medarbejdere = {"3","4","5"};
       assertEquals(conn.writeToTask("testOpgave",medarbejdere,"2021-06-30","byg en flot mobilapplikation"),conn.getLatestTaskID()); }
}