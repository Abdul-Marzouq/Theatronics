

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.paint.Color;
//import java.awt.Color;
import java.io.IOException;


public class startcntrl {
	@FXML
	private Button btn_mngr,btn_cshr,btn_user;
	@FXML private HBox hBoxMain;

public void onmngrpress() throws IOException {
	Stage pStage = (Stage) hBoxMain.getScene().getWindow();
	FXMLLoader loader = new FXMLLoader(Cinema.class.getResource("login.fxml"));
	GridPane pane = loader.load();
	Scene scene1 = new Scene(pane,1000,600);
	pStage.setScene(scene1);

}
	public void oncashpress() throws IOException {
		Stage pStage = (Stage) hBoxMain.getScene().getWindow();

		FXMLLoader loader2 = new FXMLLoader(Cinema.class.getResource("cashlog.fxml"));
	GridPane anchorPane = loader2.load();
	Scene anchorScene = new Scene(anchorPane, 1000, 600);

			pStage.setScene(anchorScene);

}
	public void onuserpress() throws IOException {
		Stage pStage = (Stage) hBoxMain.getScene().getWindow();

		FXMLLoader loader2 = new FXMLLoader(Cinema.class.getResource("user.fxml"));
	AnchorPane anchorPane = loader2.load();
	Scene anchorScene = new Scene(anchorPane, 1000, 600);

			pStage.setScene(anchorScene);

}
	
	public void cntrl() {
		//if(event.getSource() == btn_mngr) {
			btn_mngr.setStyle("-fx-background-color: #1C2833 ; -fx-font-size: 18px;");
			//cback.setStyle("-fx-text: white");
			btn_mngr.setTextFill(Color.WHITE);
			new FadeIn(btn_mngr).play();

	}

	public void cntrl2() {
		//if(event.getSource() == btn_mngr) {
		btn_cshr.setStyle("-fx-background-color: #34495E; -fx-font-size: 18px;");
		//cback.setStyle("-fx-text: white");
		btn_cshr.setTextFill(Color.WHITE);
		new FadeIn(btn_cshr).play();

	}

	public void cntrl3() {
		//if(event.getSource() == btn_mngr) {
		btn_user.setStyle("-fx-background-color: #5D6D7E; -fx-font-size: 18px;");
		//cback.setStyle("-fx-text: white");
		btn_user.setTextFill(Color.WHITE);
		new FadeIn(btn_user).play();

	}
	public void rel() {
		btn_mngr.setStyle("-fx-background-color: #CCCCCC; -fx-font-size: 18px;");
		//cback.setStyle("-fx-text: white");
		btn_mngr.setTextFill(Color.BLACK);
		new FadeIn(btn_mngr).play();
	}
	public void rel2() {
		btn_cshr.setStyle("-fx-background-color: #DDDDDD; -fx-font-size: 18px;");
		//cback.setStyle("-fx-text: white");
		btn_cshr.setTextFill(Color.BLACK);
		new FadeIn(btn_cshr).play();
	}

	public void rel3() {
		btn_user.setStyle("-fx-background-color: #FFFFFF; -fx-font-size: 18px;");
		//cback.setStyle("-fx-text: white");
		btn_user.setTextFill(Color.BLACK);
		new FadeIn(btn_user).play();
	}
}

