import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class sqlogin {
	@FXML
	private TextField uField;
	@FXML
	private Button sButton;
	@FXML private PasswordField pField;
	@FXML private Button loginBack;
	@FXML private GridPane logpane;
	  
	
	
	public static String log="";
	public static String pass="";
	  
	  public String retlogin() {
		  return log;
	  }
	  public String retpass() {
		  return log;
	  }
	  
	  private static sqlogin instance =  new sqlogin();
		
		public static  sqlogin getInstance() {
			return instance;
		}
		
		
		
	  public void initialize() {
		  sButton.setDisable(true);
	    		    }
	    
	   public void onButtonClicked(ActionEvent e) throws Exception {
	    	if(e.getSource().equals(sButton)) {
	    		//if(uField.getText().equals(pField.getText()))
	    		
	    			Stage p = (Stage) uField.getScene().getWindow();
	    			FXMLLoader loader = new FXMLLoader(Cinema.class.getResource("start.fxml"));
	    	    	HBox root =  loader.load();
	    	   		log = uField.getText();
	    	   		pass = pField.getText();
	    	   		try {
	    	   			PersonData.getInstance().initconnect(log,pass);
	    	   			if(PersonData.getInstance().getSqlpsswd() == false) {
	    	   				Dialog<ButtonType> adder = new Dialog<>();
			    			adder.initOwner(uField.getScene().getWindow());
			    			GridPane grid = new GridPane();
			    			grid.setVgap(10);
			    			grid.setHgap(10);
			    			grid.add(new Label("Invalid Username or Password"), 1, 1);
			    			adder.getDialogPane().setContent(grid);
			    			adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
			    			adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			    			//adder.getDialogPane().getButtonTypes().add(ButtonType.PREVIOUS);
			    			
			    			Optional<ButtonType> result = adder.showAndWait();
			    			uField.clear();
			    			pField.clear();
	    	   			}
	    	   			else {
	    	   				PersonData.getInstance().connect(log,pass);
	    	   				MovieData.getInstance().connect(log,pass);
	    	   				ScreenData.getInstance().connect(log,pass);
	    	   				ShowData.getInstance().connect(log,pass);
	    	   				TicketData.getInstance().connect(log,pass);
	    	   				UserShowData.getInstance().connect(log,pass);
	    					PersonData.getInstance().loadPeople();
	    					MovieData.getInstance().loadMovie();
	    					ScreenData.getInstance().loadScreen();
	    					ShowData.getInstance().loadShow();
	    					p.setScene(new Scene(root,1000,600));
	    					p.show();
	    	   			}
	    			}catch(IOException e1)
	    			{
	    				System.out.println(e1.getMessage());
	    				
	    			}
	    		
	    	}
	    }
	    
	   
public void handleKeyReleased() {
	    	
	
	String text = uField.getText();
	    	boolean disButtons = text.isEmpty() || text.trim().isEmpty();
	    	sButton.setDisable(disButtons);
	
	}

	}