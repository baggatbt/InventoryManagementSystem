package baggatta.c482bb.controller;

import baggatta.c482bb.model.Part;
import baggatta.c482bb.model.Inventory;
import baggatta.c482bb.model.Product;
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

/**
 * The logic controller for add products
 * @author Brandon Baggatta
 */
public class AddProductController implements Initializable {

    public Button addProductSaveBtn;
    public Button addProductCancelBtn;
    public Button addProductRemoveBtn;
    public TextField addProductSearchField;
    public TextField addProductIdField;
    public TextField addProductNameField;
    public TextField addProductInvField;
    public TextField addProductPriceField;
    public TextField addProductMaxField;
    public TextField addProductMinField;
    public Button addProductAddButton;
    public TableView addProductPartTableBottom;
    public TableView addProductPartTable;
    public TableColumn addProductPartIdColumn;
    public TableColumn addProductPartNameColumn;
    public TableColumn addProductInventoryColumn;
    public TableColumn addProductPriceColumn;
    public TableColumn addProductBottomTablePartIdColumn;
    public TableColumn addProductBottomTablePartNameColumn;
    public TableColumn addProductBottomTableInventoryColumn;
    public TableColumn addProductBottomTablePriceColumn;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Stage stage;

    Parent scene;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductPartTable.setItems(Inventory.getAllParts());
        addProductPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductBottomTablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductBottomTablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductBottomTableInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductBottomTablePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * When the cancel button is clicked the user is taken back to the main form.
     * @param actionEvent the cancel button click
     * @throws IOException exception
     */
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/baggatta/c482bb/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * When the save button is clicked a new product object is created and saved to the list of products.
     * The fields are also checked to ensure there are no errors in the data being added
     * The user is then taken back to the main screen.
     * @param actionEvent the save button click
     * @throws IOException exception
     */
    public void onSaveClick(ActionEvent actionEvent) throws IOException {

        try {
            boolean partAddErrorCheck = false;
            int id = 0;
            String name = addProductNameField.getText();
            double price = Double.parseDouble(addProductPriceField.getText());
            int stock = Integer.parseInt(addProductInvField.getText());
            int min = Integer.parseInt(addProductMinField.getText());
            int max = Integer.parseInt(addProductMaxField.getText());


            if (max <= min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("The minimum should be less than the Maximum");
                alert.showAndWait();
            } else if ((stock > max) | (stock < min)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("The Inventory must be between the Max and Min values.");
                alert.showAndWait();
            } else if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Name can't be empty.");
                alert.showAndWait();
            } else {
                Product newProduct = new Product(id, name, price, stock, min, max);
                newProduct.setId(Inventory.getNewProductID());
                Inventory.addProduct(newProduct);


                for (Part part : associatedParts) {
                    newProduct.addAssociatedPart(part);
                }


                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/baggatta/c482bb/view/mainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The form contains blank fields or incorrect values");
            alert.showAndWait();
        }

    }

    /**
     * When the user searches for a part it will filter out anything that isnt a match or a partial match
     * @param actionEvent the searchbar input
     * @throws IOException exception
     */
    public void onPartSearch(ActionEvent actionEvent) throws IOException{
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = addProductSearchField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        addProductPartTable.setItems(partsFound);

        if (partsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }
    }

    /**
     * When the form detects no input the table is repopulated with all the data.
     * @param keyEvent the searchbar being empty
     * @throws IOException exception
     */
    public void ifPartSearchIsEmpty(KeyEvent keyEvent) throws IOException {
        if (addProductSearchField.getText().isEmpty()) {
            addProductPartTable.setItems(Inventory.getAllParts());

        }

    }

    /**
     * When the add button is clicked a part is associated with the product object being created
     * If nothing is selected an error is thrown
     *
     * @param actionEvent the add button click
     * @throws IOException exception
     */
    public void onClickProductAdd(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) addProductPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was nothing selected to add");
            alert.showAndWait();
        } else {
            associatedParts.add(selectedPart);
            addProductPartTableBottom.setItems(associatedParts);

        }
    }

    /**
     * When the user clicks remove a part is unassociated with the product object.
     * It also checks to make sure a part is selected and asks for confirmation to remove
     * @param actionEvent remove button click
     * @throws IOException exception
     */
    public void onClickRemoveBtn(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) addProductPartTableBottom.getSelectionModel().getSelectedItem();

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
                addProductPartTableBottom.setItems(associatedParts);

            }
        }
    }
}
