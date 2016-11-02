package NewBostonTutorial;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Max on 11/2/2016.
 */
public class AlertBoxes {

    public static void display(String title, String message){
        Stage alert = new Stage();

        //con esto bloquea las otras ventanas hata que se cierre esta
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setMinWidth(250);

        Label label1  = new Label();
        label1.setText(message);
        Button button = new Button("close the window");
        button.setOnAction(event -> alert.close());

        VBox layout= new VBox (10);
        layout.getChildren().addAll(label1,button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene((layout));
        alert.setScene(scene);
        alert.showAndWait();
    }
}
