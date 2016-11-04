package UI;

import Data.Offer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

import static negocio.Main.generateRandomOffers;

public class Controller {
    @FXML
    private void eraseAll(){

        System.out.println("Borra Todo");

        ObservableList<Node> Assigned = AnchorPaneAssigned.getChildren();
        ObservableList<Node> unAssigned = AnchorPaneRecent.getChildren();
        ArrayList<Node> bkp= new  ArrayList<Node>();

        for(Node n : AnchorPaneAssigned.getChildren())
            if (!(n instanceof Button))
                bkp.add(n);


        Assigned.clear();
        for (Node n : bkp)
            Assigned.add(n);



        bkp= new  ArrayList<Node>();
        for(Node n : AnchorPaneRecent.getChildren())
            if (!(n instanceof Button))
                bkp.add(n);


        unAssigned.clear();
        for (Node n : bkp)
            unAssigned.add(n);





    }


    private void addRandomAssignedOffer(){

        AnchorPane assigned= AnchorPaneAssigned;


        ObservableList<Node> children = assigned.getChildren();



        double start=children.get(children.size()-1).getLayoutY();
        int x=0;
        for(Offer offer :generateRandomOffers(5)){
            Button n=new Button(offer.getClient()+"  "+offer.getSchedule());
            n.setLayoutY(start+x*94);
            n.prefWidthProperty().bind(offer0.widthProperty()); //RE IMPORTANTE ESTE COMANDO

            assigned.getChildren().add(n);
           x++;
        }



    }


    @FXML
    void addOffer() {
        addRandomAssignedOffer();
        System.out.println("Agrega Randoms");

    }

    @FXML
    private Button offer0;

    @FXML
    private Label idBox;

    @FXML
    private Label roomBox;

    @FXML
    private Label instrumetsBox;

    @FXML
    private Label priceBox;

    @FXML
    private Button recentOffer01;

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
