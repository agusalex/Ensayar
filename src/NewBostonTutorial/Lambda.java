package NewBostonTutorial;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Max on 11/2/2016.
 */
public class Lambda extends Application {

    Button button;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ofertas");
        button = new Button("Click me");

        button.setOnAction(event -> {
            System.out.println("lambda1");
            System.out.println("lambda2");
        });



        Button button2= new Button("no touching");
        button2.setOnAction(event -> AlertBoxes.display("lo tocaste!!","error- error-error"));
        button2.setAlignment(Pos.BOTTOM_CENTER);


        StackPane layout = new StackPane();  //layout de componentes
        //vbox toma un entero que determina el espacio vertical entre cada componenete, recomiendo usar ese
        layout.getChildren().addAll(button,button2);  // agrega de abajo a arriba, el ultimo boton lo tapa al primero

        Scene scene = new Scene (layout, 300,250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String []args){
        launch(args);
    }


}
