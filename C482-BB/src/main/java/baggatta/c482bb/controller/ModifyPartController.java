package baggatta.c482bb.controller;



import baggatta.c482bb.model.MainForm;
import baggatta.c482bb.model.InHouse;
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
 * The controller class logic for the modify part form
 * @author Brandon Baggatta
 */
public class ModifyPartController implements Initializable {

    @FXML
    private ToggleGroup modifyPart;

    @FXML
    private Button modifyPartCancelBtn;

    @FXML
    private TextField modifyPartIDTextField;

    @FXML
    private RadioButton modifyPartInHouseRadioBtn;

    @FXML
    private TextField modifyPartInvTextField;

    @FXML
    private TextField modifyPartMachineIDTextField;

    @FXML
    private TextField modifyPartMaxTextField;

    @FXML
    private TextField modifyPartMinTextField;

    @FXML
    private TextField modifyPartNameTextField;

    @FXML
    private RadioButton modifyPartOutsourcedRadioBtn;

    @FXML
    private TextField modifyPartPriceCostTextField;

    @FXML
    private Button modifyPartSaveBtn;

    @FXML
    public Label machineID;

    private Part selectedPart;



    Stage stage;

    Parent scene;

    /**
     * Initializes the controller and gets data passed over from the MainFormController
     * A runtime error occurred when the ID being passed was null.
     * This happened because I did not include a method to pass the information to this controller.
     * The correction was in implementing passPartToModify() in the main form controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");

        selectedPart = MainFormController.passPartToModify();

        modifyPartIDTextField.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameTextField.setText(selectedPart.getName());
        modifyPartInvTextField.setText((String.valueOf(selectedPart.getStock())));
        modifyPartPriceCostTextField.setText((String.valueOf(selectedPart.getPrice())));
        modifyPartMaxTextField.setText((String.valueOf(selectedPart.getMax())));
        modifyPartMinTextField.setText((String.valueOf(selectedPart.getMin())));

        if (selectedPart instanceof InHouse){
            modifyPartInHouseRadioBtn.setSelected(true);
            modifyPartMachineIDTextField.setText((String.valueOf(((InHouse) selectedPart).getMachineId())));
        }

        if (selectedPart instanceof Outsourced){
            modifyPartOutsourcedRadioBtn.setSelected(true);
            modifyPartMachineIDTextField.setText((String.valueOf(((Outsourced) selectedPart).getCompanyName())));
            machineID.setText("Company Name");
            machineID.setLayoutX(40);
            machineID.setLayoutY(332);
        }

    }

    /**
     * Takes the user back to the main form when clicking the cancel button
     * @param actionEvent cancel button click
     * @throws IOException exception
     */
    public void onClickCancel(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/baggatta/c482bb/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * WHen outsourced is selected the forum will change the machine ID label to Company Name and make sure the position is okay.
     * @param event outsourced radio button selected
     */
    public void onOutsourcedClicked(ActionEvent event) {

        machineID.setText("Company Name");
        machineID.setLayoutX(40);
        machineID.setLayoutY(332);
    }

    /**
     * When In house is selected the form changes machine ID label to Machine ID, and makes sure the position is okay.
     * @param event in house radio button selected
     */
    public void onInHouseSelected(ActionEvent event) {
        machineID.setText("Machine ID");
        machineID.setLayoutX(61);
        machineID.setLayoutY(332);
    }

    /**
     * When save is clicked it sets the changes to the part, then takes the user back to the main form
     * It also checks for errors in filling out the form
     * On success, it takes the user back to the main form.
     * @param actionEvent event
     * @throws IOException exception
     */
    public void onSaveClicked(ActionEvent actionEvent) throws IOException {

        try {
            selectedPart.setName(modifyPartNameTextField.getText());
            selectedPart.setStock(Integer.parseInt(modifyPartInvTextField.getText()));
            selectedPart.setPrice(Double.parseDouble(modifyPartPriceCostTextField.getText()));
            selectedPart.setMax((Integer.parseInt(modifyPartMaxTextField.getText())));
            selectedPart.setMin((Integer.parseInt(modifyPartMinTextField.getText())));
            int stock = Integer.parseInt(modifyPartInvTextField.getText());
            int min = Integer.parseInt(modifyPartMinTextField.getText());
            int max = Integer.parseInt(modifyPartMaxTextField.getText());

            if (max > min && (stock < max && stock > min)) {
                if (selectedPart instanceof InHouse) {
                    modifyPartInHouseRadioBtn.setSelected(true);
                    ((InHouse) selectedPart).setMachineId((Integer.parseInt(modifyPartMachineIDTextField.getText())));
                }


                if (selectedPart instanceof Outsourced) {
                    modifyPartOutsourcedRadioBtn.setSelected(true);
                    ((Outsourced) selectedPart).setCompanyName((modifyPartMachineIDTextField.getText()));
                }
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/baggatta/c482bb/view/mainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }else {
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

}

