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
    private Label invalidHourError;


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

    //Validando inputs
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
        if(isInt(phone) == false || phone.length() > 10 || phone.length() < 10){
            System.out.println("phone error");
            noErrorsFound = false;
            PhoneError.setVisible(true);
            PhoneEntry.setText("");
        }
        else PhoneError.setVisible(false);

        String ID = IDEntry.getText();
        if(isInt(ID) == false || ID.length() < 8 || ID.length() > 8  ){
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

        String startHour = StartEntry.getText();
        String endHour = EndEntry.getText();
        String startMin = MinEntry.getText();
        String endMin = MinEndEntry.getText();

        if(!isInt(startHour) || !isInt(endHour) || !isInt(startMin) || !isInt(endMin)) {
            System.out.println("error en horarios");
            noErrorsFound = false;
            invalidHourError.setVisible(true);
            StartEntry.setText("");
            EndEntry.setText("");
            MinEntry.setText("");
            MinEndEntry.setText("");
            hourError.setVisible(false);
        }
        else invalidHourError.setVisible(false);


        int startHours = 0;
        int endHours = 0;
        int startMins = 0;
        int endMins = 0;
        Schedule hours = null ;

        if(noErrorsFound == true) {
             startHours = Integer.parseInt(StartEntry.getText());
             endHours = Integer.parseInt(EndEntry.getText());
             startMins = Integer.parseInt(MinEntry.getText());
             endMins = Integer.parseInt(MinEndEntry.getText());

            if(validStartHour(startHours) == false || validEndHour(endHours) == false){
                System.out.println("error en horarios con numeros");
                noErrorsFound = false;
                invalidHourError.setVisible(true);
                StartEntry.setText("");
                EndEntry.setText("");
            }
            else invalidHourError.setVisible(false);

            if(startBeforeEnd(startHours,endHours) == false) {
                System.out.println("error en principio y fin");
                noErrorsFound = false;
                hourError.setVisible(true);
                StartEntry.setText("");
                EndEntry.setText("");
            }
            else hourError.setVisible(false);

            if(validMin(startMins) == false || validMin(endMins) == false){
                System.out.println("error en min");
                noErrorsFound = false;
                minutesError.setVisible(true);
                MinEntry.setText("");
                MinEndEntry.setText("");
            }
            else minutesError.setVisible(false);

            if(noErrorsFound == true)
                hours = new Schedule(startHours, startMins, endHours, endMins);
        }
        if(noErrorsFound == true)
            Manager.setOffer(offerInst,hours,client);

        if(noErrorsFound == false)
            System.out.println("error");

        else{
            Stage stage = (Stage) TotalPrice.getScene().getWindow();
            stage.close();
        }

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
