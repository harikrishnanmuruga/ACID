package org.example;

import java.sql.*;
import java.sql.CallableStatement;
import java.sql.Statement;

public class FullProcedureFunctionExample {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/demo";
        String username = "postgres";
        String password = "root";

        // Procedure call parameters
        int id = 5;
        String first_name = "sathish";
        String last_name = "kumar";
        int age = 23;


        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create the "student" table if it doesn't exist
            createstudenttable(connection);

            // Call the procedure
            callInsertStudentProcedure(connection, id, first_name, last_name, age);
            // Call the function
            String fullName = callGetFullNameFunction(connection, id, first_name, last_name);
            System.out.println("Full Name: " + fullName);
            // update procedure
            callUpdateStudentProcedure(connection, id, "venkat", "single", 27);


            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void createstudenttable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createTableQuery = "CREATE TABLE IF NOT EXISTS student ("
                + "id SERIAL PRIMARY KEY,"
                + "first_name VARCHAR(50) NOT NULL,"
                + "last_name VARCHAR(50) NOT NULL,"
                + "age BIGINT NOT NULL"
                + ")";
        statement.executeUpdate(createTableQuery);
        statement.close();
        System.out.println("Table 'student' created successfully.");
    }



    public static void callInsertStudentProcedure (Connection connection,int id, String first_name, String last_name,
                                                   int age) throws SQLException {
        String InsertStudent = "CALL InsertStudent(?, ?, ?,?)";

        try (CallableStatement statement = connection.prepareCall(InsertStudent)) {
            statement.setInt(1, id);
            statement.setString(2, first_name);
            statement.setString(3, last_name);
            statement.setInt(4, age);
            statement.execute();
        }
        System.out.println("insert  successfully.");
    }

    public static String callGetFullNameFunction (Connection connection, int id, String first_name, String last_name) throws
            SQLException {
        String GetFullName = "{? = CALL GetFullName(?, ?,?)}";
        String fullName = null;

        try (CallableStatement statement = connection.prepareCall(GetFullName)) {
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.setInt(2, id);
            statement.setString(3, first_name);
            statement.setString(4, last_name);

            statement.execute();
            fullName = statement.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return fullName;
    }
    public static void callUpdateStudentProcedure(Connection connection, int id, String newFirstName,
                                                  String newLastName, int newAge) throws SQLException {
        String updateStudent = "CALL UpdateStudent(?, ?, ?, ?)";

        try (CallableStatement statement = connection.prepareCall(updateStudent)) {
            statement.setInt(1, id);
            statement.setString(2, newFirstName);
            statement.setString(3, newLastName);
            statement.setInt(4, newAge);
            statement.execute();
        }
        System.out.println("updated  successfully.");
    }

}