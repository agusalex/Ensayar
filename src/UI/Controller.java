package UI;

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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocio.Comparador;
import negocio.Solver;
import negocio.SolverExacto;
import negocio.SolverGoloso;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    public static OfferWindow offerWindow;

    public Calculation calculation;

    public Node selectedElement=null;

    public enum Calculation {
        PRICE, HOURS, BEST, PRICE_HOUR
    }

    private FileChooser fileChooser;
    private DirectoryChooser dirChooser;
    private File selectedDirectory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showAssignedOffers();
        showRecentOffers();

    }



    private void refreshVisual() {
        eraseAllVisual();
        showAssignedOffers();
        showRecentOffers();
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

    private void eraseAllVisual(){
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

    @FXML
    void update() {
        Manager.resetDB();
        Manager.loadDB();
        refreshVisual();
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
        Manager.getRecentOffers().clear();
        Manager.resetDB();
        Manager.loadDB();
        refreshVisual();
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


    private Offer getLogicOffer(Node node) {
        if(node instanceof Button) {
            Parent Anchor = node.getParent();
            int index = Anchor.getChildrenUnmodifiable().indexOf(node);

            if (index <= 2)
                throw new IndexOutOfBoundsException("El elemento visual no corresponde a una oferta logica=" + index);

            if (Anchor == AnchorPaneAssigned) {
                if (index -3 >= Manager.getAssignedOffers().size())
                    throw new IndexOutOfBoundsException("El elemento visual no corresponde a una oferta logica=" + index);

                return Manager.getAssignedOffers().get(index - 3);
            }

            if (Anchor == AnchorPaneRecent) {
                if (index-3 >= Manager.getRecentOffers().size())
                    throw new IndexOutOfBoundsException("El elemento visual no corresponde a una oferta logica=" + index);

                return Manager.getRecentOffers().get(index - 3);
            }
        }

        throw new IllegalArgumentException("no es una oferta");

    }


    @FXML
    private void OfferInfo(ActionEvent event){
        Node offer=(Node)event.getSource();
        selectedElement=offer;
        Offer oferta= getLogicOffer(offer);
        showOfferInfo(oferta);
    }


    private void showOfferInfo(Offer offer) {//FIXME AGRGAR HORARIOS Y TELEFONO
        if (offer != null) {
            nameSurnameBox.setText(offer.getClient().getName());
            idBox.setText((offer.getClient().getID()));
            instrumetsBox.setText(offer.getInstruments().toString());
            priceBox.setText(Integer.toString(offer.getPrice()));
            roomBox.setText("1");
        } else {
            throw new NullPointerException("Seleccion es null");
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

        //esta oferta se obtiene en el metodo "createOffer" de la clase "OfferWindowController"
        Offer offerToAdd = Manager.getTemporaryOffer();
        if(offerToAdd != null) {
            System.out.println(offerToAdd);
            Manager.getRecentOffers().add(offerToAdd);
            eraseAllVisual();
            showRecentOffers();
            showAssignedOffers();
        }
        Manager.emptyTemporaryOffer();
        update();
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
        if(selectedElement instanceof Button && selectedElement.getParent()==AnchorPaneRecent){
            Offer oferta= getLogicOffer(selectedElement);
            Manager.getRecentOffers().remove(oferta);
            oferta.setAvailableTomorrow();//TODO LLamar a el calendario para que elija cuando va a estar disponible
            Manager.getAssignedOffers().add(oferta);
            Manager.resetDB();
            refreshVisual();
        }
    }


    @FXML
    void moveOffersToRecent() {
        for(Offer of : Manager.getAssignedOffers()){
            of.setNotAssigned();
            Manager.getRecentOffers().add(of);
        }
        Manager.getAssignedOffers().clear();
        Manager.resetDB();
        refreshVisual();
    }

    @FXML
    void deleteOffer(ActionEvent event) {
        if(selectedElement instanceof Button && selectedElement.isFocused()) {

            if(selectedElement.getParent()==AnchorPaneRecent){
                Manager.getRecentOffers().remove(getLogicOffer(selectedElement));
            }
            else if(selectedElement.getParent()==AnchorPaneAssigned){
                Manager.getAssignedOffers().remove(getLogicOffer(selectedElement));
            }
            deleteVisualOffer(selectedElement);
            Manager.resetDB();
        }
    }

   private void deleteVisualOffer(Node offer){
           if (offer.getParent() == AnchorPaneAssigned)
               AnchorPaneAssigned.getChildren().remove(offer);

           else if (offer.getParent() == AnchorPaneRecent)
               AnchorPaneRecent.getChildren().remove(offer);
           else {
               throw new IndexOutOfBoundsException("Offerta no encontrada elemento a eliminar no es un boton");
           }
       refreshVisual();

    }



    @FXML
    void bestOffers(ActionEvent event) {
        moveOffersToRecent();
       Solver bruteForce=new SolverExacto();
        Manager.calculateOffersBy(bruteForce);
        refreshVisual();
    }


    @FXML
    void price_Hour(ActionEvent event) {
        moveOffersToRecent();
        Solver cociente=new SolverGoloso(SolverGoloso.Criterios.COCIENTE);
        Manager.calculateOffersBy(cociente);
        refreshVisual();
    }

    @FXML
    void highestPrice(ActionEvent event) {
        moveOffersToRecent();
        Solver precio=new SolverGoloso(SolverGoloso.Criterios.PRECIO);
        Manager.calculateOffersBy(precio);
        refreshVisual();
    }

    @FXML
    void moreHours(ActionEvent event) {
        moveOffersToRecent();
        Solver cargaHoraria=new SolverGoloso(SolverGoloso.Criterios.HORARIO);
        Manager.calculateOffersBy(cargaHoraria);
        refreshVisual();
    }

    //ventena que indica al usuario donde quiere guardar las ofertas , despues elije si guardarlas ahi o en otro lugar
    private void setUpFileChooser(){
        Stage stage = (Stage) nameSurnameBox.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON","*.json"));
        dirChooser = new DirectoryChooser();
        selectedDirectory = new File("/");//dirChooser.showDialog(stage);
        fileChooser.setInitialDirectory( selectedDirectory);
    }

    @FXML
    void importOffers() {
        setUpFileChooser();
        Stage stage = (Stage) nameSurnameBox.getScene().getWindow();

        fileChooser.setTitle("Open Resource File");
        File offerFile = fileChooser.showOpenDialog(stage);
        if(offerFile == null) {
            return;
        }

        String path = offerFile.getAbsolutePath();
        Manager.loadDB(path);
        refreshVisual();
    }


    @FXML
    void exportOffers() {
        setUpFileChooser();
        Stage stage = (Stage) nameSurnameBox.getScene().getWindow();
        fileChooser.setTitle("Export Offers");
        File offerFile = fileChooser.showSaveDialog(stage);
        if(offerFile == null) {
            return;
        }
        String path = offerFile.getAbsolutePath();
        Manager.saveDB(path);
        infoSavedFile(new File(path));
}

    void infoSavedFile(File file) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        if(file.exists()){
        alert.setTitle("Guardado con exito");
        alert.setHeaderText(null);
        alert.setContentText("Se ha guardado el archivo con exito");}
        else{
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("No se ha guardado el archivo");}
        alert.showAndWait();

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
