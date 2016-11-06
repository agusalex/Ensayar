package UI;

import Data.Offer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocio.Comparador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    public static OfferWindow offerWindow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showAssignedOffers();
        showRecentOffers();
    }

    @FXML
    void update(ActionEvent event) {
        Manager.loadDB();
        showAssignedOffers();
        showRecentOffers();
    }

    @FXML
    private void eraseAll(){
        eraseAllVisual();
        Manager.getRecentOffers().clear();
        Manager.getAssignedOffers().clear();
        Manager.resetDB();

    }

    @FXML
    private void eraseRecent(){
        eraseAllVisual();
        Manager.getRecentOffers().clear();
        Manager.resetDB();
        showRecentOffers();
        showAssignedOffers();

    }

    public void eraseAllVisual(){
        ObservableList<Node> Assigned = AnchorPaneAssigned.getChildren();
        ObservableList<Node> unAssigned = AnchorPaneRecent.getChildren();
        ArrayList<Node> bkp= new  ArrayList<Node>();

        for(Node n : AnchorPaneAssigned.getChildren())
            if (!(n instanceof Button)||n==offer0)
                bkp.add(n);


        Assigned.clear();
        for (Node n : bkp)
            Assigned.add(n);


        bkp= new  ArrayList<Node>();
        for(Node n : AnchorPaneRecent.getChildren())
            if (!(n instanceof Button)||n== recentOffer0)
                bkp.add(n);


        unAssigned.clear();
        for (Node n : bkp)
            unAssigned.add(n);
    }


    private void showAssignedOffers(){
        AnchorPane assigned = AnchorPaneAssigned;
        Button demo = offer0;
        ObservableList<Node> children = assigned.getChildren();
        double start =0;
        if(children.size()>1){
            start = children.get(children.size()-1).getLayoutY();}
        int x=0;
        for(Offer offer :Manager.getAssignedOffers()){
            Button n=new Button(offer.getClient()+""+offer.getSchedule());
            n.setLayoutY(start+(x*cantLines(n.getText())*19));//TODO 94 deberia ser dinamico segun tamaño bton
            n.prefWidthProperty().bind(demo.widthProperty()); //RE IMPORTANTE ESTE COMANDO
            assigned.getChildren().add(n);
            x++;
        }

    }
    private Node getRecentVisualOffer(int i){

        int size=AnchorPaneRecent.getChildren().size();
        if(i<0||i>size-3)
            throw new IndexOutOfBoundsException();


    return AnchorPaneRecent.getChildren().get(i+3);
    }

    private Node getAssignedVisualOffer(int i){
        int size=AnchorPaneAssigned.getChildren().size();
        if(i<0||i>size-3)
            throw new IndexOutOfBoundsException();

        return AnchorPaneAssigned.getChildren().get(i+3);
    }

    private void showRecentOffers(){

        AnchorPane assigned = AnchorPaneRecent;
        Button demo = recentOffer0;

        ObservableList<Node> children = assigned.getChildren();
        double start =0;
        if(children.size()>1){
          start = children.get(children.size()-1).getLayoutY();}
        int x = 0;



        for(Offer offer : Manager.getRecentOffers()){
            Button n = new Button(offer.getClient()+""+offer.getSchedule());
            n.setLayoutY(start+(x*cantLines(n.getText())*19));
            n.prefWidthProperty().bind(demo.widthProperty()); //RE IMPORTANTE ESTE COMANDO
            assigned.getChildren().add(n);
            x++;
        }

    }


    public int cantLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }

    @FXML
    void addOffer() throws IOException {
        Stage stage = new Stage();
        offerWindow = new OfferWindow();
        offerWindow.start(stage);

        //TODO esto es solo para imprimir la oferta solo hay que sacar la linea de impresion
        if(Manager.getOffer() != null) {
            System.out.println(Manager.getOffer());
            Manager.getRecentOffers().add(Manager.getOffer());
            showRecentOffers();
            Manager.resetDB();
        }
    }

    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) nameSurnameBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    void about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Sobre Ensayos");
        alert.setHeaderText(null);
        alert.setContentText("Por Maxi Lencina & Agus Alexander");

        alert.showAndWait();

    }

    @FXML
    void sortByDate(ActionEvent event) {
        Manager.Sort(Comparador.porHorario());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
    }

    @FXML
    void sortByPrice(ActionEvent event) {
        Manager.Sort(Comparador.porBeneficio());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
    }
    @FXML
    void sortByDuration(ActionEvent event) {
        Manager.Sort(Comparador.porPeso());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
    }
    @FXML
    void moveOffertoAssigned(ActionEvent event) {
        for (int i=0;i<AnchorPaneRecent.getChildren().size()-3;i++) {
            Node offerV=getRecentVisualOffer(i);
            if(offerV.isFocused()) {
               int index= AnchorPaneRecent.getChildren().indexOf(offerV)-3;
                Offer offer=Manager.getRecentOffers().get(index);
                offer.setAvailableTomorrow();
                Manager.getAssignedOffers().add(offer);
                Manager.getRecentOffers().remove(index);
                Manager.resetDB();
                eraseAllVisual();
                showAssignedOffers();
                showRecentOffers();

            }
        }
    }

    @FXML
    void deleteOffer(ActionEvent event) {
        for (int i=0;i<AnchorPaneRecent.getChildren().size()-3;i++) {
            Node offer=getRecentVisualOffer(i);
            if(offer.isFocused())
                deleteVisualOffer(offer);

        }
        for (int i=0;i<AnchorPaneAssigned.getChildren().size()-3;i++) {
            Node offer=getAssignedVisualOffer(i);
            if(offer.isFocused())
                deleteVisualOffer(offer);

        }
    }

    void deleteVisualOffer(Node offer){
        ObservableList<Node> Assigned = AnchorPaneAssigned.getChildren();
        ObservableList<Node> unAssigned = AnchorPaneRecent.getChildren();
        ArrayList<Node> bkp= new  ArrayList<Node>();
        boolean isRecentOffer=false;
        boolean isAssignedOffer=false;
        int indexDelete=0;
        int auxI=0;
        for(Node n : AnchorPaneAssigned.getChildren()) {

            if (!(n instanceof Button) || n == offer0 ) {
                bkp.add(n);
            }
            else if(n!=offer){
                auxI++;
            }
            else{
                indexDelete=auxI;
                isAssignedOffer=true;
            }

        }

        Assigned.clear();
        for (Node n : bkp) {
            Assigned.add(n);
        }

        bkp= new  ArrayList<Node>();




        auxI=0;
        for(Node n : AnchorPaneRecent.getChildren()) {

            if (!(n instanceof Button) || n == recentOffer0) {
                bkp.add(n);
            }
            else if(n!=offer){
                auxI++;
            }
            else{
                indexDelete=auxI;
                isRecentOffer=true;
            }

        }

        unAssigned.clear();
        for (Node n : bkp)
            unAssigned.add(n);


        if(isAssignedOffer){
            Manager.getAssignedOffers().remove(indexDelete);
            Manager.resetDB();
        }
        else if(isRecentOffer) {
            Manager.getRecentOffers().remove(indexDelete);
            Manager.resetDB();
        }

        showAssignedOffers();
        showRecentOffers();
    }




    @FXML
    private Button offer0;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Label idBox;

    @FXML
    private Label roomBox;

    @FXML
    private Label instrumetsBox;

    @FXML
    private Label priceBox;

    @FXML
    private Button recentOffer0;

    @FXML
    private AnchorPane AnchorPaneAssigned;
    @FXML
    private AnchorPane AnchorPaneRecent;

    @FXML
    private Label nameSurnameBox;

    @FXML
    private Color x21;

    @FXML
    private Font x11;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    private Color x4;


}
