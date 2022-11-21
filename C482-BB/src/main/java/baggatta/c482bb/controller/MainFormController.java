package baggatta.c482bb.controller;

import baggatta.c482bb.model.InHouse;
import baggatta.c482bb.model.Inventory;
import baggatta.c482bb.model.Outsourced;
import baggatta.c482bb.model.Part;
import baggatta.c482bb.model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;

import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

/**
 * The controller class for the main form
 * A future improvement would be separate the types of alerts into their own class for easier access and readability.
 * An actual feature improvement would be to make it so the searchbar automatically detects searches without needing to press enter
 * javadoc folder found at C482.zip\software1\javadoc
 * @author Brandon Baggatta
 */
public class MainFormController implements Initializable {


    @FXML
    private Label welcomeText;
    @FXML
    private Button addPartBtn;

    public static Part partInfoToPass;
    public static Product productInfoToPass;

    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryLevelColumn;
    public TableColumn partPriceCostColumn;
    public TableView mainFormPartTable;
    public TableView mainFormProductTable;
    public TableColumn productNameColumn;
    public TableColumn productInventoryLevelColumn;
    public TableColumn productPriceCostColumn;
    public TableColumn productIdColumn;
    public TextField partTableSearchBar;
    public TextField modifyPartIdTextField;
    public Button modifyProductBtn;
    public Button deleteProductBtn;
    public Button addProductBtn;
    public Button mainFormExitButton;
    public TextField productTableSearchBar;

    Stage stage;
    Parent scene;

    private static boolean firstTime = true;
/*
    /*
     * ADDS TESTING DATA, COMMENT OUT WHEN DONE

    private void addTestData() {
        if (!firstTime) {
            return;
        }
        firstTime = false;

        InHouse testPart = new InHouse(1, "testPart", 2.5, 3, 1, 5, 1);
        Inventory.addPart(testPart);

        InHouse testPart2 = new InHouse(1, "fakePart", 2.5, 3, 1, 5, 1);
        Inventory.addPart(testPart);

        Outsourced testPart6 = new Outsourced(1, "another test part", 3, 2, 1, 5, "Fake Company");
        Inventory.addPart(testPart2);

        Product product = new Product(1,"product",2.49, 4,1,6);
        Inventory.addProduct(product);

        Product product2 = new Product(1,"milk",2.12, 4,1,6);
        Inventory.addProduct(product2);

        product2.addAssociatedPart(testPart);

    }
*/
    @Override
    /**
     * Initializes and adds test data, comment out the data when done
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {


       // addTestData();

        mainFormPartTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainFormProductTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * gets the selected part to pass to other screens
     * @return the selected part
     */
    public static Part passPartToModify() {
        return partInfoToPass;
    }

    /**
     * gets the selected product to pass data to the other form controllers
     * @return the selected product
     */
    public static Product passProductToModify() {
        return productInfoToPass;
    }

    @FXML
    /**
     * When add part is clicked the user will be taken to the add part window
     * @param actionEvent the add part button click
     * @throws IOException exception
     */
    public void onClickAddPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = load(getClass().getResource("/baggatta/c482bb/view/addPartInHouseForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *   When modify is clicked the user will be taken to the modify part window
     *   the data from the selected part will also be passed to the modifyPart form
     */

    @FXML
    public void onClickModify(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) mainFormPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was nothing selected to modify");
            alert.showAndWait();
        } else {
            partInfoToPass = (Part) mainFormPartTable.getSelectionModel().getSelectedItem();
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = load(getClass().getResource("/baggatta/c482bb/view/modifyPartForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * When delete is clicked the item will be deleted, or showed an error if no item selected
     * @param event delete click
     * @throws IOException exception
     */
    public void onClickDelete(ActionEvent event) throws IOException {

        Part selectedPart = (Part) mainFormPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was nothing selected to delete");
            alert.showAndWait();
        }
        //Checks to see if the user wants to delete, if cancel is selected, nothing happens
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }

        }

    }

    /**
     * When the user searches for a part either with a partial string or not it will locate it if it exists.
     * @param actionEvent searching in the part table
     * @throws IOException exception
     */
    public void onSearch(ActionEvent actionEvent) throws IOException {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partTableSearchBar.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        mainFormPartTable.setItems(partsFound);

        if (partsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }


    }

    /**
     * Checks to see if the search bar is empty, if so it populates the table view
     * @param keyEvent search bar being empty
     * @throws IOException exception
     */
    public void ifSearchIsEmpty(KeyEvent keyEvent) throws IOException {
        if (partTableSearchBar.getText().isEmpty()) {
            mainFormPartTable.setItems(Inventory.getAllParts());

        }

    }

    /**
     * If add product button is clicked, user is taken to the add product form
     * @param actionEvent product button click
     * @throws IOException exception
     */
    public void addProductBtnClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = load(getClass().getResource("/baggatta/c482bb/view/addProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * If modify is clicked user is taken to modify form
     * the data for the selected part is also passed to the modifyProductForm
     * An alert is thrown if nothing is selected
     * @param actionEvent modify button click
     * @throws IOException  exception
     */
    public void onModifyButtonClicked(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = (Product) mainFormProductTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was nothing selected to modify");
            alert.showAndWait();
        } else {
            productInfoToPass = (Product) mainFormProductTable.getSelectionModel().getSelectedItem();
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = load(getClass().getResource("/baggatta/c482bb/view/modifyProductForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Deletes selected product when the delete button is clicked on the product table.
     * Displays an error if nothing is selected and a confirmation if something is selected
     * Also checks to prevent the user from deleting a product with an associated aprt
     * @param event delete button
     *
     */
    public void onClickDeleteProductBtn(ActionEvent event) throws IOException {

        Product selectedProduct = (Product) mainFormProductTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was nothing selected to delete");
            alert.showAndWait();
        }

        ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();

        if (associatedParts.size() >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There product selected has associated parts");
            alert.showAndWait();
        }

            //Checks to see if the user wants to delete, if cancel is selected, nothing happens
        else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Delete the selected product?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(selectedProduct);
                }


            }


    }

    /**
     * Allows user to search for a specific part, or find a partial match
     * @param actionEvent product search bar
     * @throws IOException exception
     */
    public void onProductSearch(ActionEvent actionEvent) throws IOException {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productFound = FXCollections.observableArrayList();
        String searchString = productTableSearchBar.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productFound.add(product);
            }
        }

        mainFormProductTable.setItems(productFound);

        if (productFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Product not found");
            alert.showAndWait();
        }
    }

    /**
     * If the search bar is empty, it will repopulate the product table
     * @param keyEvent a key being released
     * @throws IOException exception
     */
    public void ifProductSearchIsEmpty(KeyEvent keyEvent) throws IOException {
        if (productTableSearchBar.getText().isEmpty()) {
            mainFormProductTable.setItems(Inventory.getAllProducts());

        }


    }

    /**
     * closes the application after confirming the user wants to close it.
     * @param actionEvent the exit button click
     * @throws IOException exception
     */
    public void exitButtonClicked(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to exit?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.CANCEL) {
                actionEvent.consume();
            }else {Platform.exit();}
        });


    }


}




