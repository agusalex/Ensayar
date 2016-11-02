package NewBostonTutorial;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Created by Max on 11/2/2016.
 */
public class Layouts extends Application{

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("thenewboston - JavaFX");

        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Name Label - constrains use (child, column, row)
        Button nameLabel = new Button("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        //Name Input
        Button nameInput = new Button("Bucky");
        GridPane.setConstraints(nameInput, 1, 0);

        //Password Label
        Button passLabel = new Button("Password:");
        GridPane.setConstraints(passLabel, 0, 1);

        //Password Input
        Button passInput = new Button();
        GridPane.setConstraints(passInput, 1, 1);

        //Login
        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton, 1, 2);

        //Add everything to grid
        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.show();
    }




    public static void main(String []args){
        launch(args);
    }

}
