/*
9/24/2019
Created by: Todd Bauer

This file holds a class that allows the user to manage a database.
 */

package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Manages all database activity including connecting to the database and executing SQL statements.
 */
public class DatabaseManager {

  /**
   * The connection to the database.
   */
  private Connection conn = null;

  /**
   * A prepared SQL statement to inject into the database.
   */
  private PreparedStatement ps = null;

  /**
   * An object to hold the results of a SQL query.
   */
  private ResultSet rs = null;

  /**
   * Tracks if the program is connected to a database.
   */
  private boolean isConnectedToDB;

  /**
   * Holds the user name for the database connection.
   */
  private String user;

  /**
   * Holds the password for the database connection.
   */
  private String pword;

  /**
   * Getter for the connection status.
   *
   * @return Returns a boolean that is true if connected to the database and false if not.
   */
  boolean getConnectionStatus() {
    return isConnectedToDB;
  }

  /**
   * Default constructor. This accepts the username and password for a database connection, then
   * stores those values in the class. Finally, it attempts to connect to the database.
   *
   * @param user  Holds the username for the database
   * @param pword Holds the password for the database
   */
  protected DatabaseManager(String user, String pword) {
    this.user = user;
    this.pword = pword;
    connectToDB();
  }

  /**
   * Attempts to connect to the database.
   */
  private void connectToDB() {

    // NOTE: USERNAME/PASSWORD IS sa/sa ! This was the default username, and since security
    // is not a concern with this project, I duplicated it as the password for convenience.
    try {
      // Establish a connection to the database and flag the DatabaseManager as connected.
      Class.forName("org.h2.Driver");
      conn = DriverManager.getConnection("jdbc:h2:./test", user, pword);
      isConnectedToDB = true;

      // Cover cases where a connection could not be made.
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create a new table with the specified name. For now, this makes a predetermined table with 4
   * columns: id, name, manufacturer, and type.
   *
   * @param tableName Holds the name of the table to be accessed.
   */
  void createTable(String tableName) {
    System.out.println("Creating table...");

    try {
      ps = conn.prepareStatement("CREATE TABLE "
          + tableName
          + " (id INT PRIMARY KEY, "
          + "name VARCHAR(255), manufacturer VARCHAR(255), type VARCHAR(255))");
      ps.executeUpdate();
      System.out.println("Created table.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.print("Could not execute query.");
    }

  }

  /**
   * List all of the rows in the PRODUCTS table.
   *
   * @return ArrayList An ArrayList of Products that exist in the database.
   */
  ArrayList<Product> listRowsInProductsTable() {
    ArrayList<Product> products = new ArrayList<>();

    try {
      // Get all rows from the specified table.
      System.out.println("Executing query...");
      ps = conn.prepareStatement("SELECT * FROM PRODUCTS");
      rs = ps.executeQuery();
      System.out.println("Executed query.");

      // Print the results of the query.
      while (rs.next()) {
        System.out.println("ID: "
            + rs.getInt("id")
            + ", Name: " + rs.getString("name")
            + ", Manufacturer: " + rs.getString("manufacturer")
            + ", Type: " + rs.getString("type"));
        AudioPlayer player = new AudioPlayer(
            rs.getString("name"),
            rs.getString("manufacturer"),
            rs.getString("type")
        );
        products.add(player);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.print("Could not execute query.");
    }

    return products;
  }

  /**
   * List all of the rows in the PRODUCTION table.
   *
   * @return ArrayList An ArrayList of Productions that exist in the database.
   */
  ArrayList<Production> listRowsInProductionTable() {
    ArrayList<Production> productionRun = new ArrayList<>();

    try {

      // Get a list of all products. This is used to locate the product
      // associated with this production.
      ArrayList<Product> productLine = this.listRowsInProductsTable();

      // Get all rows from the specified table.
      System.out.println("Executing query...");
      ps = conn.prepareStatement("SELECT * FROM PRODUCTION");
      rs = ps.executeQuery();
      System.out.println("Executed query.");

      // Print the results of the query.
      while (rs.next()) {
        System.out.println("ID: "
            + rs.getInt("ID")
            + ", Product Name: "
            + rs.getString("PRODUCT_NAME")
            + ", Quantity: "
            + rs.getString("QUANTITY")
            + ", Manufacture Date: " + rs.getTimestamp("MANUFACTURE_DATE"));

        for (Product product : productLine) {
          if (product.getName().equals(rs.getString("PRODUCT_NAME"))) {
            Production production = new Production(
                product,
                rs.getInt("QUANTITY"),
                new Date(rs.getTimestamp("MANUFACTURE_DATE").getTime())
            );
            productionRun.add(production);
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.print("Could not execute query.");
    }

    return productionRun;
  }

  /**
   * Insert new rows into the PRODUCT table.
   *
   * @param id           The unique ID number of the row entry.
   * @param name         The name of the product in the row entry.
   * @param manufacturer The manufacturer of the product in the row entry.
   * @param type         The "type" of the product in the row entry (see ItemType enum).
   */
  void insertRowIntoProductTable(int id, String name, String manufacturer, String type) {

    System.out.println("Inserting records into table...");
    try {
      ps = conn.prepareStatement("INSERT INTO PRODUCTS VALUES ("
          + id + ", '"
          + name + "', '"
          + manufacturer
          + "', '"
          + type
          + "')");

      ps.executeUpdate();
      System.out.println("Inserted records into table.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Could not create record.");
    }
  }

  /**
   * Insert new rows into the PRODUCTION table.
   *
   * @param id             The unique ID number of the row entry.
   * @param name           The name of the product in the row entry.
   * @param quantity       The amount of products that were manufactured.
   * @param manufacturedOn The date and time the products were manufactured.
   */
  void insertRowIntoProductionTable(int id, String name, int quantity, Date manufacturedOn) {

    System.out.println("Inserting records into table...");
    try {
      ps = conn.prepareStatement(
          "INSERT INTO PRODUCTION VALUES ("
              + id + ", '"
              + name + "', '"
              + quantity + "', '"
              + new Timestamp(manufacturedOn.getTime())
              + "')"
      );

      ps.executeUpdate();
      System.out.println("Inserted records into table.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Could not create record.");
    }
  }

  /**
   * Retrieves and prints the columns names (and their data types) in a given table.
   *
   * @param tableName Holds the name of the table to be accessed.
   */
  void listColumnsInTable(String tableName) {
    try {
      System.out.println("Listing column names and their data types...");
      ps = conn.prepareStatement("SELECT COLUMN_NAME, TYPE_NAME FROM "
          + "INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"
          + tableName
          + "'");
      rs = ps.executeQuery();

      while (rs.next()) {
        System.out.println("Name: " + rs.getString(1) + ", Data type: " + rs.getString(2));
      }
      System.out.println("Listed column names and their data types.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Could not retrieve column information.\n");
    }
  }

  /**
   * Close all objects to tidy up.
   */
  void disconnectFromDB() {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (Exception e) {
      System.out.print("Error closing ResultSet.\n");
      e.printStackTrace();
    }
    try {
      if (ps != null) {
        ps.close();
      }
    } catch (Exception e) {
      System.out.print("Error closing PreparedStatement.\n");
      e.printStackTrace();
    }
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (Exception e) {
      System.out.print("Error closing Connection.\n");
      e.printStackTrace();
    }

    isConnectedToDB = false;
    System.out.print("Connection successfully closed.\n");
  }
}