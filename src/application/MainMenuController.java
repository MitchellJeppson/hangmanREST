package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuController {

	@FXML
	void newGameButtonClicked(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("mainGame.fxml"));
			Scene scene = new Scene(root);
			scene.getRoot().requestFocus();
			stage.setScene(scene);
			
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void highscoresButtonClicked(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("HighscoresPage.fxml"));
			Scene scene = new Scene(root);
			scene.getRoot().requestFocus();
			stage.setScene(scene);
			
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@FXML
	void exitButtonClicked(ActionEvent event) {
		System.exit(0);
	}

}
