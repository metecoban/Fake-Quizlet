package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import Model.Word;
import DBConnection.DataSource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class SetPageController implements Initializable {

	@FXML
	private AnchorPane anchorSetPage;

	@FXML
	private JFXComboBox<String> comboSetName;

	@FXML
	private TableColumn<Word, String> firstWordColumn;

	@FXML
	private TableColumn<Word, String> secondWordColumn;

	@FXML
	private TableView<Word> table;

	@FXML
	private JFXButton buttonNewSet;

	@FXML
	private JFXButton buttonStudy;

	@FXML
	private JFXButton buttonArrangeSet;

	private static SetPageController instance;

	public SetPageController() {
		instance = this;
	}

	public static SetPageController getInstance() {
		return instance;
	}

	public String comboBoxSelectedSetValue() {
		return comboSetName.getSelectionModel().getSelectedItem();
	}

	@FXML
	void clickButtonArrangeSet(ActionEvent event) {
		if (!comboSetName.getSelectionModel().isEmpty()) {
			MainPageController.getInstance().createPage(anchorSetPage, "/FXML/ArrangeSetPage.fxml");
		} else {
			MainPageController.giveInformationAlert("You need at least 1 set for arranging!",
					"Try again after change.");
		}

	}

	@FXML
	void clickButtonNewSet(ActionEvent event) {
		MainPageController.getInstance().createPage(anchorSetPage, "/FXML/CreateNewSetPage.fxml");
	}

	@FXML
	void clickButtonStudy(ActionEvent event) {
		if (table.getItems().size() >= 3) {
			MainPageController.getInstance().createPage(anchorSetPage, "/FXML/StudyPage.fxml");
		} else {
			MainPageController.giveInformationAlert("You need at least 3 word for studying!",
					"Try again after change.");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// To fill combo values
		comboSetName.setItems(FXCollections.observableArrayList(DataSource.getInstance().getComboBoxNameValues()));
		comboSetName.getSelectionModel().select(0);

		// To fill table values
		firstWordColumn.setCellValueFactory(new PropertyValueFactory<Word, String>("word1"));
		secondWordColumn.setCellValueFactory(new PropertyValueFactory<Word, String>("word2"));
		table.setItems(DataSource.getInstance().getTableValues(comboSetName.getSelectionModel().getSelectedItem()));
		firstWordColumn.setResizable(false);
		secondWordColumn.setResizable(false);
	}

	@FXML
	void clickComboSetName(ActionEvent event) {
		table.setItems(DataSource.getInstance().getTableValues(comboSetName.getSelectionModel().getSelectedItem()));
		firstWordColumn.setText(DataSource.getInstance()
				.getColumnNameValues(comboSetName.getSelectionModel().getSelectedItem()).get(0));
		secondWordColumn.setText(DataSource.getInstance()
				.getColumnNameValues(comboSetName.getSelectionModel().getSelectedItem()).get(1));
	}

}
