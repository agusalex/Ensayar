package UI;

import Data.Client;
import Data.Offer;
import Data.Schedule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

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
    private TextField MinEntry;

    @FXML
    private TextField MinEndEntry;


    @FXML
    void createOffer(ActionEvent event) {
        //TODO comprobar validez de campos (me encargo yo agus)

        ArrayList<Offer.Instruments> offerInst = new ArrayList<Offer.Instruments>();
        if(Bateria.isSelected())
            offerInst.add(Offer.Instruments.BATERIA);
        if(Bajo.isSelected())
            offerInst.add(Offer.Instruments.BAJO);
        if(Guitarra.isSelected())
            offerInst.add(Offer.Instruments.GUITARRA);
        if(Teclado.isSelected())
            offerInst.add(Offer.Instruments.TECLADO);
        if(Microfono.isSelected())
            offerInst.add(Offer.Instruments.MICROFONO);

        String name = nameEntry.getText();
        String phone = PhoneEntry.getText();
        String ID = IDEntry.getText();
        Client client = new Client(name);
        client.setID(ID);
        client.setMobile(phone);


        int startHours = Integer.parseInt(StartEntry.getText());
        int endHours = Integer.parseInt(EndEntry.getText());
        int startMins = Integer.parseInt(MinEntry.getText());
        int endMins = Integer.parseInt(MinEndEntry.getText());
        Schedule hours = new Schedule(startHours,startMins,endHours,endMins);

         Manager.setOffer(offerInst,hours,client);

        Stage stage = (Stage) TotalPrice.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void cancelOffer(ActionEvent event) {
        Stage stage = (Stage) TotalPrice.getScene().getWindow();
        stage.close();


    }


}
