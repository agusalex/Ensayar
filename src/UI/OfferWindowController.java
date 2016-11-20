package UI;

import Data.Client;
import Data.Offer;
import Data.Schedule;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("CanBeFinal")
class OfferWindowController implements Initializable {

    @FXML
    private ChoiceBox<String> adittionalValue;
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
    private TextField IDEntry;
    @FXML
    private CheckBox Bateria;
    @FXML
    private TextField PhoneEntry;
    @FXML
    private CheckBox Guitarra;
    @FXML
    private Label TotalPrice;
    @FXML
    private CheckBox Teclado;
    @FXML
    private CheckBox Bajo;
    @FXML
    private TextField nameEntry;
    @FXML
    private Label nameError;
    @FXML
    private Label minutesError;
    @FXML
    private Label PhoneError;
    @FXML
    private Label hourError;
    @FXML
    private Label IDError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addHoursAndMinutes();
        addAdittionalValues();
        setDefaultChoiceBoxesParams();

        Offer temp=Manager.getTemporaryOffer();
        TotalPrice.setText(adittionalValue.getValue());

        if(Manager.getTemporaryOffer()!=null){
            Manager.getTemporaryOffer().setPrice(0);
            nameEntry.setText(temp.getClient().getName());
            IDEntry.setText(temp.getClient().getID());
            PhoneEntry.setText(temp.getClient().getMobile());
            for(Offer.Instruments inst : temp.getInstruments()){
                if(inst==Offer.Instruments.BATERIA)
                    Bateria.setSelected(true);
                if(inst==Offer.Instruments.GUITARRA)
                    Guitarra.setSelected(true);
                if(inst==Offer.Instruments.BAJO)
                    Bajo.setSelected(true);
                if(inst==Offer.Instruments.TECLADO)
                    Teclado.setSelected(true);
                if(inst==Offer.Instruments.MICROFONO)
                    Microfono.setSelected(true);

            }

            startH.setValue(Integer.toString(temp.getSchedule().getStarth()));
            endH.setValue(Integer.toString(temp.getSchedule().getEndH()));
            startMin.setValue(Integer.toString(temp.getSchedule().getStartMins()));
            endMin.setValue(Integer.toString(temp.getSchedule().getEndMins()));


        }

    }


    private void addAdittionalValues(){
        for (int x = 0; x < 500; x+=50) {
            adittionalValue.getItems().add(String.valueOf(x));
        }
    }

    private void addHoursAndMinutes(){
        for (int x = 0; x < 9; x++) {
            startH.getItems().add("0" + x);
            endH.getItems().add("0" + x);
        }
        for (int x = 10; x < 24; x++) {
            startH.getItems().add(Integer.toString(x));
            endH.getItems().add(Integer.toString(x));
        }
        startMin.getItems().add("00");
        startMin.getItems().add("30");
        endMin.getItems().add("00");
        endMin.getItems().add("30");

    }

    private void setDefaultChoiceBoxParams(ChoiceBox<String> cho) {
        cho.setValue(cho.getItems().get(0));
        cho.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> priceUpdate());
    }

    @SuppressWarnings("unchecked")
    private void setDefaultChoiceBoxesParams() {
        ChoiceBox[] choices=new ChoiceBox[]{startH,endH,startMin,endMin,adittionalValue};

        for(ChoiceBox cho :choices ) {
            setDefaultChoiceBoxParams((ChoiceBox<String>)cho);
        }

    }




    @FXML
    void priceUpdate(){
        int totalprice=0;


        int startHour = Integer.parseInt(startH.getValue());
        int endHour = Integer.parseInt(endH.getValue());
        int startmin = Integer.parseInt(startMin.getValue());
        int endmin = Integer.parseInt(endMin.getValue());


       totalprice+= Offer.getPriceperDuration(startHour,endHour,startmin,endmin);



        if (Bateria.isSelected())
            totalprice+=Offer.Instruments.instrumentValue(Offer.Instruments.BATERIA);
        if (Bajo.isSelected())
            totalprice+=Offer.Instruments.instrumentValue(Offer.Instruments.BAJO);
        if (Guitarra.isSelected())
            totalprice+=Offer.Instruments.instrumentValue(Offer.Instruments.GUITARRA);
        if (Teclado.isSelected())
            totalprice+=Offer.Instruments.instrumentValue(Offer.Instruments.TECLADO);
        if (Microfono.isSelected())
            totalprice+=Offer.Instruments.instrumentValue(Offer.Instruments.MICROFONO);
        int valoradd=Integer.parseInt(adittionalValue.getValue());
        totalprice+=valoradd;









        TotalPrice.setText(Integer.toString(totalprice));
}

    @FXML
    void createOffer() {

        boolean noErrorsFound = true;  //esto revisa si encontre alguno de los errores de arriba


        ArrayList<Offer.Instruments> offerInst = new ArrayList<>();
        if (Bateria.isSelected())
            offerInst.add(Offer.Instruments.BATERIA);
        if (Bajo.isSelected())
            offerInst.add(Offer.Instruments.BAJO);
        if (Guitarra.isSelected())
            offerInst.add(Offer.Instruments.GUITARRA);
        if (Teclado.isSelected())
            offerInst.add(Offer.Instruments.TECLADO);
        if (Microfono.isSelected())
            offerInst.add(Offer.Instruments.MICROFONO);


        //Validando inputs - Cliente
        Client client = null;
        String name = nameEntry.getText();
        if (!isValidName(name)) {
            System.out.println("error nombre");
            noErrorsFound = false;
            nameError.setVisible(true);
            nameEntry.setText("");
        } else nameError.setVisible(false);

        String phone = PhoneEntry.getText();
        if (!isTelephone(phone) ) {
            System.out.println("phone error");
            noErrorsFound = false;
            PhoneError.setVisible(true);
            PhoneEntry.setText("");
        } else PhoneError.setVisible(false);

        String ID = IDEntry.getText();
        if (!isDNI(ID) ) {
            System.out.println("dni error");
            noErrorsFound = false;
            IDError.setVisible(true);
            IDEntry.setText("");
        } else IDError.setVisible(false);

        if (noErrorsFound) {
            client = new Client(name);
            client.setID(ID);
            client.setMobile(phone);
        }
//Validando inputs - horarios
        String startHour = Integer.toString(Integer.parseInt(startH.getValue()));
        String endHour = Integer.toString(Integer.parseInt(endH.getValue()));
        String startmin = Integer.toString(Integer.parseInt(startMin.getValue()));
        String endmin = Integer.toString(Integer.parseInt(endMin.getValue()));

        int startHours = Integer.parseInt(startHour);
        int endHours = Integer.parseInt(endHour);
        int startMins = Integer.parseInt(startmin);
        int endMins = Integer.parseInt(endmin);
        Schedule hours;


        if (!validStartHour(startHours) || !validEndHour(endHours)) {
            System.out.println("error en horarios con numeros");
            noErrorsFound = false;
            hourError.setVisible(true);
            setDefaultChoiceBoxesParams();
        } else hourError.setVisible(false);

        if (!startBeforeEnd(startHours, endHours)) {
            System.out.println("error en principio y fin");
            noErrorsFound = false;
            hourError.setVisible(true);
            setDefaultChoiceBoxesParams();
        } else hourError.setVisible(false);

        if (validMin(startMins) || validMin(endMins)) {
            System.out.println("error en min");
            noErrorsFound = false;
            minutesError.setVisible(true);
            setDefaultChoiceBoxesParams();
        } else minutesError.setVisible(false);


        if (noErrorsFound) {
            hours = new Schedule(startHours, startMins, endHours, endMins);
            Offer temp=new Offer(offerInst, hours, client);

            temp.setPrice(temp.getPrice()+Offer.getPriceperDuration(startHours,endHours,startMins,endMins)+Integer.parseInt(adittionalValue.getValue()));
            Manager.setTempOffer(temp);
            Stage stage = (Stage) TotalPrice.getScene().getWindow();
            stage.close();
        }


        if (!noErrorsFound)
            System.out.println("error");

    }


    @FXML
    private void cancelOffer() {
        Stage stage = (Stage) TotalPrice.getScene().getWindow();
        stage.close();
    }


    private static boolean matchesRegex(Pattern regex, String string){
        Matcher matcher = regex.matcher(string);
        if(string.equals(""))
            return false;
        if(matcher.find()) {
            if (matcher.start() == 0 && matcher.end() == string.length()){
                return true;
        }

        }
        return false;

    }


    private static boolean isValidName(String name){
        Pattern pat=Pattern.compile("([A-Z][a-z]*[\\s])*([A-Z][a-z]*)");
        return matchesRegex(pat,name);
    }

    private static boolean isDNI(String number) {
        Pattern pat = Pattern.compile("(\\d{8})");
        return matchesRegex(pat, number);
    }

    private static boolean isTelephone(String number){
        Pattern pat = Pattern.compile("(\\d{8})");
        Pattern pat2 = Pattern.compile("(\\d{10})");

        return  matchesRegex(pat,number)||matchesRegex(pat2,number);
    }


    private boolean validStartHour(int hour) {
        return hour >= 0 && hour <= 23;
    }

    private boolean validEndHour(int hour) {
        return hour >= 1 && hour <= 24;
    }

    private boolean startBeforeEnd(int hour1, int hour2) {
        return hour1 < hour2;
    }

    private boolean validMin(int min) {
        return min != 30 && min != 0;
    }

//
}
