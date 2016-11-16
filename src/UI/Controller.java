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
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


@SuppressWarnings("unused")
public class Controller implements Initializable {

    private Node selectedElement = null;

    private FileChooser fileChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        showAssignedOffers();
        showRecentOffers();
        datePicker.setFocusTraversable(false);


    }


    private void refreshVisual() {
        eraseAllVisual();
        showAssignedOffers();
        showRecentOffers();
    }

    private void showRecentOffers() {

        AnchorPane assigned = AnchorPaneRecent;
        Button demo = recentOffer0;
        double start = recentOffer0.getLayoutY();
        int x = 0;

        for (Offer offer : Manager.getRecentOffers()) {
            Button n = new Button(offer.getClient() + "" + offer.getSchedule());
            n.setLayoutY(start + (x * cantLines(n.getText()) * 19));
            n.setOnAction(demo.getOnAction());
            n.prefWidthProperty().bind(demo.widthProperty()); //RE IMPORTANTE ESTE COMANDO
            assigned.getChildren().add(n);
            x++;
        }

    }

    private void showAssignedOffers() {
        AnchorPane assigned = AnchorPaneAssigned;
        Button demo = offer0;
        double start = offer0.getLayoutY();


        int x = 0;

        for (Offer offer : Manager.getCurrentAssignedOffers()) {
            Button n = new Button(offer.getClient() + "" + offer.getSchedule());
            n.setOnAction(demo.getOnAction());
            n.setLayoutY(start + (x * cantLines(n.getText()) * 19));
            n.prefWidthProperty().bind(demo.widthProperty()); //RE IMPORTANTE ESTE COMANDO
            assigned.getChildren().add(n);
            x++;
        }

    }

    private void eraseAllVisual() {
        ObservableList<Node> Assigned = AnchorPaneAssigned.getChildren();
        ObservableList<Node> unAssigned = AnchorPaneRecent.getChildren();
        ArrayList<Node> bkp = new ArrayList<>();

        for (Node n : AnchorPaneAssigned.getChildren())
            if (!(n instanceof Button) || n == offer0)
                bkp.add(n);


        Assigned.clear();
        Assigned.addAll(bkp);


        bkp = new ArrayList<>();
        for (Node n : AnchorPaneRecent.getChildren())
            if (!(n instanceof Button) || n == recentOffer0)
                bkp.add(n);


        unAssigned.clear();
        unAssigned.addAll(bkp);
    }

    @FXML
    void changeDate(){
        LocalDate selection = datePicker.getValue();
        Manager.setCurrentDate(selection);
        Manager.loadDB();
        refreshVisual();
    }
    @FXML
    void generateDemo(){
        for(Offer offer :Offer.generateRandomOffers())
            Manager.getRecentOffers().add(offer);

        Manager.resetDB();
        Manager.loadDB();
        refreshVisual();

    }


    @FXML
    void update() {
        Manager.resetDB();
        Manager.loadDB();
        refreshVisual();
    }

    @FXML
    private void eraseAll() {
        Manager.getRecentOffers().clear();
        Manager.getCurrentAssignedOffers().clear();
        Manager.getNotCurrentAssignedOffers().clear();
        Manager.resetDB();
        Manager.loadDB();
        refreshVisual();
    }

    @FXML
    private void eraseRecent() {
        Manager.getRecentOffers().clear();
        Manager.resetDB();
        Manager.loadDB();
        refreshVisual();
    }


    private Offer getLogicOffer(Node node) {
        if (node instanceof Button) {
            Parent Anchor = node.getParent();
            int index = Anchor.getChildrenUnmodifiable().indexOf(node);

            if (index <= 2)
                throw new IndexOutOfBoundsException("El elemento visual no corresponde a una oferta logica=" + index);

            if (Anchor == AnchorPaneAssigned) {
                if (index - 3 >= Manager.getCurrentAssignedOffers().size())
                    throw new IndexOutOfBoundsException("El elemento visual no corresponde a una oferta logica=" + index);

                return Manager.getCurrentAssignedOffers().get(index - 3);
            }

            if (Anchor == AnchorPaneRecent) {
                if (index - 3 >= Manager.getRecentOffers().size())
                    throw new IndexOutOfBoundsException("El elemento visual no corresponde a una oferta logica=" + index);

                return Manager.getRecentOffers().get(index - 3);
            }
        }

        throw new IllegalArgumentException("no es una oferta");

    }


    @FXML
    private void OfferInfo(ActionEvent event) {
        Node offer = (Node) event.getSource();
        selectedElement = offer;
        Offer oferta = getLogicOffer(offer);
        showOfferInfo(oferta);
    }


    private void showOfferInfo(Offer offer) {//FIXME AGRGAR HORARIOS Y TELEFONO
        if (offer != null) {
            nameSurnameBox.setText(offer.getClient().getName());
            idBox.setText((offer.getClient().getID()));
            instrumentsBox.setText(offer.getInstruments().toString());
            priceBox.setText(Integer.toString(offer.getPrice()));
            roomBox.setText("1");
        } else {
            throw new NullPointerException("Seleccion es null");
        }
    }


    private int cantLines(String str) {
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }

    @FXML
    void addOffer() throws IOException {
        Stage stage = new Stage();
        OfferWindow offerWindow = new OfferWindow();
        offerWindow.start(stage);

        //esta oferta se obtiene en el metodo "createOffer" de la clase "OfferWindowController"
        Offer offerToAdd = Manager.getTemporaryOffer();
        if (offerToAdd != null) {
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
    void closeProgram() {
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
    }

    @FXML
    void sortByPrice() {
        Manager.Sort(Comparador.porBeneficio());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
    }

    @FXML
    void sortByPrice_Hour() {
        Manager.Sort(Comparador.porCociente());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
    }

    @FXML
    void sortByDuration() {
        Manager.Sort(Comparador.porPeso());
        eraseAllVisual();
        showRecentOffers();
        showAssignedOffers();
    }


    @FXML
    void moveOffertoAssigned() {
        if (selectedElement instanceof Button && selectedElement.getParent() == AnchorPaneRecent) {
            Offer oferta = getLogicOffer(selectedElement);
            oferta.setAvailable(Manager.getCurrentDate());
            Manager.resetDB();
            Manager.loadDB();
            refreshVisual();
        }
    }


    @FXML
    void moveOffersToRecent() {
        for (Offer of : Manager.getCurrentAssignedOffers()) {
            of.setNotAssigned();
            Manager.getRecentOffers().add(of);
        }
        Manager.getCurrentAssignedOffers().clear();
        Manager.resetDB();
        refreshVisual();
    }

    @FXML
    void deleteOffer() {
        if (selectedElement instanceof Button && selectedElement.isFocused()) {

            if (selectedElement.getParent() == AnchorPaneRecent) {
                Manager.getRecentOffers().remove(getLogicOffer(selectedElement));
            } else if (selectedElement.getParent() == AnchorPaneAssigned) {
                Manager.getCurrentAssignedOffers().remove(getLogicOffer(selectedElement));
            }
            deleteVisualOffer(selectedElement);
            Manager.resetDB();
        }
    }

    private void deleteVisualOffer(Node offer) {
        if (offer.getParent() == AnchorPaneAssigned)
            AnchorPaneAssigned.getChildren().remove(offer);

        else if (offer.getParent() == AnchorPaneRecent)
            AnchorPaneRecent.getChildren().remove(offer);
        else {
            throw new IndexOutOfBoundsException("Oferta no encontrada elemento a eliminar no es un boton");
        }
        refreshVisual();

    }


    @FXML
    void bestOffers(ActionEvent event) {
        moveOffersToRecent();
        Solver bruteForce = new SolverExacto();
        Manager.calculateOffersBy(bruteForce);
        Manager.loadDB();
        refreshVisual();
    }


    @FXML
    void price_Hour(ActionEvent event) {
        moveOffersToRecent();
        Solver cociente = new SolverGoloso(SolverGoloso.Criterios.COCIENTE);
        Manager.calculateOffersBy(cociente);
        Manager.loadDB();
        refreshVisual();
    }

    @FXML
    void highestPrice(ActionEvent event) {
        moveOffersToRecent();
        Solver precio = new SolverGoloso(SolverGoloso.Criterios.PRECIO);
        Manager.calculateOffersBy(precio);
        Manager.loadDB();
        refreshVisual();
    }

    @FXML
    void moreHours(ActionEvent event) {
        moveOffersToRecent();
        Solver cargaHoraria = new SolverGoloso(SolverGoloso.Criterios.HORARIO);
        Manager.calculateOffersBy(cargaHoraria);
        Manager.loadDB();
        refreshVisual();
    }

    //ventena que indica al usuario donde quiere guardar las ofertas , despues elije si guardarlas ahi o en otro lugar
    @SuppressWarnings("UnusedAssignment")
    private void setUpFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        DirectoryChooser dirChooser = new DirectoryChooser();
        File selectedDirectory = new File("/");
        fileChooser.setInitialDirectory(selectedDirectory);
    }

    @FXML
    void importOffers() {
        setUpFileChooser();
        Stage stage = (Stage) nameSurnameBox.getScene().getWindow();

        fileChooser.setTitle("Open Resource File");
        File offerFile = fileChooser.showOpenDialog(stage);
        String path;
        if (offerFile != null)
            path = offerFile.getAbsolutePath();

        else {

            return;
        }
        boolean isSuccess = Manager.importDB(path);

        System.out.print(isSuccess);

        if (isSuccess)
            showMessage("Base de datos importada con exito", "Importar", "Base de Datos");
        else
            showMessage("Error al importar la Base de datos", "Importar: ERROR", "Error");

        refreshVisual();

    }


    @FXML
    void mergeOffers() {
        setUpFileChooser();
        Stage stage = (Stage) nameSurnameBox.getScene().getWindow();

        fileChooser.setTitle("Combinar");
        File offerFile = fileChooser.showOpenDialog(stage);
        if (offerFile == null) {
            showMessage("Error al combinar, Base datos vacia", "Error!:Combinar", "Base de Datos");

        }
        else {
            String path = offerFile.getAbsolutePath();
            boolean isSuccess = Manager.mergeDB(path);

            refreshVisual();
            if (isSuccess)
                showMessage("Base de datos actualizada con exito", "Combinar", "Base de Datos");
            else {
                showMessage("Error al actualizar la Base de datos", "Combinar: ERROR", "Error");
            }

        }
    }


    @FXML
    void exportOffers() {
        setUpFileChooser();
        Stage stage = (Stage) nameSurnameBox.getScene().getWindow();
        fileChooser.setTitle("Export Offers");
        File offerFile = fileChooser.showSaveDialog(stage);

        if (offerFile == null) {
            showMessage("Error al exportar, Base datos vacia", "Error!:Exportar", "Base de Datos");
            return;

        }

        @SuppressWarnings("ConstantConditions") String path = offerFile.getAbsolutePath();

        boolean isSuccess = Manager.exportDB(path);

        if (isSuccess)
            showMessage("Se ha guardado el archivo con exito", "Exportar", "Base de Datos");
        else
            showMessage("Error al exportar, el archivo no se pudo guardar", "Error!: Exportar", "Base de Datos");
    }


    private void showMessage(String msg, String title, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    @FXML
    private DatePicker datePicker;


    @FXML
    private Button offer0;


    @FXML
    private Label idBox;

    @FXML
    private Label roomBox;

    @FXML
    private Label instrumentsBox;

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
