package UI;

import Data.Offer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    private void eraseAll(){

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
        double start = children.get(children.size()-1).getLayoutY();
        int x=0;

        for(Offer offer :Manager.getAssignedOffers()){
            Button n=new Button(offer.getClient()+"  "+offer.getSchedule());
            n.setLayoutY(start+x*94);//TODO 94 deberia ser dinamico segun tamaño bton
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

        double start = children.get(children.size()-1).getLayoutY();
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
            Manager.updateDB();
        }
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
