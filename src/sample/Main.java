package sample;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * This program is a simple JavaFX program with 3 tabs and the ability to connect to a
 * database and execute SQL statements. It contains various objects that represent
 * multimedia players and allows the user to catalog these products and their
 * production runs.
 * @author Todd Bauer
 * @since 9/24/2019
 */

public class Main extends Application {

  /**
   * This holds a list of all products.
   */
  private ArrayList<Product> productLine = new ArrayList<>();

  /**
   * This holds a list of all production runs.
   */
  private ArrayList<Production> productionRun = new ArrayList<>();

  /**
   * This launches the main loop with any argument given by the user.
   * @param args An argument passed to the program on runtime.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * This method creates the GUI elements and serves as the main loop.
   * @param primaryStage The Stage object to create GUI elements on.
   */
  @Override
  public void start(Stage primaryStage) {

    // Populates the productLine and productionRun ArrayLists with their
    // corresponding rows in the PRODUCT and PRODUCTION database tables.
    populateLists();

    primaryStage.setTitle("Product Manager");

    // Create tabs in the application and do not let the user close them.
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    // Create a new tab, insert the login pane, then add it to the TabPane.
    Tab loginTab = new Tab();
    loginTab.setText("Login");
    loginTab.setContent(createLoginPane());
    loginTab.setClosable(false);
    tabPane.getTabs().add(loginTab);

    // Create the second tab, insert the products pane, then add it to the TabPane.
    Tab productTab = new Tab();
    productTab.setText("Products");
    productTab.setContent(createProductPane());
    productTab.setClosable(false);
    tabPane.getTabs().add(productTab);

    // Create the third tab, insert the production pane, then add it to the TabPane.
    Tab productionTab = new Tab();
    productionTab.setText("Production");
    productionTab.setContent(createProductionPane());
    productionTab.setClosable(false);
    tabPane.getTabs().add(productionTab);

    // Create and show the scene from the TabPane.
    Scene scene = new Scene(tabPane, 300, 275);
    primaryStage.setScene(scene);
    scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
    primaryStage.show();
  }

  private GridPane createLoginPane() {
    // Login pane creation. This will be inserted into the TabPane.
    GridPane loginPane = new GridPane();
    loginPane.setAlignment(Pos.CENTER);
    loginPane.setHgap(10);
    loginPane.setVgap(10);
    loginPane.setPadding(new Insets(25, 25, 25, 25));

    Text scenetitle = new Text("Welcome");
    scenetitle.setId("welcome-text");
    loginPane.add(scenetitle, 0, 0, 2, 1);

    Label userName = new Label("User Name:");
    loginPane.add(userName, 0, 1);

    TextField userTextField = new TextField();
    loginPane.add(userTextField, 1, 1);

    Label pw = new Label("Password:");
    loginPane.add(pw, 0, 2);

    PasswordField pwBox = new PasswordField();
    loginPane.add(pwBox, 1, 2);

    // Create the login button.
    Button btn = new Button("Sign in");
    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btn);
    loginPane.add(hbBtn, 1, 4);

    // Create a text label for printing errors and confirmations.
    final Text actiontarget = new Text();
    actiontarget.setId("actiontarget");
    loginPane.add(actiontarget, 1, 5);

    // When the user clicks the login button, connect to the database
    // and attempt to execute SQL statements.
    btn.setOnAction(event -> {

      // Create a new database manager object for database manipulation.
      DatabaseManager db = new DatabaseManager(userTextField.getText(), pwBox.getText());

      // If a connection was established, run some SQL queries to test function.
      // Else, inform the user that a connection could not be made.
      if (db.getConnectionStatus()) {
        //db.createTable("Products");
        //db.insertRowIntoTestTable(1, "iPod", "Apple", "AM");
        db.listRowsInProductsTable();
        db.listColumnsInTable("PRODUCTS");
        db.disconnectFromDB();
        actiontarget.setText("Connection to db successful.");
      } else {
        actiontarget.setText("Connection to db failed.\nCheck name and password.");
      }
    });

    return loginPane;
  }

  private GridPane createProductPane() {
    // productPane pane creation. This will be inserted into the TabPane.
    GridPane productPane = new GridPane();
    productPane.setAlignment(Pos.CENTER);
    productPane.setHgap(10);
    productPane.setVgap(10);
    productPane.setPadding(new Insets(25, 25, 25, 25));

    Text scenetitle = new Text("Products");
    scenetitle.setId("welcome-text");
    productPane.add(scenetitle, 0, 0, 2, 1);

    // Product name textfield.
    Label productNameLabel = new Label("Product Name:");
    productPane.add(productNameLabel, 0, 1);
    TextField productNameTextfield = new TextField();
    productPane.add(productNameTextfield, 1, 1);

    // Product manufacturer textfield.
    Label productManufacturerLabel = new Label("Manufacturer:");
    productPane.add(productManufacturerLabel, 0, 2);
    TextField productManufacturerTextfield = new TextField();
    productPane.add(productManufacturerTextfield, 1, 2);

    // Product type selector.
    Label productTypeLabel = new Label("Product Type:");
    productPane.add(productTypeLabel, 0, 3);
    ComboBox<ItemType> productTypeCBox =
        new ComboBox<>(FXCollections.observableArrayList(ItemType.values()));
    productPane.add(productTypeCBox, 1, 3);

    // Create the Add Product button.
    Button addProductBtn = new Button("Add Product");
    HBox hbAddProductBtn = new HBox(10);
    hbAddProductBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbAddProductBtn.getChildren().add(addProductBtn);
    productPane.add(hbAddProductBtn, 1, 4);

    // Create a text label for printing errors and confirmations.
    final Text actiontarget = new Text();
    actiontarget.setId("actiontarget");
    productPane.add(actiontarget, 0, 5, 2, 1);

    addProductBtn.setOnAction(event -> {

      Widget userWidget = new Widget(
          productNameTextfield.getText(),
          productTypeCBox.getValue().type
      );
      userWidget.setManufacturer(productManufacturerTextfield.getText());

      // Create a new database manager object for database manipulation.
      DatabaseManager db = new DatabaseManager("sa", "sa");

      if (db.getConnectionStatus()) {

        db.insertRowIntoProductTable(3, userWidget.getName(),
            userWidget.getManufacturer(), userWidget.getType());
        productLine.add(userWidget);

        db.listRowsInProductsTable();
        db.listColumnsInTable("PRODUCTS");
        db.disconnectFromDB();
        actiontarget.setText("Connection to db successful.");
      } else {
        actiontarget.setText("Connection to db failed.\nCheck name and password.");
      }
    });

    return productPane;
  }

  private GridPane createProductionPane() {

    // productionPane pane creation. This will be inserted into the TabPane.
    GridPane productionPane = new GridPane();
    productionPane.setAlignment(Pos.CENTER);
    productionPane.setHgap(10);
    productionPane.setVgap(10);
    productionPane.setPadding(new Insets(25, 25, 25, 25));

    Text scenetitle = new Text("Production");
    scenetitle.setId("welcome-text");
    productionPane.add(scenetitle, 0, 0, 2, 1);

    // Product name ComboBox.
    Label productionNameLabel = new Label("Product Name:");
    productionPane.add(productionNameLabel, 0, 1);

    // Rather than displaying the entire toString() of a product, just show its name.
    ArrayList<String> names = new ArrayList<>();
    for (Product p : productLine) {
      names.add(p.getName());
    }
    ComboBox<String> productionNameCBox =
        new ComboBox<>(FXCollections.observableArrayList(names));

    productionPane.add(productionNameCBox, 1, 1);

    // Product quantity textfield.
    Label productionQuantityLabel = new Label("Quantity:");
    productionPane.add(productionQuantityLabel, 0, 2);
    TextField productionQuantityTextfield = new TextField();
    productionPane.add(productionQuantityTextfield, 1, 2);

    // Create the Add Product button.
    Button addProductionBtn = new Button("Add Product");
    HBox hbAddProductionBtn = new HBox(10);
    hbAddProductionBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbAddProductionBtn.getChildren().add(addProductionBtn);
    productionPane.add(hbAddProductionBtn, 1, 3);

    // Create a text label for printing errors and confirmations.
    final Text actiontarget = new Text();
    actiontarget.setId("actiontarget");
    productionPane.add(actiontarget, 0, 4, 2, 1);

    addProductionBtn.setOnAction(event -> {

      Production productionRecord;

      // Find the Product in productLine that corresponds to the product name
      // in the ComboBox, then use it to create a Production record.
      for (Product product : productLine) {
        if (product.getName().equals(productionNameCBox.getValue())) {
          productionRecord = new Production(
              product,
              Integer.parseInt(productionQuantityTextfield.getText())
          );
          productionRun.add(productionRecord);

          // Create a new database manager object for database manipulation.
          DatabaseManager db = new DatabaseManager("sa", "sa");

          if (db.getConnectionStatus()) {

            db.insertRowIntoProductionTable(
                3,
                productionRecord.getName(),
                productionRecord.getQuantity(),
                productionRecord.getManufactureDate()
            );

            db.listRowsInProductionTable();

            db.disconnectFromDB();

            actiontarget.setText("Connection successful.");
          } else {
            actiontarget.setText("Connection to db failed.\nCheck name and password.");
          }
        }
      }
    });

    return productionPane;
  }

  /**
   * Populates the productLine and productRun ArrayLists with information
   * from the database.
   */
  private void populateLists() {

    DatabaseManager db = new DatabaseManager("sa", "sa");
    productLine = db.listRowsInProductsTable();
    productionRun = db.listRowsInProductionTable();

    System.out.print(productLine + "\n\n");
    System.out.print(productionRun + "\n\n");
  }
}