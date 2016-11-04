package UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Max on 11/2/2016.
 */
public class MainWindow  extends Application {

    Button button1,button2,button3,button4;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ofertas");


        VBox unnasignedOffersLayout = new VBox(5);
        button1 = new Button("Boton1");
        button2 = new Button("Boton2");
        unnasignedOffersLayout.getChildren().addAll(button1,button2);

        //vertical box pone objetos uno arriba del otro
        //la distancia vertical determina el espacio entre cada uno
        VBox asignedOffersLayout = new VBox(5);
        button3 = new Button("Boton3");
        button4 = new Button("Boton4");
        asignedOffersLayout.getChildren().addAll(button3,button4);

        //este layout tiene 4 ubicaciones, top,bottom,left,right  permite poner varios layouts repartidos en la pantalla
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(unnasignedOffersLayout);
        borderPane.setCenter(asignedOffersLayout);

        Scene scene = new Scene(borderPane,800,400);
        primaryStage.setScene(scene);
        primaryStage.show();






    }

    public static void main(String[] args) {
        launch(args);
    }
}
