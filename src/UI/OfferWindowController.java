package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class OfferWindowController {


    @FXML
    private CheckBox Microfono;

    @FXML
    private Button cancel;

    @FXML
    private TextField adittionalValue;

    @FXML
    private TextField IDEntry;

    @FXML
    private CheckBox Bateria;

    @FXML
    private TextField StartEntry;

    @FXML
    private TextField PhoneEntry;

    @FXML
    private CheckBox Guitarra;

    @FXML
    private TextField EndEntry;

    @FXML
    private Button Create;

    @FXML
    private Label TotalPrice;

    @FXML
    private CheckBox Teclado;

    @FXML
    private CheckBox Bajo;

    @FXML
    private TextField nameEntry;

    @FXML
    private ScrollPane window;


    @FXML
    void createOffer(ActionEvent event) {

        Stage stage = (Stage) TotalPrice.getScene().getWindow();
        stage.close();


        //ArrayList<Offer.Instruments> offerInst = new ArrayList<Offer.Instruments>();
        //TODO agregar los checkboxes seleccionados
        //String name = nameEntry.getText();
        //TODO agregar campos de cliente
        //String telefono
        //string id
        //Client client = new Client(name, ID, Telefono);

        //TODO setear schedule
        //Schedule hours = new Schedule (start.getText(), end.getText());

        //TODO setear oferta
        // Manager.setOffer(offerInst,schedule,client);
        System.out.print("sadsa");

    }

    @FXML
    private void cancelOffer(ActionEvent event) {
        Stage stage = (Stage) TotalPrice.getScene().getWindow();
        stage.close();


    }


}
