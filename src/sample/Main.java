package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

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

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        loginPane.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        actiontarget.setId("actiontarget");
        loginPane.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setText("Sign in button pressed");
            }
        });

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

        // Create and show the scene from the TabPane.
        Scene scene = new Scene(tabPane, 300, 275);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        primaryStage.show();

        try {
            Class.forName ("org.h2.Driver");
            Connection conn = DriverManager.getConnection ("jdbc:h2:./test", "sa","sa");
            Statement st = conn.createStatement();

            /*
            System.out.println("Creating table...");
            String sql = "CREATE TABLE TEST (uid INT PRIMARY KEY, fname VARCHAR(255), lname VARCHAR(255))";
            st.executeUpdate(sql);
            System.out.println("Created table.");
            */

            /*
            System.out.println("Inserting records into table...");
            sql = "INSERT INTO TEST VALUES (1, 'Todd', 'Bauer')";
            st.executeUpdate(sql);
            System.out.println("Inserted records into table.");
            */

            /*
            System.out.println("Executing query...");
            sql = "SELECT * FROM TEST";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println("UID: " + rs.getInt("uid") + " First Name: " +
                        rs.getString("fname") + " Last Name: " +
                        rs.getString("lname"));
            }
            System.out.println("Executed query.");
            */

            /*
            System.out.println("Listing column names and their data types...");
            String sql = "SELECT COLUMN_NAME, TYPE_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println("1: " + rs.getString(1) + " 2: " + rs.getString(2));
            }
            System.out.println("Listed column names and their data types.");
            */

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}