package org.example;

import java.sql.*;

public class AtomicityCallable {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/employee";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "root";

    public static void main(String[] args) {
        Connection conn = null;
        CallableStatement stmt1 = null;
        CallableStatement stmt2 = null;

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Set auto-commit as false to maintain atomicity
            conn.setAutoCommit(false);

            // Prepare insert query
            String insertCall = "{CALL insert_data(?, ?, ?)}";
            stmt1 = conn.prepareCall(insertCall);
            stmt1.setLong(1, 01);
            stmt1.setString(2, "Raj");
            stmt1.setString(3, "raj@gmail.com");

            // Execute insert query
            stmt1.executeUpdate();

           // Prepare update query
            String updateCall = "{CALL update_data(?, ?)}";
            stmt2 = conn.prepareCall(updateCall);
            stmt2.setLong(1, 01);
            stmt2.setString(2, "Jey");

            // Execute update query
            stmt2.executeUpdate();

            // Commit the transaction if both queries are successful
            conn.commit();

            System.out.println("Queries executed successfully. Transaction committed.");

        } catch (SQLException se) {
            // Rollback the transaction if any query fails
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Transaction rolled back successfully.");
                }
            } catch (SQLException re) {
                re.printStackTrace();
            }
            se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            // Clean-up resources
            try {
                if (stmt1 != null) {
                    stmt1.close();
                }
                if (stmt2 != null) {
                    stmt2.close();
                }
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}