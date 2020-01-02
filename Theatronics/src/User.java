
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

public class User {
	@FXML
	private ComboBox movieSelect;
	@FXML
	private AnchorPane APcash;
	@FXML
	private Button uback;
	@FXML
	private VBox rooter;
	@FXML
	private DatePicker dpicker;

	int sel =-1;
	public void initialize() throws IOException {
		movieSelect.setItems(MovieData.getInstance().getmovid());

	}

	public void setMovieSelect(ActionEvent e) throws IOException, NumberFormatException, SQLException {
	
		if(movieSelect.getSelectionModel().getSelectedItem()==null)
			return;
		String movName = (String) movieSelect.getSelectionModel().getSelectedItem();
		//Stage p = (Stage)movieSelect.getScene().getWindow();
		
		TableView<UserShow> Table= new TableView<UserShow>();
		Table.getColumns().addAll(UserShow.getColumn(Table));
		String[] arrOfStr = movName.split("-", 2);
		UserShowData.getInstance().loadUserShowbyMovieID(Integer.valueOf(arrOfStr[0]));
		List<UserShow> lists = (List<UserShow>)UserShowData.getInstance().getUserShows();
		//List<Show> list = FXCollections.observableArrayList();
		
		
		
		/*System.out.print(arrOfStr[0]);
		for(i=0;i<lists.size();++i)
			if(lists.get(i).getMov_id()==Integer.valueOf(arrOfStr[0]))
				list.add(lists.get(i));*/

		Table.setItems((ObservableList<UserShow>)lists);

		Table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount()>0) {
					if(Table.getSelectionModel().getSelectedIndex()>=0) {
						//sel = Table.getSelectionModel().getSelectedItem().getShowId();
						System.out.print(Table.getSelectionModel().getSelectedItem().getMovName());
					}
				}
			}
		});
		//VBox root =  new VBox();
		rooter.getChildren().clear();
		rooter.getChildren().addAll(Table);
		
		if(dpicker.getValue()!=null)
			dpicker.getEditor().clear();


	}


	public void onHover() {
		uback.setStyle("-fx-background-color: crimson;");
		//cback.setStyle("-fx-text: white");
		uback.setTextFill(Color.WHITE);
		new FadeIn(uback).play();
	}

	public void back() throws IOException{
		Stage pStage = (Stage)APcash.getScene().getWindow();
		FXMLLoader loader2 = new FXMLLoader(Cinema.class.getResource("start.fxml"));
		HBox anchorPane = loader2.load();
		Scene anchorScene = new Scene(anchorPane, 1000, 600);
		pStage.setScene(anchorScene);

	}

	public void onExit() {
		//cback.setStyle("-fx-background-colour: white mnemonicParsing='false'");
		//Duration duration = new Duration.ofSeconds(0.3);
		uback.setTextFill(Color.BLACK);
		uback.setStyle("-fx-background-color: white;");
		new FadeIn(uback).play();
	}
	public void date_selected() throws SQLException, IOException
	{
		if(dpicker.getValue()!=null)
		{
			TableView<UserShow> Table = new TableView<UserShow>();
			Table.getColumns().addAll(UserShow.getColumn(Table));
			UserShowData.getInstance().loadUserShowbyDate(dpicker.getValue());
			
			List<UserShow> lists = (List<UserShow>)UserShowData.getInstance().getUserShows();
			Table.setItems((ObservableList<UserShow>)lists);

			Table.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if(event.getClickCount()>0) {
						if(Table.getSelectionModel().getSelectedIndex()>=0) {
							//sel = Table.getSelectionModel().getSelectedItem().getShowId();
							System.out.print(Table.getSelectionModel().getSelectedItem().getMovName());
						}
					}
				}
			});
			//VBox root =  new VBox();
			rooter.getChildren().clear();
			rooter.getChildren().addAll(Table);
			movieSelect.getSelectionModel().clearSelection();
		}
	}
}
