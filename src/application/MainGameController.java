package application;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;
import javax.swing.text.html.Option;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainGameController {

	@FXML private Label kLetter;
	@FXML private Label rLetter;    
	@FXML private Label wLetter;
	@FXML private Label iLetter;
	@FXML private Label mLetter;
	@FXML private Label dLetter;
	@FXML private Label hLetter;
	@FXML private Label uLetter;
	@FXML private Label aLetter;
	@FXML private Label cLetter;
	@FXML private Label nLetter;
	@FXML private Label pLetter;
	@FXML private Label sLetter;
	@FXML private Label fLetter;
	@FXML private Label xLetter;
	@FXML private Label eLetter;
	@FXML private Label qLetter;
	@FXML private ImageView hangingImageView;
	@FXML private Label lLetter;
	@FXML private Label vLetter;
	@FXML private Label zLetter;
	@FXML private Label bLetter;
	@FXML private Label tLetter;
	@FXML private Label guessingWordLabel;
	@FXML private Label yLetter;
	@FXML private Label gLetter;
	@FXML private Label jLetter;
	@FXML private Label oLetter;
	@FXML private BorderPane borderPane;
	private String[] lettersString = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	private List<String> oldGuesses = new ArrayList<>();
	private int countOfGuesses = 0;
	private String hiddenWord="";
	private int imageIndex = 0;
	private Label[] letters= new Label[26];
	private Image[] imgArray = {new Image("start.jpg"), new Image("head.jpg"), new Image("body.jpg"), new Image("leftLeg.jpg"), new Image("rightLeg.jpg"), new Image("leftArm.jpg"), new Image("rightArm.jpg"), new Image("leftEye.jpg"), new Image("rightEye.jpg"), new Image("mouth.jpg")};

	@FXML
	void initialize(){
		hangingImageView.setImage(imgArray[0]);
		Label[] letterTemp = {aLetter, bLetter, cLetter, dLetter, eLetter, fLetter, gLetter, hLetter, iLetter, jLetter, kLetter, lLetter, mLetter, nLetter, oLetter, pLetter, qLetter, rLetter, sLetter, tLetter, uLetter, vLetter, wLetter, xLetter, yLetter, zLetter};
		letters = Arrays.copyOf(letterTemp, 26);
		File wordFile = new File("words.txt");
		int num = new SecureRandom().nextInt(194433+1);
		int count = 1;
		try (BufferedReader br = new BufferedReader(new FileReader(wordFile))) {
			while (count <= num) {
				if(count == num){
					hiddenWord = br.readLine();
					System.out.println(hiddenWord);
				}
				else{
					br.readLine();
				}
				count++;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int hiddenWordLength = hiddenWord.length();

		for(int i = 0; i < hiddenWordLength; i++){
			guessingWordLabel.setText(guessingWordLabel.getText()+" _");
		}
	}

	@FXML
	void keyboardInput(KeyEvent e){
		String guess = e.getText().toUpperCase();

		if(guess.compareTo("A") >= 0 && guess.compareTo("Z") <= 0){								//is a letter
			if(!oldGuesses.contains(guess)){													//is a new letter
				countOfGuesses++;
				if(hiddenWord.toUpperCase().contains(guess)){									//is in guessingWord
					oldGuesses.add(guess);					
					int index = Arrays.asList(lettersString).indexOf(guess.toLowerCase());
					letters[index].setTextFill(Color.web("#00FF00"));
					letters[index].setFont(Font.font(35));

					for(int i = 0; i < hiddenWord.length(); i++){
						if(hiddenWord.toUpperCase().charAt(i) == guess.charAt(0)){				//Find letter in hidden word and show it
							char[] newValue = guessingWordLabel.getText().toCharArray();
							newValue[i*2+1] = guess.charAt(0);
							guessingWordLabel.setText(String.valueOf(newValue));
						}
					}
					if(!guessingWordLabel.getText().contains("_")){								//Guessed last letter
						gameOver(true);
						/*Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Game Over");
						alert.setHeaderText("Congratulations!");
						alert.setContentText("You won!!");
						Optional<ButtonType> result = alert.showAndWait();*/
					}
				}
				else{//WRONG GUESS

					int index = Arrays.asList(lettersString).indexOf(guess.toLowerCase());
					letters[index].setTextFill(Color.web("#FF0000"));
					letters[index].setFont(Font.font(35));
					if(imageIndex != 9){
						imageIndex++;
						hangingImageView.setImage(imgArray[imageIndex]);
					}
					else{
						gameOver(false);
					}
				}
			}
			else{			//Is not in hidden word
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Incorrect Character");
				alert.setHeaderText("Incorrect Character");
				alert.setContentText("You have already guessed this character.");
				alert.showAndWait();
			}
		}
		else{ //bad character
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Incorrect Character");
			alert.setHeaderText("Incorrect Character");
			alert.setContentText("Please type any character a-z");
			alert.showAndWait();
		}
	}


	private void gameOver(boolean wonTheGame){
		if(wonTheGame){
			TextInputDialog d = new TextInputDialog();
			d.initStyle(StageStyle.UNDECORATED);
			d.setContentText("Please enter your name: ");
			Optional<String> answer = d.showAndWait();
			if(answer.isPresent()){
				try{
					URL obj = new URL("http", "localhost", 8081, "/hangman/add");
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					con.setRequestMethod("POST");
					//con.setRequestProperty("User-Agent", USER_AGENT);
					con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

					String urlParameters = "name="+answer.get()+"&score="+countOfGuesses;

					// Send post request
					con.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					wr.writeBytes(urlParameters);
					wr.flush();
					wr.close();

					int responseCode = con.getResponseCode();
					Stage stage = (Stage) aLetter.getScene().getWindow();
					try {
						Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
						Scene scene = new Scene(root);
						scene.getRoot().requestFocus();
						stage.setScene(scene);
						
						stage.show();
					} catch(Exception e) {
						e.printStackTrace();
					}

				}catch(Exception e){
					e.printStackTrace();
				}

			}

		}
	}

}
