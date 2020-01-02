import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class admincontrol {
	
	public int name =-1;
	
@FXML
private VBox rooter;
@FXML
private ToggleButton movButt;
@FXML
private ToggleButton empButt;
@FXML
private ToggleButton scrnButt;
@FXML
private ToggleButton showButt;
@FXML
private Button addButt;
@FXML 
private Button back;

@FXML
private HBox hboxMain;
public void initialize() {
	rooter.setPrefWidth(400);
	rooter.setPrefHeight(400);
}

public void onempButtClicked(ActionEvent e) {
	
			if(empButt.isSelected())
	        //Stage p = (Stage) uField.getScene().getWindow();
			{TableView<Person> Table;
			Table = new TableView<Person>();
	    	Table.getColumns().addAll(Person.getColumn(Table));
	    	Table.setItems(PersonData.getInstance().getPersons());

	    	Table.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
	    		
	    		@Override
	    		public void handle(MouseEvent event) {
	    			if(event.getClickCount()>0) {
	    				if(Table.getSelectionModel().getSelectedIndex()>=0) {
	    					name = Table.getSelectionModel().getSelectedIndex();
	    					System.out.print(Table.getSelectionModel().getSelectedItem().getFname());
	    				}
	    			}
	    		}
	    	});
	    	//VBox root =  new VBox();
	    	rooter.getChildren().clear();
	   		rooter.getChildren().addAll(Table);
	   		//p.setScene(new Scene(root,500,500));
	   		//p.show();
			}
			else {
				rooter.getChildren().clear();
			}
		}
public void onscrnButtClicked(ActionEvent e) {
	
	if(scrnButt.isSelected())
    //Stage p = (Stage) uField.getScene().getWindow();
	{TableView<Screen> Table;
	Table = new TableView<Screen>();
	Table.getColumns().addAll(Screen.getColumn(Table));
	Table.setItems(ScreenData.getInstance().getScreens());

	Table.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			if(event.getClickCount()>0) {
				if(Table.getSelectionModel().getSelectedIndex()>=0) {
					name = Table.getSelectionModel().getSelectedIndex();
					System.out.print(Table.getSelectionModel().getSelectedItem().getScrn_name());
				}
			}
		}
	});
	//VBox root =  new VBox();
	rooter.getChildren().clear();
		rooter.getChildren().addAll(Table);
		//p.setScene(new Scene(root,500,500));
		//p.show();
	}
	else {
		rooter.getChildren().clear();
	}
}
public void onDelete() throws SQLException {
	if(empButt.isSelected() && name!=-1 && PersonData.getInstance().getPersons().size()!= name) {
		PersonData.getInstance().delPeople(name);
	}
	else if(movButt.isSelected() && name!=-1 && MovieData.getInstance().getMovies().size()!= name) {
		MovieData.getInstance().delMovie(name);
	}else if(scrnButt.isSelected() && name!=-1 && ScreenData.getInstance().getScreens().size()!= name) {
		ScreenData.getInstance().delScreen(name);
	}else if(showButt.isSelected() && name!=-1 && ShowData.getInstance().getShows().size()!= name) {
		ShowData.getInstance().delShow(name);
	}
}

public void onEdit() throws NumberFormatException, SQLException {
	Dialog<ButtonType> adder = new Dialog<>();
	adder.initOwner(hboxMain.getScene().getWindow());
	try {
		Parent root = FXMLLoader.load(getClass().getResource("addmovie.fxml"));
		adder.getDialogPane().setContent(root);
	}catch(IOException e)
	{
		System.out.println("Couldnt load the panne");
		e.printStackTrace();
		return;
	}

	if(empButt.isSelected() && name!=-1 && PersonData.getInstance().getPersons().size()!= name) {
		Person item = PersonData.getInstance().getPersons().get(name);
		
		
		
			Label label1 = new Label("Emp Id: ");
			Label label2 = new Label("Name: ");
			Label label3 = new Label("Emp Cat: ");
			Label label4 = new Label("Password: ");

			TextField text1 = new TextField();
			TextField text2 = new TextField();
			ComboBox<String> cat =  new ComboBox<String>();
			TextField passwd = new TextField();
			
			GridPane grid = new GridPane();
			text1.setText(String.valueOf(item.getEmpid()));
			text2.setText(item.getFname());
			passwd.setText(item.getPasswd());
			ObservableList<String> cats = FXCollections.observableArrayList();
			cats.add("Manager");
			cats.add("Cashier");
			cat.setItems(cats);
			cat.getSelectionModel().select(item.getEmpcat());
			
			grid.setVgap(10);
			grid.setHgap(10);
			//grid.add(label1, 1, 1);
			//grid.add(text1, 2, 1);
			grid.add(label2, 1, 1);
			grid.add(text2, 2, 1);
			grid.add(label3, 1, 2);
			grid.add(cat, 2, 2);
			grid.add(label4, 1, 3);
			grid.add(passwd, 2, 3);
			adder.getDialogPane().setContent(grid);
			adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
			adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			Optional<ButtonType> result = adder.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.OK)
			{
				 PersonData.getInstance().upPeople(new Person(item.getEmpid(),text2.getText(),cat.getSelectionModel().getSelectedItem(),passwd.getText()),name);
				 onempButtClicked(null);
				 /*TableView<Person> Table = new TableView<Person>();
			    	Table.getColumns().addAll(Person.getColumn(Table));
			    	Table.setItems(PersonData.getInstance().getPersons());
			    	rooter.getChildren().clear();
			   		rooter.getChildren().addAll(Table);*/
			}
			else
				System.out.print("cancel");
		
		
	}else if(movButt.isSelected() && name!=-1 && MovieData.getInstance().getMovies().size()!= name) {
		Movie item = MovieData.getInstance().getMovies().get(name);
		
		
		Label label0 = new Label("Movie Id: ");
		Label label1 = new Label("Movie Name: ");
		Label label2 = new Label("Movie Type: ");
		Label label3 = new Label("Start Date: ");
		Label label4 = new Label("End Date: ");
		Label label5 = new Label("Collections: ");
		Label label6 = new Label("Num of Runs: ");
		Label label7 = new Label("Max Runs: ");
		
		TextField text0 = new TextField();
		DatePicker dp1 = new DatePicker();
		DatePicker dp2 = new DatePicker();
		TextField text1 = new TextField();
		TextField text2 = new TextField();
		TextField text3 = new TextField();
		TextField text4 = new TextField();
		TextField text5 = new TextField();
		
		text1.setText(String.valueOf(item.getMovId()));
		text1.setText(item.getMovName());
		text2.setText(item.getMovieType());
		text3.setText(String.valueOf(item.getCollection()));
		text4.setText(String.valueOf(item.getNumOfRuns()));
		text5.setText(String.valueOf(item.getMaxRuns()));
		
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		//grid.add(label0, 1, 1);
		//grid.add(text0, 2, 1);
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(text2, 2, 2);
		grid.add(label3, 1, 3);
		grid.add(dp1, 2, 3);
		grid.add(label4, 1, 4);
		grid.add(dp2, 2, 4);
		//grid.add(label5, 1, 6);
		//grid.add(text3, 2, 6);
		//grid.add(label6, 1,7);
		//grid.add(text4, 2, 7);
		grid.add(label7, 1,5);
		grid.add(text5, 2, 5);
		adder.getDialogPane().setContent(grid);
		adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
		adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = adder.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK)
		{
			
			if(dp1.getValue()!=null && dp2.getValue()!=null)
			 MovieData.getInstance().upMovie(new Movie(item.getMovId(),text1.getText(),text2.getText(),dp1.getValue(),dp2.getValue(),
					 item.getCollection(),item.getNumOfRuns(),Integer.valueOf(text5.getText())),name);
			else if (dp1.getValue() == null && dp2.getValue()==null)
				MovieData.getInstance().upMovie(new Movie(item.getMovId(),text1.getText(),text2.getText(),item.getBegdate(),item.getEnddate(),
						item.getCollection(),item.getNumOfRuns(),Integer.valueOf(text5.getText())),name);
				
			else if (dp2.getValue() == null)
				MovieData.getInstance().upMovie(new Movie(item.getMovId(),text1.getText(),text2.getText(),dp1.getValue(),item.getEnddate(),
						item.getCollection(),item.getNumOfRuns(),Integer.valueOf(text5.getText())),name);
			else 
				MovieData.getInstance().upMovie(new Movie(item.getMovId(),text1.getText(),text2.getText(),item.getBegdate(),dp2.getValue(),
						item.getCollection(),item.getNumOfRuns(),Integer.valueOf(text5.getText())),name);
				
			onmovButtClicked(null);
			/*TableView<Movie> Table = new TableView<Movie>();
	    	Table.getColumns().addAll(Movie.getColumn(Table));
	    	Table.setItems(MovieData.getInstance().getMovies());
	    	rooter.getChildren().clear();
	   		rooter.getChildren().addAll(Table);*/
		}
		else
			System.out.print("cancel");
		
		
	}else if(scrnButt.isSelected() && name!=-1 && ScreenData.getInstance().getScreens().size()!= name) {
		Screen item = ScreenData.getInstance().getScreens().get(name);
		
		    //Label label1 = new Label("Screen Id: ");
			Label label2 = new Label("Screen Name: ");
			Label label3 = new Label("Screen Type: ");
			Label label4 = new Label("No of Seats: ");
			
			TextField text1 = new TextField();
			TextField text2 = new TextField();
			TextField text3 = new TextField();
			TextField text4 = new TextField();
			GridPane grid = new GridPane();
			text1.setText(String.valueOf(item.getScrn_id()));
			text2.setText(item.getScrn_name());
			text3.setText(item.getScrn_type());
			text4.setText(String.valueOf(item.getNum_seats()));
			
			grid.setVgap(10);
			grid.setHgap(10);
			//grid.add(label1, 1, 1);
			//grid.add(text1, 2, 1);
			grid.add(label2, 1, 1);
			grid.add(text2, 2, 1);
			grid.add(label3, 1, 2);
			grid.add(text3, 2, 2);
			grid.add(label4, 1, 3);
			grid.add(text4, 2, 3);
			
			adder.getDialogPane().setContent(grid);
			adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
			adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			Optional<ButtonType> result = adder.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.OK)
			{
	
				
				 ScreenData.getInstance().upScreen(new Screen(item.getScrn_id(),text2.getText(),text3.getText(),Integer.valueOf(text4.getText())),name);
				 onscrnButtClicked(null);
				 /*TableView<Screen> Table = new TableView<Screen>();
			    	Table.getColumns().addAll(Screen.getColumn(Table));
			    	Table.setItems(ScreenData.getInstance().getScreens());
			    	rooter.getChildren().clear();
			   		rooter.getChildren().addAll(Table);*/
			}
			else
				System.out.print("cancel");
		
		
	}else if(showButt.isSelected() && name!=-1 && ShowData.getInstance().getShows().size()!= name) {
		Show item = ShowData.getInstance().getShows().get(name);
		
	    //Label label1 = new Label("Show ID: ");
		Label label2 = new Label("Show Date: ");
		Label label3 = new Label("Show Time: ");
		Label label4 = new Label("Seats Left ");
		Label label5 = new Label("Screen Id ");
		//Label label6 = new Label("Movie Id ");
		
		//TextField text1 = new TextField();
		DatePicker dp = new DatePicker();
		ComboBox<String> tp = new ComboBox<String>();
		
		TextField text2 = new TextField();
		TextField text4 = new TextField();
		
		GridPane grid = new GridPane();
		//text1.setText(String.valueOf(item.getShowId()));
		text2.setText(String.valueOf(item.getSeats_left()));
		text4.setText(String.valueOf(item.getMov_id()));
		
		ObservableList<String> ids = FXCollections.observableArrayList();
		
			ids.add("9:00AM");
			ids.add("5:00PM");
			ids.add("2:00PM");
			ids.add("10:00PM"); 
		
		tp.setItems(ids);
		tp.getSelectionModel().select(item.getShowTime());
		
		ComboBox<Integer> sp =  new ComboBox<Integer>();
		sp.setItems(ScreenData.getInstance().getscrnid());
		
		//System.out.println(item.getScrn_id());
		sp.getSelectionModel().select((Integer)item.getScrn_id());
		
		ComboBox<String> mp =  new ComboBox<String>();
		mp.setItems(MovieData.getInstance().getmovid());
		int i;
		for(i =0;i<MovieData.getInstance().getMovies().size();++i)
		{
			if(MovieData.getInstance().getMovies().get(i).getMovId() == item.getMov_id())
				break;
		}
		mp.getSelectionModel().select(item.getMov_id()+"-"+MovieData.getInstance().getMovies().get(i).getMovName());
		
		grid.setVgap(10);
		grid.setHgap(10);
		//grid.add(label1, 1, 1);
		//grid.add(text1, 2, 1);
		grid.add(label2, 1, 1);
		grid.add(dp, 2, 1);
		grid.add(label3, 1, 2);
		grid.add(tp, 2, 2);
		grid.add(label4, 1, 3);
		grid.add(text2, 2, 3);
		grid.add(label5, 1, 4);
		grid.add(sp, 2, 4);
		//grid.add(label6, 1, 5);
		//grid.add(mp, 2, 5);
		
		adder.getDialogPane().setContent(grid);
		adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
		adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = adder.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK)
		{
			String temp = mp.getSelectionModel().getSelectedItem();
			String[] arrOfStr = temp.split("-", 2); 
			 if(dp.getValue()!=null)
			 ShowData.getInstance().upShow(new Show(item.getShowId(),dp.getValue(),
					 tp.getSelectionModel().getSelectedItem(),Integer.valueOf(text2.getText()),sp.getSelectionModel().getSelectedItem(),
					 item.getMov_id()),name);
			 else
				 ShowData.getInstance().upShow(new Show(item.getShowId(),item.getShowDate(),
						 tp.getSelectionModel().getSelectedItem(),Integer.valueOf(text2.getText()),sp.getSelectionModel().getSelectedItem(),
						 item.getMov_id()),name);
			 onshowButtClicked(null);
			 /*TableView<Show> Table = new TableView<Show>();
		    	Table.getColumns().addAll(Show.getColumn(Table));
		    	Table.setItems(ShowData.getInstance().getShows());
		    	rooter.getChildren().clear();
		   		rooter.getChildren().addAll(Table);*/
		}
		else
			System.out.print("cancel");
	
	
	}	
	else
	{
		adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
		adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = adder.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK)
		{
		
		}
		else
			System.out.print("cancel");
	}
}
public void onAddpressed() throws NumberFormatException, SQLException{
	Dialog<ButtonType> adder = new Dialog<>();
	adder.initOwner(hboxMain.getScene().getWindow());
	try {
		Parent root = FXMLLoader.load(getClass().getResource("addmovie.fxml"));
		adder.getDialogPane().setContent(root);
	}catch(IOException e)
	{
		System.out.println("Couldnt load the panne");
		e.printStackTrace();
		return;
	}
	if(empButt.isSelected())
	{
		
		Label label1 = new Label("Emp Id: ");
		Label label2 = new Label("Name: ");
		Label label3 = new Label("Emp Cat: ");
		Label label4 = new Label("Password: ");

		TextField text1 = new TextField();
		TextField text2 = new TextField();
		ComboBox<String> cat =  new ComboBox<String>();
		TextField passwd = new TextField();
		
		GridPane grid = new GridPane();
		
		ObservableList<String> cats = FXCollections.observableArrayList();
		cats.add("Manager");
		cats.add("Cashier");
		cat.setItems(cats);
		
		
		         
		
		grid.setVgap(10);
		grid.setHgap(10);
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(text2, 2, 2);
		grid.add(label3, 1, 3);
		grid.add(cat, 2, 3);
		grid.add(label4, 1, 4);
		grid.add(passwd, 2, 4);
		adder.getDialogPane().setContent(grid);
		adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
		adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = adder.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK)
		{
			if(text1.getText().isEmpty())
				return;
			PersonData.getInstance().addPeople(new Person(Integer.valueOf(text1.getText()),text2.getText(),
					cat.getSelectionModel().getSelectedItem(),passwd.getText()));
		}
		else
			System.out.print("cancel");
	}else if(movButt.isSelected())
	{
		Label label0 = new Label("Movie ID: ");
		Label label1 = new Label("Movie Name: ");
		Label label2 = new Label("Movie Cat: ");
		Label label3 = new Label("Start Date: ");
		Label label4 = new Label("End Date: ");
		Label label5 = new Label("Collections: ");
		Label label6 = new Label("Num of Runs: ");
		Label label7 = new Label("Max Runs: ");
		
		TextField text0 = new TextField();
		DatePicker dp1 = new DatePicker();
		DatePicker dp2 = new DatePicker();
		TextField text1 = new TextField();
		TextField text2 = new TextField();
		TextField text3 = new TextField();
		TextField text4 = new TextField();
		TextField text5 = new TextField();

		
		
		         
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.add(label0, 1, 1);
		grid.add(text0, 2, 1);
		grid.add(label1, 1, 2);
		grid.add(text1, 2, 2);
		grid.add(label2, 1, 3);
		grid.add(text2, 2, 3);
		grid.add(label3, 1, 4);
		grid.add(dp1, 2, 4);
		grid.add(label4, 1, 5);
		grid.add(dp2, 2, 5);
		//grid.add(label5, 1, 6);
		//grid.add(text3, 2, 6);
		//grid.add(label6, 1, 7);
		//grid.add(text4, 2, 7);
		//grid.add(label7, 1, 8);
		//grid.add(text5, 2, 8);
		
		adder.getDialogPane().setContent(grid);
		adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
		adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = adder.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK)
		{
			int diff = dp2.getValue().compareTo(dp1.getValue());
			
			System.out.print(diff);
			MovieData.getInstance().addMovie(new Movie(Integer.valueOf(text0.getText()),
					text1.getText(),text2.getText(),dp1.getValue(),dp2.getValue(),
					0,0,diff*4));
		}
		else
			System.out.print("cancel");
		
	}
	else if(scrnButt.isSelected())
	{
		Label label1 = new Label("Screen No: ");
		Label label2 = new Label("Screen Name: ");
		Label label3 = new Label("Screen Type: ");
		Label label4 = new Label("No of Seats: ");
		
		TextField text1 = new TextField();
		TextField text2 = new TextField();
		TextField text3 = new TextField();
		TextField text4 = new TextField();
		GridPane grid = new GridPane();
		
		
		
		
		grid.setVgap(10);
		grid.setHgap(10);
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(text2, 2, 2);
		grid.add(label3, 1, 3);
		grid.add(text3, 2, 3);
		grid.add(label4, 1, 4);
		grid.add(text4, 2, 4);
		
		adder.getDialogPane().setContent(grid);
		adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
		adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = adder.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK)
		{
			
			
			 ScreenData.getInstance().addScreen(new Screen(Integer.valueOf(text1.getText()),text2.getText(),text3.getText(),Integer.valueOf(text4.getText())));
			
		}
		else
			System.out.print("cancel");
		
	}else if(showButt.isSelected()) {
		
	    Label label1 = new Label("Show ID: ");
		Label label2 = new Label("Show Date: ");
		Label label3 = new Label("Show Time: ");
		Label label4 = new Label("Seats Left ");
		Label label5 = new Label("Screen Id ");
		Label label6 = new Label("Movie Id ");
		
		TextField text1 = new TextField();
		DatePicker dp = new DatePicker();
		ComboBox<String> tp = new ComboBox<String>();
		TextField text2 = new TextField();
		//TextField text4 = new TextField();
		
		GridPane grid = new GridPane();
		
		
		ObservableList<String> ids = FXCollections.observableArrayList();
		
			ids.add("9:00AM");
			ids.add("5:00PM");
			ids.add("2:00PM");
			ids.add("10:00PM"); 
		
		tp.setItems(ids);

		ComboBox<Integer> sp =  new ComboBox<Integer>();
		sp.setItems(ScreenData.getInstance().getscrnid());
		
		ComboBox<String> mp =  new ComboBox<String>();
		mp.setItems(MovieData.getInstance().getmovid());
		
		grid.setVgap(10);
		grid.setHgap(10);
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(dp, 2, 2);
		grid.add(label3, 1, 3);
		grid.add(tp, 2, 3);
		grid.add(label4, 1, 4);
		grid.add(text2, 2, 4);
		grid.add(label5, 1, 5);
		grid.add(sp, 2, 5);
		grid.add(label6, 1, 6);
		grid.add(mp, 2, 6);
		
		adder.getDialogPane().setContent(grid);
		adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
		adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = adder.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK)
		{
			String temp = mp.getSelectionModel().getSelectedItem();
			String[] arrOfStr = temp.split("-", 2); 
			 ShowData.getInstance().addShow(new Show(Integer.valueOf(text1.getText()),dp.getValue(),
					 tp.getSelectionModel().getSelectedItem(),Integer.valueOf(text2.getText()),sp.getSelectionModel().getSelectedItem(),
					 Integer.valueOf(arrOfStr[0])));
			
		}
		else
			System.out.print("cancel");
	
	
	}	
	else{
	adder.getDialogPane().getButtonTypes().add(ButtonType.OK);
	adder.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
	Optional<ButtonType> result = adder.showAndWait();
	if(result.isPresent() && result.get() == ButtonType.OK)
	{
		
	}
	else
		System.out.print("cancel");
	}
	
	
}
public void onmovButtClicked(ActionEvent e) {
	
	if(movButt.isSelected())
	{
		name =-1;
	TableView<Movie> Table;
	Table = new TableView<Movie>();
	Table.getColumns().addAll(Movie.getColumn(Table));
	Table.setItems(MovieData.getInstance().getMovies());

	Table.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			if(event.getClickCount()>0) {
				if(Table.getSelectionModel().getSelectedIndex()>=0) {
					name  = Table.getSelectionModel().getSelectedIndex();
					System.out.print(Table.getSelectionModel().getSelectedItem().getMovName());
				}
			}
		}
	});
	
	rooter.getChildren().clear();
		rooter.getChildren().addAll(Table);
	}
	else {
		rooter.getChildren().clear();
	}
   }



public void onshowButtClicked(ActionEvent e) {
	
	if(showButt.isSelected())
	{
		name =-1;
	TableView<Show> Table;
	Table = new TableView<Show>();
	Table.getColumns().addAll(Show.getColumn(Table));
	Table.setItems(ShowData.getInstance().getShows());

	Table.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			if(event.getClickCount()>0) {
				if(Table.getSelectionModel().getSelectedIndex()>=0) {
					name  = Table.getSelectionModel().getSelectedIndex();
					System.out.print(Table.getSelectionModel().getSelectedItem().getShowId());
				}
			}
		}
	});
	
	rooter.getChildren().clear();
		rooter.getChildren().addAll(Table);
	}
	else {
		rooter.getChildren().clear();
	}
   }

public void onHover2() {
	back.setStyle("-fx-background-color: crimson;");
	//cback.setStyle("-fx-text: white");
	back.setTextFill(Color.WHITE);
	//cback.setBackground(background);
}

public void onExit2() {
	//cback.setStyle("-fx-background-colour: white mnemonicParsing='false'");
	back.setTextFill(Color.BLACK);
	back.setStyle("-fx-background-color: white;");
}
public void onBack() throws IOException{
	Stage pStage = (Stage)rooter.getScene().getWindow();
	FXMLLoader loader2 = new FXMLLoader(Cinema.class.getResource("start.fxml"));
	HBox anchorPane = loader2.load();
	Scene anchorScene = new Scene(anchorPane, 1000, 600);
			pStage.setScene(anchorScene);

}

}


