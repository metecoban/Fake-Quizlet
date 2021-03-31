package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import DBConnection.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class CreateNewSetPageController implements Initializable {

	@FXML
	private AnchorPane anchorSetPage;

	@FXML
	private JFXButton buttonGoBack;

	@FXML
	private JFXTextField tFSetName;

	@FXML
	private JFXTextField tFFirstColumnName;

	@FXML
	private JFXTextField tFSecondColumnName;

	@FXML
	private JFXButton buttonAddSet;

	@FXML
	void clickButtonAddSet(ActionEvent event) {
		if (!(tFSetName.getText().equals("") || tFFirstColumnName.getText().equals("")
				|| tFSecondColumnName.getText().equals(""))) {
			DataSource.getInstance().addSet(tFSetName.getText(), tFFirstColumnName.getText(),
					tFSecondColumnName.getText());
			clearTF();
		} else {
			MainPageController.giveInformationAlert("You did not answer the fields!", "Try to enter all fields.");
			clearTF();
		}

	}

	private void clearTF() {
		tFSetName.setText("");
		tFFirstColumnName.setText("");
		tFSecondColumnName.setText("");
	}

	@FXML
	void clickButtonGoBack(ActionEvent event) {
		MainPageController.getInstance().createPage(anchorSetPage, "/FXML/SetPage.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
