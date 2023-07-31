package org.example;

import java.sql.*;
import java.sql.CallableStatement;

public class ProcedureFunctionExample {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/Company";
        String username = "postgres";
        String password = "root";

        // Procedure call parameters
        int id = 2;
        String first_name = "hari";
        String last_name = "krishnan";
        int age = 22;

        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Call the procedure
            myprocedure(connection, id, first_name, last_name, age);

            // Call the function
            String fullName = myFunction(connection, first_name, last_name);
            System.out.println("Full Name: " + fullName);

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void myprocedure(Connection connection, int id, String first_name, String last_name, int age) throws SQLException {
        String myprocedure = "CALL myprocedure(?, ?, ?, ?)";

        try (CallableStatement statement = connection.prepareCall(myprocedure)) {
            statement.setInt(1, id);
            statement.setString(2, first_name);
            statement.setString(3, last_name);
            statement.setInt(4, age);
            statement.execute();
        }
    }

    public static String myFunction(Connection connection, String first_name, String last_name) throws SQLException {
        String myFunction = "{? = CALL myFunction(?, ?)}";
        String fullName = null;

        try (CallableStatement statement = connection.prepareCall(myFunction)) {
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.setString(2, first_name);
            statement.setString(3, last_name);
            statement.execute();
            fullName = statement.getString(1);
        }

        return fullName;
    }
}
