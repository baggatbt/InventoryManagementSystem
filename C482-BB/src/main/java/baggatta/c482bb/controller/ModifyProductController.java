package baggatta.c482bb.controller;

import baggatta.c482bb.model.Part;
import baggatta.c482bb.model.Inventory;
import baggatta.c482bb.model.MainForm;
import baggatta.c482bb.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
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

/**
 * The controller logic for the modify product form.
 * @author Brandon Baggatta
 */
public class ModifyProductController implements Initializable {

    public TableView modifyProductTopTable;
    public TableColumn modifyProductTopPartIdField;
    public TableColumn modifyProductTopPartNameField;
    public TableColumn modifyProductTopInventoryField;
    public TableColumn modifyProductTopPriceField;
    public TableView modifyProductBottomTable;
    public TableColumn modifyProductBottomPartIdField;
    public TableColumn modifyProductBottomPartNameField;
    public TableColumn modifyProductBottomInventoryField;
    public TableColumn modifyProductBottomPriceField;
    public Button modifyProductAddBtn;
    public Button modifyProductSaveBtn;
    public Button modifyProductCancelBtn;
    public Button modifyProductRemoveBtn;
    public TextField modifyProductSearchField;
    public TextField modifyProductIdField;
    public TextField modifyProductNameField;
    public TextField modifyProductInvField;
    public TextField modifyProductPriceField;
    public TextField modifyProductMaxField;
    public TextField modifyProductMinField;

    private Product selectedProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = MainFormController.passProductToModify();
        associatedParts = selectedProduct.getAllAssociatedParts();

        modifyProductIdField.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameField.setText(selectedProduct.getName());
        modifyProductInvField.setText((String.valueOf(selectedProduct.getStock())));
        modifyProductPriceField.setText((String.valueOf(selectedProduct.getPrice())));
        modifyProductMaxField.setText((String.valueOf(selectedProduct.getMax())));
        modifyProductMinField.setText((String.valueOf(selectedProduct.getMin())));

        modifyProductTopTable.setItems(Inventory.getAllParts());
        modifyProductTopPartIdField.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductTopPartNameField.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductTopInventoryField.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductTopPriceField.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductBottomTable.setItems(associatedParts);
        modifyProductBottomPartIdField.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductBottomPartNameField.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductBottomInventoryField.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductBottomPriceField.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * When cancel is clicked the user is taken back to the mainForm
     * @param actionEvent cancel button click
     * @throws IOException exception
     */
    public void onClickCancel(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/baggatta/c482bb/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * When save is clicked the field values are checked for errors and saved to the product object.
     * @param actionEvent the save button click
     * @throws IOException exception
     */
    public void onSaveClicked(ActionEvent actionEvent) throws IOException {

        try {

            int id = selectedProduct.getId();

            String name = modifyProductNameField.getText();
            Double price = Double.parseDouble(modifyProductPriceField.getText());
            int stock = Integer.parseInt(modifyProductInvField.getText());
            int min = Integer.parseInt(modifyProductMinField.getText());
            int max = Integer.parseInt(modifyProductMaxField.getText());

            if (max > min && (stock < max && stock > min)) {
                Product newProduct = new Product(id, name, price, stock, min, max);

                for (Part part : associatedParts) {
                    newProduct.addAssociatedPart(part);
                }
                Inventory.addProduct(newProduct);
                Inventory.deleteProduct(selectedProduct);

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/baggatta/c482bb/view/mainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Max must be greater than Min, and Inventory must be between those values");
                alert.showAndWait();
            }
        }catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The form contains blank fields or incorrect values");
            alert.showAndWait();
        }
    }

    /**
     * When add is clicked a part is associated with the selected product
     * @param actionEvent the add button click
     * @throws IOException exception
     */
    public void onClickAdd(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) modifyProductTopTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was nothing selected to add");
            alert.showAndWait();
        } else {
            associatedParts.add(selectedPart);
            modifyProductBottomTable.setItems(associatedParts);
        }
    }

    /**
     * When the user searches it filters out anything that isnt a match or partial match
     * @param actionEvent search bar input
     * @throws IOException exception
     */
    public void onPartSearch(ActionEvent actionEvent) throws IOException {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = modifyProductSearchField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        modifyProductTopTable.setItems(partsFound);

        if (partsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }

    }

    /**
     * Checks to see if the search bar is empty, if it is, it repopulates the table.
     * @param keyEvent search bar key release
     * @throws IOException exception
     */
    public void ifSearchIsEmpty(KeyEvent keyEvent) throws IOException {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = modifyProductSearchField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }

        }
        modifyProductTopTable.setItems(partsFound);
    }

    /**
     * When remove is clicked the associated part is unassociated with the product.
     * @param actionEvent remove button click
     * @throws IOException exception
     */
    public void onRemoveClicked(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) modifyProductBottomTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There is no part selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();


            if ((result.isPresent()) && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                modifyProductBottomTable.setItems(associatedParts);

            }
        }
    }
}

