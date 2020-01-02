

//import com.sun.java.util.jar.pack.Attribute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;





import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;


public class Cashier {

	@FXML
	private ComboBox<String> movieSelect;
	@FXML
	private DatePicker dpicker;

	@FXML
	private AnchorPane APcash;
	@FXML
	private Button cback;
	@FXML
	private VBox rooter;
	@FXML
	private Button book;
	@FXML
	private ComboBox<String> noticks;

	int sel = -1;
	public void initialize() throws IOException {
		movieSelect.setItems(MovieData.getInstance().getmovid());
		ObservableList<String> cats = FXCollections.observableArrayList();
		cats.add("1");
		cats.add("2");
		cats.add("3");
		cats.add("4");
		noticks.setItems(cats);
		noticks.getSelectionModel().select(0);
	}

	


	public void onHover() {
		cback.setStyle("-fx-background-color: white;");
		//cback.setStyle("-fx-text: white");
		cback.setTextFill(Color.BLACK);
		new FadeIn(cback).play();
		//cback.setBackground(background);
	}

	
	public void back() throws IOException{
		Stage pStage = (Stage)rooter.getScene().getWindow();
		FXMLLoader loader2 = new FXMLLoader(Cinema.class.getResource("start.fxml"));
		HBox anchorPane = loader2.load();
		Scene anchorScene = new Scene(anchorPane, 1000, 600);
				pStage.setScene(anchorScene);

	}
	public void onExit() {
		//cback.setStyle("-fx-background-colour: white mnemonicParsing='false'");
		cback.setTextFill(Color.WHITE);
		cback.setStyle("-fx-background-color: transparent;");
		new FadeIn(cback).play();
	}

	public void onHover2() {
		book.setStyle("-fx-background-color: crimson;");
		//cback.setStyle("-fx-text: white");
		book.setTextFill(Color.WHITE);
		new FadeIn(book).play();
		//cback.setBackground(background);
	}

	public void onExit2() {
		//cback.setStyle("-fx-background-colour: white mnemonicParsing='false'");
		book.setTextFill(Color.BLACK);
		book.setStyle("-fx-background-color: white;");
		new FadeIn(book).play();
	}

	public void setMovieSelect(ActionEvent e) throws IOException, NumberFormatException, SQLException {

		if(movieSelect.getSelectionModel().getSelectedItem()==null)
			return;
		String movName = (String) movieSelect.getSelectionModel().getSelectedItem();

		
		TableView<Show> Table;
		Table = new TableView<Show>();
		Table.getColumns().addAll(Show.getColumn(Table));
		String[] arrOfStr = movName.split("-", 2);
		List<Show> lists = (List<Show>)ShowData.getInstance().retbyMovieID(Integer.valueOf(arrOfStr[0]));
		//List<Show> list = FXCollections.observableArrayList();
		
		
		
		/*System.out.print(arrOfStr[0]);
		for(i=0;i<lists.size();++i)
			if(lists.get(i).getMov_id()==Integer.valueOf(arrOfStr[0]))
				list.add(lists.get(i));*/

		Table.setItems((ObservableList<Show>)lists);

		Table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount()>0) {
					if(Table.getSelectionModel().getSelectedIndex()>=0) {
						sel = Table.getSelectionModel().getSelectedItem().getShowId();
						System.out.print(Table.getSelectionModel().getSelectedItem().getMov_id());
					}
				}
			}
		});
		//VBox root =  new VBox();
		rooter.getChildren().clear();
		new FadeIn(Table).play();
		rooter.getChildren().addAll(Table);
		
		if(dpicker.getValue()!=null)
		dpicker.getEditor().clear();


	}
	public void onBooked() throws SQLException, NumberFormatException, IOException {
		if(sel==-1)
			return;
		Dialog<ButtonType> adder = new Dialog<>();
		adder.initOwner(rooter.getScene().getWindow());
		Ticket item = null;
		int i=0,k=0;
		String ticks = "BOOKED OUT!!!";
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("addmovie.fxml"));
			adder.getDialogPane().setContent(root);
		}catch(IOException e)
		{
			System.out.println("Couldnt load the panne");
			e.printStackTrace();
			return;
		}
		if(dpicker.getValue()!=null)
		{
			System.out.println("yolo"+cashlog.getInstance().retemp()+"man"+sel);
			
			if(cashlog.getInstance().retemp()!=-1)
			{
				k = noticks.getSelectionModel().getSelectedIndex()+1;
				
				for(i=0;i<k;++i) {
					if(ShowData.getInstance().getShowbyID(sel).getSeats_left()==0)
						break;
				 item = TicketData.getInstance().insert(sel,cashlog.getInstance().retemp());
				 ticks = "Ticket ID = "+ String.valueOf(item.ticketID)+"  Seat No. = "+String.valueOf(item.seatNum);
				 grid.add(new Label(ticks), 1, 3+i);
				}
				//System.out.println("lololol"+item.ticketID);
			}
			date_selected();
		}
		else if(movieSelect.getSelectionModel().getSelectedItem()!=null){
			System.out.println("in troy");
			if(cashlog.getInstance().retemp()!=-1)
			{k = noticks.getSelectionModel().getSelectedIndex()+1;
			
			for(i=0;i<k;++i) {
				if(ShowData.getInstance().getShowbyID(sel).getSeats_left()==0)
					break;
			 item = TicketData.getInstance().insert(sel,cashlog.getInstance().retemp());
			 ticks = "Ticket ID = "+ String.valueOf(item.ticketID)+"  Seat No. = "+String.valueOf(item.seatNum);
			 grid.add(new Label(ticks), 1, 3+i);
			}
			}
			setMovieSelect(null);
		}
		
	
		if(k==i)
		{Label label1 = new Label("Show ID: "+String.valueOf(item.showID));
		Label label2 = new Label("Emp ID: "+String.valueOf(item.empID));
		grid.add(label1, 1, 1);
		grid.add(label2, 1, 2);
		
		
		grid.add(new Button("Print"),1,i+3);
		}else if(i==0 && sel!=-1)
		{
			grid.add(new Label(ticks), 1, 1);
		}
		else
		{Label label1 = new Label("Show ID: "+String.valueOf(item.showID));
		Label label2 = new Label("Emp ID: "+String.valueOf(item.empID));
			grid.add(label1, 1, 1);
			grid.add(label2, 1, 2);
			grid.add(new Label("SOLD OUT!!!"), 1, i+3);
			grid.add(new Button("Print"),1,i+4);
		}
		adder.getDialogPane().setContent(grid);
		adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
		adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		//adder.getDialogPane().getButtonTypes().add(ButtonType.PREVIOUS);

		Optional<ButtonType> result = adder.showAndWait();
	}
	public void date_selected() throws SQLException
	{
		if(dpicker.getValue()!=null)
		{
			TableView<Show> Table;
			Table = new TableView<Show>();
			Table.getColumns().addAll(Show.getColumn(Table));
			
			List<Show> lists = (List<Show>)ShowData.getInstance().retbyDate(dpicker.getValue());
			Table.setItems((ObservableList<Show>)lists);

			Table.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if(event.getClickCount()>0) {
						if(Table.getSelectionModel().getSelectedIndex()>=0) {
							sel = Table.getSelectionModel().getSelectedItem().getShowId();
							System.out.print(Table.getSelectionModel().getSelectedItem().getMov_id());
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

