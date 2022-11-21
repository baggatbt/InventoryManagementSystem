package baggatta.c482bb.controller;

import baggatta.c482bb.model.InHouse;
import baggatta.c482bb.model.Inventory;
import baggatta.c482bb.model.Outsourced;
import baggatta.c482bb.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the logic for the add part form
 * @author Brandon Baggatta
 */
public class AddPartController implements Initializable {

    public String machineIDLabel;
    public RadioButton addPartRadioBtnInHouse;
    public RadioButton addPartRadioBtnOutsourced;
    public ToggleGroup addPart;
    public Button addPartCancelButton;
    public Label machineID;
    public Button addPartSaveBtn;
    public TextField addPartIDField;
    @FXML
    public TextField addPartNameField;
    public TextField addPartInvField;
    public TextField addPartPriceCostField;
    public TextField addPartMaxField;
    public TextField addPartMinField;
    public TextField addPartMachineIDField;
    Stage stage;

    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
    }

    /**
     * When cancel is clicked on the add part screen the user is taken back to the main form.
     * @param actionEvent the cancel button click
     * @throws IOException exception
     */
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/baggatta/c482bb/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * When outsourced is selected, the form will change the machine ID label to Company Name and ensure the position is the same
     * @param event the outsourced radio button being selected
     */
    public void onOutsourcedClicked(ActionEvent event) {
        machineID.setText("Company Name");
        machineID.setLayoutX(48);
        machineID.setLayoutY(417);
    }

    /**
     * When In House is selected the form changes the machine ID label to Machien ID, and makes sure the position is the same.
     * @param event In house radio button being selected
     */
    public void onInHouseSelected(ActionEvent event) {
        machineID.setText("Machine ID");
        machineID.setLayoutX(68);
        machineID.setLayoutY(417.0);
    }

    /**
     * When the save button is clicked a new part is created, it also checks to ensure text fields are filled out correctly.
     * On successful add the user is then taken to the main form screen.
     * @param actionEvent the save button click
     * @throws IOException exception
     */
    public void onSaveClicked(ActionEvent actionEvent) throws IOException {

        try{

        int id = 0;
        String partName = addPartNameField.getText();
        String companyName;
        double price = Double.parseDouble(addPartPriceCostField.getText());
        int stock = Integer.parseInt(addPartInvField.getText());
        int min = Integer.parseInt((addPartMinField.getText()));
        int max = Integer.parseInt((addPartMaxField.getText()));
        int machineID;


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
            } else {


                if (addPartRadioBtnInHouse.isSelected()) {
                    machineID = Integer.parseInt(addPartMachineIDField.getText());
                    InHouse newInHousePart = new InHouse(id, partName, price, stock, min, max, machineID);
                    newInHousePart.setId(Inventory.getNewPartId());
                    Inventory.addPart(newInHousePart);
                }

                if (addPartRadioBtnOutsourced.isSelected()) {
                    companyName = addPartMachineIDField.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, partName, price, stock, min, max, companyName);
                    newOutsourcedPart.setId(Inventory.getNewPartId());
                    Inventory.addPart(newOutsourcedPart);

                }

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/baggatta/c482bb/view/mainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The form contains blank fields or incorrect values");
            alert.showAndWait();
        }

    }

}




