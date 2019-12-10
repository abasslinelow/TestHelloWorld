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
      System.out.println("Executing query: SELECT * FROM PRODUCTS");
      ps = conn.prepareStatement("SELECT * FROM PRODUCTS");
      rs = ps.executeQuery();
      System.out.println("Executed query");

      Product player;

      // Print the results of the query.
      while (rs.next()) {
        if (rs.getString("monitor_type").equals("None")) {
          player = new AudioPlayer(
              rs.getString("name"),
              rs.getString("manufacturer"),
              rs.getString("type")
          );
        } else {

          // Create a screen object to belong to the video player.
          Screen screen = new Screen(
              rs.getString("screen_resolution"),
              rs.getInt("screen_refresh"),
              rs.getInt("screen_response")
          );

          // Determine the monitor type before creating the movie player.
          MonitorType monitorType;
          if (rs.getString("monitor_type").equals(MonitorType.LCD.name())) {
            monitorType = MonitorType.LCD;
          } else {
            monitorType = MonitorType.LED;
          }

          player = new MoviePlayer(
              rs.getString("name"),
              rs.getString("manufacturer"),
              rs.getString("type"),
              screen,
              monitorType
          );
        }
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
      Product productInProduction = null;

      // Get all rows from the specified table.
      System.out.println("Executing query: SELECT * FROM PRODUCTION");
      ps = conn.prepareStatement("SELECT * FROM PRODUCTION");
      rs = ps.executeQuery();
      System.out.println("Executed query.");

      // For each row in the table...
      while (rs.next()) {

        String productName = rs.getString("PRODUCT_NAME");

        // Loop through the product line and find the product associated with the
        // current row in the production table.
        for (Product product : productLine) {
          if (product.getName().equals(productName)) {
            productInProduction = product;
            break;
          }
        }

        // Add the production to the ArrayList, but not before checking to see
        // if the product associated with it was found in the product line.
        if (productInProduction != null) {
          Production productionToAdd = new Production(
              productInProduction,
              rs.getInt("QUANTITY"),
              new Date(rs.getTimestamp("MANUFACTURE_DATE").getTime())
          );
          productionRun.add(productionToAdd);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.print("Could not execute query.");
    }

    return productionRun;
  }

  /**
   * Insert a new AudioPlayer into the PRODUCTS table.
   *
   * @param name         The name of the product in the row entry.
   * @param manufacturer The manufacturer of the product in the row entry.
   * @param type         The "type" of the product in the row entry (see ItemType enum).
   */
  void insertRowIntoProductTable(String name, String manufacturer, String type) {

    System.out.println("Inserting records into products table...");
    try {
      ps = conn.prepareStatement("INSERT INTO PRODUCTS ("
          + "name, manufacturer, type"
          + ") VALUES ('"
          + name + "', '"
          + manufacturer + "', '"
          + type + "')");

      ps.executeUpdate();
      System.out.println("Inserted records into products table.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Could not create record.");
    }
  }

  /**
   * Insert a new MoviePlayer into the PRODUCTS table.
   *
   * @param name         The name of the product in the row entry.
   * @param manufacturer The manufacturer of the product in the row entry.
   * @param type         The "type" of the product in the row entry (see ItemType enum).
   * @param resolution   The resolution of the screen on the movie player.
   * @param refreshRate  The refresh rate (in Hz) of the screen on the movie player.
   * @param responseTime The response time (in ms) of the screen on the movie player.
   */
  void insertRowIntoProductTable(String name, String manufacturer, String type,
                                  String resolution, int refreshRate, int responseTime,
                                  MonitorType monitorType) {

    System.out.println("Inserting records into products table...");
    try {
      ps = conn.prepareStatement("INSERT INTO PRODUCTS ("
          + "name, manufacturer, type, screen_resolution, "
          + "screen_refresh, screen_response, monitor_type"
          + ") VALUES ('"
          + name + "', '"
          + manufacturer + "', '"
          + type + "', '"
          + resolution + "', '"
          + refreshRate + "', '"
          + responseTime + "', '"
          + monitorType + "')"
      );

      ps.executeUpdate();
      System.out.println("Inserted records into products table.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Could not create record.");
    }
  }

  /**
   * Insert new rows into the PRODUCTION table.
   *
   * @param name           The name of the product in the row entry.
   * @param quantity       The amount of products that were manufactured.
   * @param manufacturedOn The date and time the products were manufactured.
   */
  void insertRowIntoProductionTable(String name, int quantity, Date manufacturedOn) {

    System.out.println("Inserting records into production table...");
    
    try {
      ps = conn.prepareStatement(
          "INSERT INTO PRODUCTION VALUES ("
              + "default, '"
              + name + "', '"
              + quantity + "', '"
              + new Timestamp(manufacturedOn.getTime())
              + "')"
      );

      ps.executeUpdate();
      System.out.println("Inserted records into production table.");
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