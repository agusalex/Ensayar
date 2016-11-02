package NewBostonTutorial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Max on 11/2/2016.
 */
public class SwitchingScenes extends Application{

    Stage window;
    Scene scene1, scene2;


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        Label label1 = new Label ("first scene");
        Button button1 = new Button("go to scene 2");
        button1.setOnAction(event -> window.setScene(scene2));

        // layout 1-- vbox(como stackPane) va apilando obetos uno arriba del otro
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1,button1);
        scene1 = new Scene(layout1, 200,200);


        Button button2 = new Button("go back to scene 1");
        button2.setOnAction(event -> window.setScene(scene1));

        //layout2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2,600,300);

        window.setScene(scene1);
        window.setTitle("scene1");
        window.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
