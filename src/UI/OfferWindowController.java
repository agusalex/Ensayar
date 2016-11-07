package UI;

import Data.Client;
import Data.Offer;
import Data.Schedule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OfferWindowController implements Initializable {

    public static OfferWindow offerWindow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        for(int x=0;x<9;x++) {
            startH.getItems().add("0" + x);
            endH.getItems().add("0" + x);
        }
        for(int x=10;x<24;x++) {
            startH.getItems().add(Integer.toString(x));
            endH.getItems().add(Integer.toString(x));
        }

        startMin.getItems().add("00");
        startMin.getItems().add("30");
        endMin.getItems().add("00");
        endMin.getItems().add("30");

        setDefaultHoursAndMinutes();





    }

    @FXML
    private ChoiceBox<String> startH;
    @FXML
    private ChoiceBox<String> endMin;
    @FXML
    private ChoiceBox<String> endH;
    @FXML
    private ChoiceBox<String> startMin;
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
    private TextField PhoneEntry;

    @FXML
    private CheckBox Guitarra;

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
    private Label nameError;

    @FXML
    private Label priceError;

    @FXML
    private Label minutesError;

    @FXML
    private Label PhoneError;

    @FXML
    private Label hourError;

    @FXML
    private Label IDError;


    @FXML
    void createOffer(ActionEvent event) {

        boolean noErrorsFound = true;  //esto revisa si encontre alguno de los errores de arriba


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


        //Validando inputs - Cliente
        Client client = null ;
        String name = nameEntry.getText();
        if(isValidName(name) == false ){
            System.out.println("error nombre");
            noErrorsFound = false;
            nameError.setVisible(true);
            nameEntry.setText("");
        }
        else nameError.setVisible(false);

        String phone = PhoneEntry.getText();
        if(isInt(phone) == false || phone.length() != 10){
            System.out.println("phone error");
            noErrorsFound = false;
            PhoneError.setVisible(true);
            PhoneEntry.setText("");
        }
        else PhoneError.setVisible(false);

        String ID = IDEntry.getText();
        if(isInt(ID) == false || ID.length()!= 8 ){
            System.out.println("dni error");
            noErrorsFound = false;
            IDError.setVisible(true);
            IDEntry.setText("");
        }
        else IDError.setVisible(false);

        if(noErrorsFound == true){
            client = new Client(name);
            client.setID(ID);
            client.setMobile(phone);
        }
//Validando inputs - horarios
        String startHour =Integer.toString(Integer.parseInt(startH.getValue()));
        String endHour = Integer.toString(Integer.parseInt(endH.getValue()));
        String startmin = Integer.toString(Integer.parseInt(startMin.getValue()));
        String endmin = Integer.toString(Integer.parseInt(endMin.getValue()));

        int startHours = Integer.parseInt(startHour);
        int endHours = Integer.parseInt(endHour);
        int startMins = Integer.parseInt(startmin);
        int endMins = Integer.parseInt(endmin);
        Schedule hours = null ;


        if(validStartHour(startHours) == false || validEndHour(endHours) == false){
            System.out.println("error en horarios con numeros");
            noErrorsFound = false;
            hourError.setVisible(true);
            setDefaultHoursAndMinutes();
        }
        else hourError.setVisible(false);

        if(startBeforeEnd(startHours,endHours) == false) {
            System.out.println("error en principio y fin");
            noErrorsFound = false;
            hourError.setVisible(true);
            setDefaultHoursAndMinutes();
        }
        else hourError.setVisible(false);

        if(validMin(startMins) == false || validMin(endMins) == false){
            System.out.println("error en min");
            noErrorsFound = false;
            minutesError.setVisible(true);
            setDefaultHoursAndMinutes();
        }
        else minutesError.setVisible(false);

        if(noErrorsFound == true) {
            hours = new Schedule(startHours, startMins, endHours, endMins);
        }

        if(noErrorsFound == true) {
            Manager.setOffer(offerInst, hours, client);
        }

        if(noErrorsFound == true) {
            Stage stage = (Stage) TotalPrice.getScene().getWindow();
            stage.close();
        }


        if(noErrorsFound == false)
            System.out.println("error");

    }

    private void setDefaultHoursAndMinutes() {
        startH.setValue(startH.getItems().get(0));
        endH.setValue(endH.getItems().get(0));
        startMin.setValue(startMin.getItems().get(0));
        endMin.setValue(endMin.getItems().get(0));
    }

    @FXML
    private void cancelOffer(ActionEvent event) {
        Stage stage = (Stage) TotalPrice.getScene().getWindow();
        stage.close();
    }

    private boolean isValidName(String name){
        if(name.length() == 0)
            return false;
        String nonLetters = "1234567890,.-_´¨^{}[]++~*;:/|!$#%&()=?¡¿'\\";
        char letter ;
        for(int i = 0; i < name.length(); i++){
            letter = name.charAt(i);
            if(nonLetters.indexOf(letter) != -1) //si la letra actual del nombre aparece en "nonLetters", quiere decir que no es valido
                return false;
        }
        return true;
    }

    private boolean isInt(String number){
        if(number.length() == 0)
            return false;
        String digits = "1234567890";
        char digit;
        for(int i = 0; i < number.length(); i++){
            digit = number.charAt(i);
            if(digits.indexOf(digit) == -1) //si el digito del numero no aparece en "digits", no es valido
            return false;
        }
        return true;
    }

    private boolean validStartHour(int hour){
        return hour >= 0 && hour <= 23;
    }

    private boolean validEndHour(int hour){return hour >= 1 && hour <= 24;}

    private boolean startBeforeEnd(int hour1, int hour2){
        return hour1 < hour2;
    }

    private boolean validMin(int min){
        return  min == 30 || min == 0;
    }


}
