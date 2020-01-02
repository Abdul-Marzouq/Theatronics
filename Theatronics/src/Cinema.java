 

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cinema extends Application{

	Button btn_mngr,btn_cshr,btn_user;
	
	@Override
	public void init() throws Exception {
		/*btn_mngr = new Button("Manager");
		btn_cshr= new Button("Cashier");
		btn_user =  new Button("User");
		btn_mngr.setMinHeight(600); 
		btn_cshr.setMinHeight(600);
		btn_user.setMinHeight(600); 
		btn_mngr.setMinWidth(300); 
		btn_cshr.setMinWidth(300);
		btn_user.setMinWidth(300); */
		
		
	}
	@Override
	public void start(Stage pStage) throws Exception {
		
		/*HBox root =  new HBox();
		root.setAlignment(Pos.CENTER);
		//root.setSpacing(100);
		FXMLLoader loader = new FXMLLoader(Cinema.class.getResource("login.fxml"));
        GridPane pane = loader.load();
        root.getChildren().addAll(btn_mngr,btn_cshr,btn_user);
		Scene scene = new Scene(root,1000,600);
		Scene scene1 = new Scene(pane,1000,600);
		btn_mngr.setOnAction(
				e->pStage.setScene(scene1)
		);*/
		
		FXMLLoader loader0 = new FXMLLoader(Cinema.class.getResource("sqlogin.fxml"));
		GridPane root =  loader0.load();
		Scene scene = new Scene(root,1000,600);

		
		pStage.setScene(scene);
		pStage.setTitle("Theatronics Inc.");
		pStage.show();
		
	}
	public void stop() {
		PersonData.getInstance().disconnect();
		MovieData.getInstance().disconnect();
		ScreenData.getInstance().disconnect();
		ShowData.getInstance().disconnect();
		TicketData.getInstance().disconnect();
		UserShowData.getInstance().disconnect();
	}
	
	public static void main(String args[]) {
		launch(args);
	}
	
	

}
