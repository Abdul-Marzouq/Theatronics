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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

public class controller {


	@FXML
	private TextField uField;
	@FXML
	private Button sButton;
	@FXML private PasswordField pField;
	@FXML private Button loginBack;
	@FXML private GridPane logpane;
	   
	  @FXML
	  public void initialize() {
		  sButton.setDisable(true);
	    		    }
	    
	   public void onButtonClicked(ActionEvent e) throws IOException, NumberFormatException, SQLException {
	    	if(e.getSource().equals(sButton)) {
	    		//if(uField.getText().equals(pField.getText()))
	    		if(PersonData.getInstance().retrieve(Integer.valueOf(uField.getText()), pField.getText()))            
	    	     {
	    			Stage p = (Stage) uField.getScene().getWindow();
	    			FXMLLoader loader = new FXMLLoader(Cinema.class.getResource("admin.fxml"));
	    	    	HBox root =  loader.load();
	    	   		
	    	   		p.setScene(new Scene(root,1000,600));
	    	   		p.show();
	    		}
	    		else {
	    			Dialog<ButtonType> adder = new Dialog<>();
	    			adder.initOwner(uField.getScene().getWindow());
	    			GridPane grid = new GridPane();
	    			grid.setVgap(10);
	    			grid.setHgap(10);
	    			grid.add(new Label("Error: Invalid Entry"), 1, 1);
	    			adder.getDialogPane().setContent(grid);
	    			adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
	    			adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
	    			//adder.getDialogPane().getButtonTypes().add(ButtonType.PREVIOUS);
	    			
	    			Optional<ButtonType> result = adder.showAndWait();
	    			uField.clear();
	    			pField.clear();
	    		}
	    	}
	    }
	    
	   public void onBackClicked()throws IOException {
		   Stage p = (Stage) logpane.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(Cinema.class.getResource("start.fxml"));
	    	HBox root =  loader.load();
	   		
	   		p.setScene(new Scene(root,1000,600));
	   		
	   }
public void handleKeyReleased() {
	    	
	
	String text = uField.getText();
	    	boolean disButtons = text.isEmpty() || text.trim().isEmpty();
	    	sButton.setDisable(disButtons);
	
	}

	}

