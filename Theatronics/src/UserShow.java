
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserShow {


	//private int movId;
	private String movName;
	private String movType;
	private String scrName;
	private LocalDate date;
	private String hours;
	private int seatsLeft;

	public UserShow(String fname, String movType, String scrName, LocalDate date, String hours, int seatsLeft) {
		super();
		//this.movId = movId;
		this.movName = fname;
		this.movType = movType;
		this.scrName = scrName;
		this.date = date;
		this.hours = hours;
		this.seatsLeft = seatsLeft;
	}

	public static ArrayList<TableColumn<UserShow,?>> getColumn(TableView<UserShow> table){
		int i;
		ArrayList<TableColumn<UserShow,?>> cols =  new ArrayList<TableColumn<UserShow,?>>();
		String[] colNames = {"Movie Name", "2D/3D", "Screen Name", "Date", "Time", "Seats Remaining"};
		String[] varNames =  {"movName", "movType", "scrName", "date", "hours", "seatsLeft"};
		Integer[] colWidth = {20,10,20,15,15,15};

		i=0;
		TableColumn<UserShow,String> movnamecol = new TableColumn<>(colNames[i++]);
		TableColumn<UserShow,String> typeval = new TableColumn<>(colNames[i++]);
		TableColumn<UserShow,LocalDate> screenName = new TableColumn<>(colNames[i++]);
		TableColumn<UserShow,LocalDate> dateofs = new TableColumn<>(colNames[i++]);
		TableColumn<UserShow,Integer> hrs = new TableColumn<>(colNames[i++]);
		TableColumn<UserShow,Integer> sleft = new TableColumn<>(colNames[i++]);


		i=0;
		
		movnamecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		typeval.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		screenName.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		dateofs.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		hrs.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		sleft.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));


		i=0;
		
		movnamecol.setCellValueFactory(new PropertyValueFactory<UserShow,String>(varNames[i++]));
		typeval.setCellValueFactory(new PropertyValueFactory<UserShow,String>(varNames[i++]));
		screenName.setCellValueFactory(new PropertyValueFactory<UserShow,LocalDate>(varNames[i++]));
		dateofs.setCellValueFactory(new PropertyValueFactory<UserShow,LocalDate>(varNames[i++]));
		hrs.setCellValueFactory(new PropertyValueFactory<UserShow,Integer>(varNames[i++]));
		sleft.setCellValueFactory(new PropertyValueFactory<UserShow,Integer>(varNames[i++]));

		
		cols.add(movnamecol);
		cols.add(typeval);
		cols.add(screenName);
		cols.add(dateofs);
		cols.add(hrs);
		cols.add(sleft);

		return cols;

	}


	public String getMovName() {
		return movName;
	}

	public void setMovName(String movName) {
		this.movName = movName;
	}

	public String getMovType() {
		return movType;
	}

	public void setMovType(String movType) {
		this.movType = movType;
	}

	public String getScrName() {
		return scrName;
	}

	public void setScrName(String scrName) {
		this.scrName = scrName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public int getSeatsLeft() {
		return seatsLeft;
	}

	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
	}
}
