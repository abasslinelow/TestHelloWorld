/*
9/24/2019
Created by: Todd Bauer

This program is a simple JavaFX program with 3 tabs and the ability to connect to a
database and execute SQL statements.
 */

package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("JavaFX Welcome");

    // Create tabs in the application and do not let the user close them.
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

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

    // Create a new tab, insert the login pane, then add it to the TabPane.
    Tab loginTab = new Tab();
    loginTab.setText("Login");
    loginTab.setContent(loginPane);
    loginTab.setClosable(false);
    tabPane.getTabs().add(loginTab);

    // Create the second tab. (This is blank for now)
    Tab tab2 = new Tab();
    tab2.setText("Tab 2");
    tabPane.getTabs().add(tab2);

    // Create the third tab. (This is blank for now)
    Tab tab3 = new Tab();
    tab3.setText("Tab 3");
    tabPane.getTabs().add(tab3);

    // When the user clicks the login button, connect to the database
    // and attempt to execute SQL statements.
    btn.setOnAction(event -> {

      // Create a new database manager object for database manipulation.
      DBManager db = new DBManager(userTextField.getText(), pwBox.getText());

      // If a connection was established, run some SQL queries to test function.
      // Else, inform the user that a connection could not be made.
      if (db.getConnectionStatus()) {
        //db.createTable("TEST");
        //db.insertRowIntoTestTable(3, "Bill", "Burr");
        db.listRowsInTable("TEST");
        db.listColumnNamesInTable("TEST");
        db.disconnectFromDB();
        actiontarget.setText("Connection successful.");
      } else {
        actiontarget.setText("Connection failed.\nCheck name and password.");
      }
    });

    // Create and show the scene from the TabPane.
    Scene scene = new Scene(tabPane, 300, 275);
    primaryStage.setScene(scene);
    scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
    primaryStage.show();
  }
}