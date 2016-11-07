package UI;

import Data.DataBase;
import Data.Offer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocio.Comparador;
import negocio.Instancia;
import negocio.SolverGoloso;
import negocio.Subconjunto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static UI.Manager.getAssignedOffers;


public class Controller implements Initializable{

    public static OfferWindow offerWindow;

    public Calculation calculation;

    public enum Calculation {
        PRICE, HOURS, BEST, PRICE_HOUR
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showAssignedOffers();
        showRecentOffers();
    }

    @FXML
    void update(ActionEvent event) {
        eraseAllVisual();
        showAssignedOffers();
        showRecentOffers();
    }

    @FXML
    private void eraseAll(){
        eraseAllVisual();
        Manager.getRecentOffers().clear();
        getAssignedOffers().clear();
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
        double start =offer0.getLayoutY();


        int x=0;

        for(Offer offer : Manager.getAssignedOffers()){
            Button n=new Button(offer.getClient()+""+offer.getSchedule());
            n.setOnAction(demo.getOnAction());
            n.setLayoutY(start+(x*cantLines(n.getText())*19));
            n.prefWidthProperty().bind(demo.widthProperty()); //RE IMPORTANTE ESTE COMANDO
            assigned.getChildren().add(n);
            x++;
        }

    }
    private Node getRecentVisualOffer(int i){

        int size=AnchorPaneRecent.getChildren().size();
        if(i<0||i>size-3)
            throw new IndexOutOfBoundsException("no hay botonoes a que acceder");


    return AnchorPaneRecent.getChildren().get(i+3);
    }

    private Node getAssignedVisualOffer(int i){
        int size=AnchorPaneAssigned.getChildren().size();
        if(i<0||i>size-3)
            throw new IndexOutOfBoundsException("no hay botones a que acceder");

        return AnchorPaneAssigned.getChildren().get(i+3);
    }

    private void showRecentOffers(){

        AnchorPane assigned = AnchorPaneRecent;
        Button demo = recentOffer0;
        ObservableList<Node> children = assigned.getChildren();
        double start =recentOffer0.getLayoutY();
        int x=0;

        for(Offer offer : Manager.getRecentOffers()){
            Button n = new Button(offer.getClient()+""+offer.getSchedule());
            n.setLayoutY(start+(x*cantLines(n.getText())*19));
            n.setOnAction(demo.getOnAction());
            n.prefWidthProperty().bind(demo.widthProperty()); //RE IMPORTANTE ESTE COMANDO
            assigned.getChildren().add(n);
            x++;
        }

    }



    private void showOfferInfo(Offer offer){//FIXME AGRGAR HORARIOS Y TELEFONO
       if(offer!=null){

        nameSurnameBox.setText(offer.getClient().getName());
        idBox.setText((offer.getClient().getID()));
        instrumetsBox.setText(offer.getInstruments().toString());
        priceBox.setText(Integer.toString(offer.getPrice()));
        roomBox.setText("1");}
        else{
           throw new NullPointerException("Seleccion es null");
       }



    }


    private Offer getLogicOffer(Node node) {
        if(node instanceof Button) {
            Parent Anchor = node.getParent();
            int index = Anchor.getChildrenUnmodifiable().indexOf(node);

            if (index <= 2)
                throw new IndexOutOfBoundsException("El elemento visual no corresponde a una oferta logica=" + index);


            if (Anchor == AnchorPaneAssigned) {
                if (index -3> Manager.getAssignedOffers().size())
                    throw new IndexOutOfBoundsException("El elemento visual no corresponde a una oferta logica=" + index);

                return Manager.getAssignedOffers().get(index - 3);
            }
            if (Anchor == AnchorPaneRecent) {
                if (index-3 > Manager.getRecentOffers().size())
                    throw new IndexOutOfBoundsException("El elemento visual no corresponde a una oferta logica=" + index);

                return Manager.getRecentOffers().get(index - 3);

            }
        }

        throw new IllegalArgumentException("no es una oferta");


    }


    @FXML
    private void OfferInfo(ActionEvent event){
        System.out.println("Mostrando INFO...\n\n\n\n");
        Node offer=(Node)event.getSource();

        Offer oferta= getLogicOffer(offer);
        showOfferInfo(oferta);

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

        //esta oferta se obtiene en el metodo "createOffer" de la clase "OfferWindowController"
        Offer offerToAdd = Manager.getOffer();
        if(offerToAdd != null) {
            System.out.println(offerToAdd);
            Manager.getRecentOffers().add(offerToAdd);
            eraseAllVisual();
            showRecentOffers();
            showAssignedOffers();
        }
    }

    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) nameSurnameBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Sobre Ensayos");
        alert.setHeaderText(null);
        alert.setContentText("Por Maxi Lencina & Agus Alexander");
        alert.showAndWait();

    }

    @FXML
    void sortByDate() {
        Manager.Sort(Comparador.porHorario());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
        this.calculation = Calculation.BEST;
    }

    @FXML
    void sortByPrice() {
        Manager.Sort(Comparador.porBeneficio());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
        this.calculation = Calculation.PRICE;
    }

    @FXML
    void sortByPrice_Hour() {
        Manager.Sort(Comparador.porCociente());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
        this.calculation = Calculation.PRICE_HOUR;
    }

    @FXML
    void sortByDuration() {
        Manager.Sort(Comparador.porPeso());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
        this.calculation = Calculation.HOURS;
    }


    @FXML
    void moveOffertoAssigned() {
        for (int i=0;i<AnchorPaneRecent.getChildren().size()-3;i++) {// FIXME ESTO ESTA HORRIBLE MEJORAR MANEJO DE ARREGLOS
            Node offerV=getRecentVisualOffer(i);   // en este metodo busca el elemento en i+3;
            if(offerV.isFocused()) {
               int index= AnchorPaneRecent.getChildren().indexOf(offerV)-3;// FIXME ESTO ESTA HORRIBLE MEJORAR MANEJO DE ARREGLOS
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


    @FXML   //FIXME AL PASAR UNA OFERTA A RECIENTE NO SE QUEDA AHI AL CARGAR EL ARCHIVO AL CERRAR Y ABRIR
    void moveOfferToRecent() {
        for(Offer of : Manager.getAssignedOffers()){
            of.setNotAssigned();
            Manager.getRecentOffers().add(of);
        }
        Manager.getAssignedOffers().clear();
        Manager.resetDB();
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
    }

    @FXML
    void deleteOffer(ActionEvent event) {
        for (int i=0;i<AnchorPaneRecent.getChildren().size()-3;i++) {// FIXME ESTO ESTA HORRIBLE MEJORAR MANEJO DE ARREGLOS
            Node offer=getRecentVisualOffer(i);
            if(offer.isFocused())
                deleteVisualOffer(offer);

        }
        for (int i=0;i<AnchorPaneAssigned.getChildren().size()-3;i++) {// FIXME ESTO ESTA HORRIBLE MEJORAR MANEJO DE ARREGLOS
            Node offer=getAssignedVisualOffer(i);
            if(offer.isFocused())
                deleteVisualOffer(offer);

        }
    }

   private void deleteVisualOffer(Node offer){
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
    void bestOffers(ActionEvent event) {

    }

    @FXML
    void price_Hour(ActionEvent event) {
        Instancia instance = new Instancia();
        for(Offer of : DataBase.getDb().getOffers())
            instance.agregarObjeto(of);
        SolverGoloso solverGoloso = new SolverGoloso(SolverGoloso.Criterios.COCIENTE);
        Subconjunto solution = solverGoloso.resolver(instance);

        Manager.getAssignedOffers().clear();
        Manager.getRecentOffers().clear();

        for(Offer of : solution.getOffers()){
            of.setAvailableTomorrow();
            Manager.getAssignedOffers().add(of);
        }

        for(Offer of : DataBase.getDb().getOffers()){
            if(!solution.contiene(of))
                Manager.getRecentOffers().add(of);
        }
        Manager.resetDB();
        eraseAllVisual();
        showAssignedOffers();
        showRecentOffers();
    }

    @FXML
    void highestPrice(ActionEvent event) {
        Instancia instance = new Instancia();
        for(Offer of : DataBase.getDb().getOffers())
            instance.agregarObjeto(of);
        SolverGoloso solverGoloso = new SolverGoloso(SolverGoloso.Criterios.PRECIO);
        Subconjunto solution = solverGoloso.resolver(instance);

        Manager.getAssignedOffers().clear();
        Manager.getRecentOffers().clear();

        for(Offer of : solution.getOffers()){
            of.setAvailableTomorrow();
            Manager.getAssignedOffers().add(of);
        }

        for(Offer of : DataBase.getDb().getOffers()){
            if(!solution.contiene(of))
                Manager.getRecentOffers().add(of);
        }
        Manager.resetDB();
        eraseAllVisual();
        showAssignedOffers();
        showRecentOffers();
    }

    @FXML
    void moreHours(ActionEvent event) {
        Instancia instance = new Instancia();
        for(Offer of : DataBase.getDb().getOffers())
            instance.agregarObjeto(of);
        SolverGoloso solverGoloso = new SolverGoloso(SolverGoloso.Criterios.HORARIO);
        Subconjunto solution = solverGoloso.resolver(instance);

        Manager.getAssignedOffers().clear();
        Manager.getRecentOffers().clear();

        for(Offer of : solution.getOffers()){
            of.setAvailableTomorrow();
            Manager.getAssignedOffers().add(of);
        }

        for(Offer of : DataBase.getDb().getOffers()){
            if(!solution.contiene(of))
                Manager.getRecentOffers().add(of);
        }
        Manager.resetDB();
        eraseAllVisual();
        showAssignedOffers();
        showRecentOffers();
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
    private Button recentOffer0;

    @FXML
    private AnchorPane AnchorPaneAssigned;
    @FXML
    private AnchorPane AnchorPaneRecent;

    @FXML
    private Label nameSurnameBox;



}
