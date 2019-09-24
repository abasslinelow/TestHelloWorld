package sample;

import java.sql.*;

public class DBManager {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private boolean isConnectedToDB;
    boolean getConnectionStatus() {return isConnectedToDB;}

    private String user;
    private String pword;

    protected DBManager(String user, String pword) {
        this.user = user;
        this.pword = pword;
        connectToDB();
    }

    private void connectToDB() {

        // NOTE: USERNAME/PASSWORD IS sa/sa ! This was the default username, and since security
        // is not a concern with this project, I duplicated it as the password for convenience.
        try {
            // Establish a connection to the database and flag the DBManager as connected.
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:./test", user, pword);
            isConnectedToDB = true;

        // Cover cases where a connection could not be made.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // List all of the rows in a given table.
    void listRowsInTable(String tableName) {
        try {
            // Get all rows from the specified table.
            System.out.println("Executing query...");
            ps = conn.prepareStatement("SELECT * FROM " + tableName);
            rs = ps.executeQuery();
            System.out.println("Executed query.");

            // Print the results of the query.
            while (rs.next()) {
                System.out.println("UID: " + rs.getInt("uid") + ", First Name: " +
                        rs.getString("fname") + ", Last Name: " +
                        rs.getString("lname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Could not execute query.");
        }
    }

    // Close all objects to tidy up.
    void disconnectFromDB() {
            try { if (rs != null) rs.close(); } catch (Exception e) {
                System.out.print("Error closing ResultSet.");
                e.printStackTrace();
            } try { if (ps != null) ps.close(); } catch (Exception e) {
                System.out.print("Error closing PreparedStatement.");
                e.printStackTrace();
            } try { if (conn != null) conn.close(); } catch (Exception e) {
                System.out.print("Error closing Connection.");
                e.printStackTrace();
            }

            isConnectedToDB = false;
            System.out.print("Connection successfully closed.");
    }

    // Create a new table with the specified name. For now, this makes a
    // predetermined table with 3 columns: UID, first name, and last name.
    void createTable(String tableName) {
        System.out.println("Creating table...");

        try {
            ps = conn.prepareStatement("CREATE TABLE " + tableName + " (uid INT PRIMARY KEY, " +
                                       "fname VARCHAR(255), lname VARCHAR(255))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Could not execute query.");
        }

        System.out.println("Created table.");
    }

    // Insert new rows into the TEST table.
    void insertRowIntoTestTable(int uid, String firstName, String lastName) {

        System.out.println("Inserting records into table...");
        try {
            ps = conn.prepareStatement("INSERT INTO TEST VALUES (" + uid + ", '" +
                                        firstName + "', " + "'" + lastName + "')");
            ps.executeUpdate();
            System.out.println("Inserted records into table.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not create record.");
        }
    }

    // Retrieves and prints the columns names (and their data types) in the TEST table.
    void listColumnNamesInTable(String tableName) {
        try {
            System.out.println("Listing column names and their data types...");
            ps = conn.prepareStatement("SELECT COLUMN_NAME, TYPE_NAME FROM " +
                    "INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName + "'");
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Name: " + rs.getString(1) + ", Data type: " + rs.getString(2));
            }
            System.out.println("Listed column names and their data types.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not retrieve column information.");
        }
    }
}