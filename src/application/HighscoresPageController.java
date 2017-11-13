package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HighscoresPageController {

    @FXML private Label highscore9;
    @FXML private Label highscore8;
    @FXML private Label highscore10;
    @FXML private Label highscore5;
    @FXML private Label highscore4;
    @FXML private Label highscore7;
    @FXML private Label highscore6;
    @FXML private Label highscore1;
    @FXML private Label highscore3;
    @FXML private Label highscore2;
    
    @FXML void initialize(){
        Label[] labelArr = {highscore1, highscore2, highscore3, highscore4, highscore5, highscore6, highscore7, highscore8, highscore9, highscore10}; 

		try{
    	String url = "http://localhost:8081/hangman/all";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		JSONArray arr = new JSONArray();
		while ((inputLine = in.readLine()) != null) {
			arr = new JSONArray(inputLine);
		}
		in.close();

		//print result
		System.out.println(arr.length());
		System.out.println(labelArr.length);
		for (int i = 0; i < arr.length(); i++)
		{
			System.out.println(i);
			String name = arr.getJSONObject(i).getString("name");
			int score = arr.getJSONObject(i).getInt("score");
			String output = String.format("%-30s: %d", name, score);
		    labelArr[i].setText(output);
		    System.out.println(labelArr[i].getFont());
		    System.out.println(labelArr[i].getHeight());
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }
    
    @FXML void backButtonPressed(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
			Scene scene = new Scene(root);
			scene.getRoot().requestFocus();
			stage.setScene(scene);
			
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

}
