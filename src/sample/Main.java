package sample;

import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This program is a simple JavaFX program with 3 tabs and the ability to connect to a database and
 * execute SQL statements. It contains various objects that represent multimedia players and allows
 * the user to catalog these products and their production runs.
 *
 * @author Todd Bauer
 * @since 9/24/2019
 */

public class Main extends Application {

  // Create a new database manager object for database manipulation.
  DatabaseManager db = new DatabaseManager("sa", "sa");

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
   *
   * @param args An argument passed to the program on runtime.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * This method creates the GUI elements and serves as the main loop.
   *
   * @param primaryStage The Stage object to create GUI elements on.
   */
  @Override
  public void start(Stage primaryStage) {

    // Populates the productLine and productionRun ArrayLists with their
    // corresponding rows in the PRODUCT and PRODUCTION database tables.
    populateLists();

    System.out.println();
    System.out.println("PRODUCT LINE:");
    print(productLine);

    System.out.println();
    System.out.println("PRODUCTION RUN:");
    print(productionRun);
    System.out.println();

    productLine.sort(Product.ProductNameComparator);
    productionRun.sort(Production.ProductionNameComparator);

    System.out.println();
    System.out.println("PRODUCT LINE - SORTED:");
    print(productLine);

    System.out.println();
    System.out.println("PRODUCTION RUN - SORTED:");
    print(productionRun);
    System.out.println();

    System.out.println("ONLY AUDIO DEVICES:");
    Product.printType(productLine, ItemType.AUDIO);

    System.out.println("ONLY AUDIO_MOBILE DEVICES:");
    Product.printType(productLine, ItemType.AUDIO_MOBILE);

    System.out.println("ONLY VISUAL DEVICES:");
    Product.printType(productLine, ItemType.VISUAL);

    System.out.println("ONLY VISUAL_MOBILE DEVICES:");
    Product.printType(productLine, ItemType.VISUAL_MOBILE);

    primaryStage.setTitle("Product Manager");
    primaryStage.setWidth(512);
    primaryStage.setHeight(480);

    // Create tabs in the application and do not let the user close them.
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    tabPane.getTabs().add(createLoginTab());
    tabPane.getTabs().add(createProductTab());
    tabPane.getTabs().add(createProductionTab());

    // Create and show the scene from the TabPane.
    Scene scene = new Scene(tabPane, 640, 480);
    primaryStage.setScene(scene);
    scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
    primaryStage.show();
  }

  /**
   * Creates the contents of the login tab.
   *
   * @return The login tab.
   */
  private Tab createLoginTab() {
    // Login pane creation. This will be inserted into the TabPane.
    GridPane loginPane = new GridPane();
    loginPane.setAlignment(Pos.CENTER);
    loginPane.setHgap(10);
    loginPane.setVgap(10);
    loginPane.setPadding(new Insets(25, 25, 25, 25));

    Text scenetitle = new Text("Welcome");
    scenetitle.setId("welcome-text");
    GridPane.setHalignment(scenetitle, HPos.CENTER);
    loginPane.add(scenetitle, 0, 0, 3, 1);

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

    // Create the second tab, insert the products pane, then return it.
    Tab loginTab = new Tab();
    loginTab.setText("Login");
    loginTab.setContent(loginPane);
    loginTab.setClosable(false);

    return loginTab;
  }

  /**
   * Creates the contents of the product tab.
   *
   * @return The product tab.
   */
  private Tab createProductTab() {
    // productPane pane creation. This will be inserted into the TabPane.
    GridPane productPane = new GridPane();
    productPane.setAlignment(Pos.CENTER);
    productPane.setHgap(10);
    productPane.setVgap(10);
    productPane.setPadding(new Insets(25, 25, 25, 25));

    Text scenetitle = new Text("Product Line");
    scenetitle.setId("welcome-text");
    GridPane.setHalignment(scenetitle, HPos.CENTER);
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
    productTypeCBox.setPrefWidth(180);
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

    // Movie player screen properties. Only show if a movie player is selected.
    // Monitor type selector.
    Label monitorTypeLabel = new Label("Monitor Type:");
    productPane.add(monitorTypeLabel, 0, 5);
    ComboBox<MonitorType> monitorTypeCBox =
        new ComboBox<>(FXCollections.observableArrayList(MonitorType.values()));
    productTypeCBox.setPrefWidth(180);
    productPane.add(monitorTypeCBox, 1, 5);
    monitorTypeLabel.setVisible(false);
    monitorTypeCBox.setVisible(false);

    // Screen resolution Textfield.
    Label resolutionLabel = new Label("Screen resolution (e.g. 800x600):");
    productPane.add(resolutionLabel, 0, 6);
    TextField resolutionTextfield = new TextField();
    productPane.add(resolutionTextfield, 1, 6);
    resolutionLabel.setVisible(false);
    resolutionTextfield.setVisible(false);

    // Refresh rate Textfield.
    Label refreshRateLabel = new Label("Refresh rate (Hz): ");
    productPane.add(refreshRateLabel, 0, 7);
    TextField refreshRateTextfield = new TextField();
    productPane.add(refreshRateTextfield, 1, 7);
    refreshRateLabel.setVisible(false);
    refreshRateTextfield.setVisible(false);

    // Response time Textfield.
    Label responseTimeLabel = new Label("Response time (ms): ");
    productPane.add(responseTimeLabel, 0, 8);
    TextField responseTimeTextfield = new TextField();
    productPane.add(responseTimeTextfield, 1, 8);
    responseTimeLabel.setVisible(false);
    responseTimeTextfield.setVisible(false);

    productTypeCBox.setOnAction(event -> {
      if (productTypeCBox.getValue() == ItemType.VISUAL
          || productTypeCBox.getValue() == ItemType.VISUAL_MOBILE) {
        resolutionLabel.setVisible(true);
        resolutionTextfield.setVisible(true);
        refreshRateLabel.setVisible(true);
        refreshRateTextfield.setVisible(true);
        responseTimeLabel.setVisible(true);
        responseTimeTextfield.setVisible(true);
        monitorTypeLabel.setVisible(true);
        monitorTypeCBox.setVisible(true);
        GridPane.setRowIndex(hbAddProductBtn, 9);
        GridPane.setRowIndex(actiontarget, 10);
      } else {
        resolutionLabel.setVisible(false);
        resolutionTextfield.setVisible(false);
        resolutionTextfield.setText("");
        refreshRateLabel.setVisible(false);
        refreshRateTextfield.setVisible(false);
        refreshRateTextfield.setText("");
        responseTimeLabel.setVisible(false);
        responseTimeTextfield.setVisible(false);
        responseTimeTextfield.setText("");
        monitorTypeLabel.setVisible(false);
        monitorTypeCBox.setVisible(false);
        GridPane.setRowIndex(hbAddProductBtn, 4);
        GridPane.setRowIndex(actiontarget, 5);
      }
    });

    addProductBtn.setOnAction(event -> {

      // Create a new database manager object for database manipulation.
      DatabaseManager db = new DatabaseManager("sa", "sa");

      // Establish a connection to the database.
      if (db.getConnectionStatus()) {

        // Determine whether the product is movie player or an audio player.
        if (hasScreen(productTypeCBox.getValue())) {

          // If movie player, make a screen, make the product, then add to database.
          Screen screen = new Screen(
              resolutionTextfield.getText(),
              Integer.parseInt(refreshRateTextfield.getText()),
              Integer.parseInt(responseTimeTextfield.getText())
          );
          MoviePlayer product = new MoviePlayer(
              productNameTextfield.getText(),
              productManufacturerTextfield.getText(),
              productTypeCBox.getValue().type,
              screen,
              monitorTypeCBox.getValue()
          );
          db.insertRowIntoProductTable(
              product.getName(),
              product.getManufacturer(),
              product.getType(),
              product.getScreen().getResolution(),
              product.getScreen().getRefreshRate(),
              product.getScreen().getResponseTime(),
              product.getMonitorType()
          );
          productLine.add(product);
        } else {

          // Else there is no screen, just make the product and add to database.
          AudioPlayer product = new AudioPlayer(
              productNameTextfield.getText(),
              productManufacturerTextfield.getText(),
              productTypeCBox.getValue().type
          );

          db.insertRowIntoProductTable(
              product.getName(),
              product.getManufacturer(),
              product.getType()
          );
          productLine.add(product);
        }

        // Disconnect from the database and repopulate the lists to reflect the new product.
        db.disconnectFromDB();
        populateLists();
        actiontarget.setText("Connection to db successful.");
      } else {
        actiontarget.setText("Connection to db failed.\nCheck name and password.");
      }
    });

    // Create the second tab, insert the products pane, then add it to the TabPane.
    Tab productTab = new Tab();
    productTab.setText("Products");
    productTab.setContent(productPane);
    productTab.setClosable(false);

    return productTab;
  }

  /**
   * Determines if a product has a screen.
   * @param type The type of product (Audio, AudioMobile, Visual, VisualMobile)
   * @return boolean True if has a screen, false if not.
   */
  private boolean hasScreen(ItemType type) {
    switch (type) {
      case VISUAL:
      case VISUAL_MOBILE:
        return true;
      default:
        return false;
    }
  }

  /**
   * Creates the contents of the production tab.
   *
   * @return The production tab.
   */
  private Tab createProductionTab() {

    // productionPane pane creation. This will be inserted into the TabPane.
    GridPane productionPane = new GridPane();
    productionPane.setAlignment(Pos.CENTER);
    productionPane.setHgap(10);
    productionPane.setVgap(10);
    productionPane.setPadding(new Insets(15, 25, 25, 15));

    Text scenetitle = new Text("Production Run");
    scenetitle.setId("welcome-text");
    GridPane.setHalignment(scenetitle, HPos.CENTER);
    productionPane.add(scenetitle, 0, 0, 4, 1);

    // Production table from database.
    TableView<Production> productionTable = new TableView<>();
    productionTable.setEditable(true);

    // The various columns of the database.  Makes a new column that will draw a String
    // from a Production object, then tie this to the "name" attribute in a Production.
    // setCellValueFactory uses reflection to automatically infer the getters and setters.
    TableColumn<Production, String> productNameCol = new TableColumn<>("Product Name");
    productNameCol.setStyle("-fx-alignment: CENTER;");
    productNameCol.setCellValueFactory(
        new PropertyValueFactory<>("name"));
    productionTable.getColumns().add(productNameCol);

    TableColumn<Production, String> manufacturerCol = new TableColumn<>("Manufacturer");
    manufacturerCol.setStyle("-fx-alignment: CENTER;");
    manufacturerCol.setCellValueFactory(
        new PropertyValueFactory<>("manufacturer"));
    productionTable.getColumns().add(manufacturerCol);

    TableColumn<Production, Integer> quantityCol = new TableColumn<>("Quantity");
    quantityCol.setStyle("-fx-alignment: CENTER;");
    quantityCol.setCellValueFactory(
        new PropertyValueFactory<>("quantity"));
    productionTable.getColumns().add(quantityCol);

    TableColumn<Production, Date> manufactureDateCol = new TableColumn<>("Manufacture Date");
    manufactureDateCol.setStyle("-fx-alignment: CENTER;");
    manufactureDateCol.setCellValueFactory(
        new PropertyValueFactory<>("manufacturedOn"));
    productionTable.getColumns().add(manufactureDateCol);

    productionTable.setMinWidth(467);
    productionTable.setItems(populateProductionTable());
    productionPane.add(productionTable, 0, 1, 4, 1);

    // Product name ComboBox.
    Label productionNameLabel = new Label("Product Name:");
    productionNameLabel.setMinWidth(300);
    productionNameLabel.setAlignment(Pos.CENTER_RIGHT);
    productionPane.add(productionNameLabel, 2, 2);

    // Rather than displaying the entire toString() of a product, just show its name.
    ComboBox<String> productionNameCBox =
        new ComboBox<>(FXCollections.observableArrayList(getNamesOfProducts()));

    productionNameCBox.setPrefWidth(140);
    GridPane.setHalignment(productionNameCBox, HPos.RIGHT);
    productionPane.add(productionNameCBox, 3, 2);

    // Product quantity textfield.
    Label productionQuantityLabel = new Label("Quantity:");
    productionQuantityLabel.setMinWidth(300);
    productionQuantityLabel.setAlignment(Pos.CENTER_RIGHT);
    productionPane.add(productionQuantityLabel, 2, 3);
    TextField productionQuantityTextfield = new TextField();
    productionQuantityTextfield.setAlignment(Pos.CENTER_RIGHT);
    GridPane.setHalignment(productionQuantityTextfield, HPos.RIGHT);
    productionQuantityTextfield.setMaxWidth(140);
    productionPane.add(productionQuantityTextfield, 3, 3);

    // Create the Add Product button.
    Button addProductionBtn = new Button("Add Product");
    HBox hbAddProductionBtn = new HBox(10);
    hbAddProductionBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbAddProductionBtn.getChildren().add(addProductionBtn);
    productionPane.add(hbAddProductionBtn, 3, 4);

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
                productionRecord.getName(),
                productionRecord.getQuantity(),
                productionRecord.getManufacturedOn()
            );
            db.listRowsInProductionTable();
            db.disconnectFromDB();

            productionTable.setItems(populateProductionTable());
          }
        }
      }
    });


    // Create the second tab, insert the products pane, then return it.
    Tab productionTab = new Tab();
    productionTab.setText("Productions");
    productionTab.setContent(productionPane);
    productionTab.setClosable(false);

    // Set an event handler for when the tab is focused. This will refresh all
    // lists as well as repopulate the Product Name ComboBox and database table.
    productionTab.setOnSelectionChanged(event -> {
      if (productionTab.isSelected()) {
        populateLists();
        productionNameCBox.setItems(FXCollections.observableArrayList(getNamesOfProducts()));
        productionTable.setItems(populateProductionTable());
      }
    });

    return productionTab;
  }

  /**
   * Populates the Production table with the contents of the Production database table.
   *
   * @return ObservableList The contents of the Production table in the database.
   */
  private ObservableList<Production> populateProductionTable() {

    DatabaseManager db = new DatabaseManager("sa", "sa");

    final ObservableList<Production> data =
        FXCollections.observableArrayList(db.listRowsInProductionTable());

    db.disconnectFromDB();

    return data;
  }

  /**
   * Populates the productLine and productRun ArrayLists with information from the database.
   */
  private void populateLists() {

    DatabaseManager db = new DatabaseManager("sa", "sa");
    productLine = db.listRowsInProductsTable();
    productionRun = db.listRowsInProductionTable();
    db.disconnectFromDB();
  }

  /**
   * Populates the ComboBox with the names of all current products.
   * @return ArrayList Strings of all product names in the productLine ArrayList.
   */
  private ArrayList<String> getNamesOfProducts() {
    // Rather than displaying the entire toString() of a product, just show its name.
    ArrayList<String> names = new ArrayList<>();
    for (Product p : productLine) {
      names.add(p.getName());
    }

    return names;
  }

  /**
   * Iterates through an ArrayList and prints all of the elements. This should work with
   * any of the Collections within this project.
   *
   * @param list The ArrayList to iterate through.
   */
  private void print(ArrayList list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i).toString());
      System.out.println("--------------------");
    }
  }
}