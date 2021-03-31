package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import DBConnection.DataSource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ArrangeSetPageController implements Initializable {

	@FXML
	private AnchorPane anchorArrangeSetPage;

	@FXML
	private JFXButton buttonGoBack;

	@FXML
	private JFXTextField tFFirstRow;

	@FXML
	private JFXTextField tFSecondRow;

	@FXML
	private JFXButton buttonAdd;

	@FXML
	private JFXComboBox<String> comboWords;

	@FXML
	private JFXButton buttonDeleteSelected, buttonDeleteSet;

	@FXML
	private Label labelSetName;

	@FXML
	private JFXCheckBox chechForDelete;

	@FXML
	void clickButtonAdd(ActionEvent event) {
		if (!(tFFirstRow.getText().equals("") || tFSecondRow.getText().equals(""))) {
			DataSource.getInstance().addWordsToSet(labelSetName.getText().split(":")[1].trim(), tFFirstRow.getText(),
					tFSecondRow.getText());
			clearTF();
		} else {
			MainPageController.giveInformationAlert("You did not answer the fields!", "Try to enter all fields.");
			clearTF();
		}
	}

	@FXML
	void clickButtonDeleteSelected(ActionEvent event) {
		if (!comboWords.getSelectionModel().isEmpty()) {
			DataSource.getInstance()
					.delWordsFromSet(comboWords.getSelectionModel().getSelectedItem().toString().split("-")[0].trim());
			fillComboBox();
		} else {
			MainPageController.giveInformationAlert("The set has not a value!", "You can not delete anything.");
		}
	}

	@FXML
	void clickButtonDeleteSet(ActionEvent event) {
		if (chechForDelete.isSelected()) {
			DataSource.getInstance().delSet(labelSetName.getText().split(":")[1].trim());
			MainPageController.giveInformationAlert("The set succesfully deleted.", "");
			chechForDelete.setSelected(false);
		} else {
			MainPageController.giveInformationAlert("If you want to delete this set, confirm on the left text!",
					"And try again.");
		}

	}

	@FXML
	void clickButtonGoBack(ActionEvent event) {
		MainPageController.getInstance().createPage(anchorArrangeSetPage, "/FXML/SetPage.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fillComboBox();
		labelSetName.setText("Set Name: " + SetPageController.getInstance().comboBoxSelectedSetValue());
	}

	private void fillComboBox() {
		comboWords.setItems(FXCollections.observableArrayList(DataSource.getInstance()
				.getComboBoxWordValues(SetPageController.getInstance().comboBoxSelectedSetValue())));
		comboWords.getSelectionModel().select(0);
	}

	private void clearTF() {
		tFFirstRow.setText("");
		tFSecondRow.setText("");
	}

}
