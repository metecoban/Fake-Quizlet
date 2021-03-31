package Controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import DBConnection.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class StudyPageController implements Initializable {

	@FXML
	private AnchorPane anchorStudyPage;

	@FXML
	private JFXButton buttonGoBack;

	@FXML
	private JFXTextField tFAnswer;

	@FXML
	private JFXButton buttonChangeColumn;

	@FXML
	private JFXButton buttonAnswer1, buttonOK;

	@FXML
	private JFXButton buttonAnswer2;

	@FXML
	private JFXButton buttonAnswer3;

	@FXML
	private JFXButton buttonShowAnswer;

	@FXML
	private Label labelQuestion1, labelTruth2, labelTruth1;

	@FXML
	private Label labelQuestion;

	private HashMap<String, String> hashList, answerHashList;

	@FXML
	void clickButtonChangeColumn(ActionEvent event) {
		var keys = hashList.keySet();
		var values = hashList.values();
		HashMap<String, String> tempList = new HashMap<>();
		for (int i = 0; i < keys.size(); i++) {
			tempList.put(values.toArray()[i].toString(), keys.toArray()[i].toString());
		}
		hashList.clear();
		hashList.putAll(tempList);
		answerHashList.clear();
		answerHashList.putAll(hashList);
		fillWithQuestion();
	}

	@FXML
	void clickButtonShowAnswer(ActionEvent event) {
		tFAnswer.setText(hashList.get(labelQuestion.getText()));
	}

	private void labelTruthForText(boolean i) {
		if (i) {
			labelTruth1.setText("True");
			labelTruth1.setStyle("-fx-background-color: #7DCEA0");
		} else {
			labelTruth1.setText("False");
			labelTruth1.setStyle("-fx-background-color: #EC7063");
		}
	}

	private void labelTruthForButton(boolean i) {
		if (i) {
			labelTruth2.setText("True");
			labelTruth2.setStyle("-fx-background-color: #7DCEA0");
		} else {
			labelTruth2.setText("False");
			labelTruth2.setStyle("-fx-background-color: #EC7063");
		}
	}

	@FXML
	void clickButtonOK(ActionEvent event) {
		if (tFAnswer.getText().equals(hashList.get(labelQuestion.getText()))) {
			labelTruthForText(true);
			tFAnswer.setText("");
			hashList.remove(labelQuestion1.getText());
			fillWithQuestion();
		} else {
			tFAnswer.setText("");
			labelTruthForText(false);
		}
	}

	@FXML
	void clickTFAnswer(ActionEvent event) {
		if (tFAnswer.getText().equals(hashList.get(labelQuestion.getText()))) {
			labelTruthForText(true);
			tFAnswer.setText("");
			hashList.remove(labelQuestion1.getText());
			fillWithQuestion();
		} else {
			tFAnswer.setText("");
			labelTruthForText(false);
		}
	}

	@FXML
	void clickButtonGoBack(ActionEvent event) {
		MainPageController.getInstance().createPage(anchorStudyPage, "/FXML/SetPage.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hashList = new HashMap<>();
		answerHashList = new HashMap<>();
		resetQuestions();
		answerHashList.putAll(hashList);
		fillWithQuestion();
	}

	private void fillWithQuestion() {

		if (hashList.size() != 0) {
			Random generator = new Random();
			Object[] keys = hashList.keySet().toArray();
			String randomValue = hashList.keySet().toArray()[generator.nextInt(keys.length)].toString();
			labelQuestion.setText(randomValue);
			labelQuestion1.setText(randomValue);
			fillAnswers(randomValue, answerHashList.size());
		} else {
			MainPageController.giveInformationAlert("You have solved all questions!", "It is resetting.");
			resetQuestions();
			fillWithQuestion();
		}

	}

	private void resetQuestions() {
		hashList = DataSource.getInstance()
				.getHashMapWordValues(SetPageController.getInstance().comboBoxSelectedSetValue());
		labelTruth1.setText("");
		labelTruth2.setText("");
		labelTruth1.setStyle("-fx-background-color: #ECF0F1");
		labelTruth2.setStyle("-fx-background-color: #ECF0F1");
	}

	@FXML
	void controlQuestion(ActionEvent event) {
		if (event.getSource().toString().split("'")[1].replace("'", "").trim()
				.equals(hashList.get(labelQuestion1.getText()))) {
			labelTruthForButton(true);
			hashList.remove(labelQuestion1.getText());
			fillWithQuestion();
		} else {
			labelTruthForButton(false);
		}
	}

	private void fillAnswers(String key, int len) {
		Random generator = new Random();
		String randomValue1, randomValue2;
		int randomValue = generator.nextInt(3);
		while (true) {
			randomValue1 = answerHashList.keySet().toArray()[generator.nextInt(len)].toString();
			randomValue2 = answerHashList.keySet().toArray()[generator.nextInt(len)].toString();
			if (!(key.equals(randomValue1) || key.equals(randomValue2) || randomValue1.equals(randomValue2))) {
				break;
			}
		}
		switch (randomValue) {
		case 0:
			buttonAnswer1.setText(hashList.get(key));
			buttonAnswer2.setText(answerHashList.get(randomValue1));
			buttonAnswer3.setText(answerHashList.get(randomValue2));
			break;
		case 1:
			buttonAnswer1.setText(answerHashList.get(randomValue1));
			buttonAnswer2.setText(hashList.get(key));
			buttonAnswer3.setText(answerHashList.get(randomValue2));
			break;
		case 2:
			buttonAnswer1.setText(answerHashList.get(randomValue1));
			buttonAnswer2.setText(answerHashList.get(randomValue2));
			buttonAnswer3.setText(hashList.get(key));
			break;
		}
	}

}
