package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MainPageController implements Initializable {

	private static Alert alert = new Alert(Alert.AlertType.INFORMATION);

	@FXML
	private AnchorPane changeable;

	private static MainPageController instance;

	public MainPageController() {
		instance = this;
	}

	public static MainPageController getInstance() {
		return instance;
	}

	public static void giveInformationAlert(String infoHeaderText, String infoContentText) {
		alert.setTitle("Alert");
		alert.setHeaderText(infoHeaderText);
		alert.setContentText(infoContentText);
		alert.showAndWait();
	}

	private void setNode(Node node) {
		changeable.getChildren().clear();
		changeable.getChildren().add((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();
	}

	public void createPage(AnchorPane pane, String location) {
		try {
			pane = FXMLLoader.load(getClass().getResource(location));
			setNode(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createPage(changeable, "/FXML/SetPage.fxml");
	}

}
