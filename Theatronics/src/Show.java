import java.time.LocalTime;
import java.util.ArrayList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class Show {
 private int showId;
 private  String showTime;
 private LocalDate showDate;
 private int seats_left;
 private int scrn_id;
 private int mov_id;
 
 public Show(int showId,LocalDate showDate, String showTime,int seats_left,int scrn_id,int mov_id) {
	 super();
	 this.showId = showId;
	 this.showDate = showDate;
	 this.showTime = showTime;
	 this.seats_left = seats_left;
	 this.scrn_id =scrn_id;
	 this.mov_id = mov_id;
	 
 }
public Show() {
	// TODO Auto-generated constructor stub
}
public int getShowId() {
	return showId;
}
public void setShowId(int showId) {
	this.showId = showId;
}
public String getShowTime() {
	return showTime;
}
public void setShowTime(String showTime) {
	this.showTime = showTime;
}
public LocalDate getShowDate() {
	return showDate;
}
public void setShowDate(LocalDate showDate) {
	this.showDate = showDate;
}
public int getSeats_left() {
	return seats_left;
}
public void setSeats_left(int seats_left) {
	this.seats_left = seats_left;
}

public static ArrayList<TableColumn<Show,?>> getColumn(TableView<Show> table){
	int i;
	ArrayList<TableColumn<Show,?>> cols =  new ArrayList<TableColumn<Show,?>>();
	String[] colNames = {"Show Id","Show Date","Show Time","Seats Left","Screen Id","Movie Id"};
	String[] varNames =  {"showId","showDate","showTime","seats_left","scrn_id","mov_id"};
	Integer[] colWidth = {15,20,20,15,15,15};
	
	i=0;
	TableColumn<Show,Integer> shw_idcol = new TableColumn<>(colNames[i++]);
	TableColumn<Show,LocalDate> shw_datecol = new TableColumn<>(colNames[i++]);
	TableColumn<Show,String> shw_timecol = new TableColumn<>(colNames[i++]);
	TableColumn<Show,Integer> seats_leftcol = new TableColumn<>(colNames[i++]);
	TableColumn<Show,Integer> scrn_idcol = new TableColumn<>(colNames[i++]);
	TableColumn<Show,Integer> mov_idcol = new TableColumn<>(colNames[i++]);

	i=0;
	shw_idcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	shw_datecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	shw_timecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	seats_leftcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	scrn_idcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	mov_idcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));

	i=0;
	shw_idcol.setCellValueFactory(new PropertyValueFactory<Show,Integer>(varNames[i++]));
	shw_datecol.setCellValueFactory(new PropertyValueFactory<Show,LocalDate>(varNames[i++]));
	shw_timecol.setCellValueFactory(new PropertyValueFactory<Show,String>(varNames[i++]));
	seats_leftcol.setCellValueFactory(new PropertyValueFactory<Show,Integer>(varNames[i++]));
	scrn_idcol.setCellValueFactory(new PropertyValueFactory<Show,Integer>(varNames[i++]));
	mov_idcol.setCellValueFactory(new PropertyValueFactory<Show,Integer>(varNames[i++]));

	
	cols.add(shw_idcol);
	cols.add(shw_datecol);
	cols.add(shw_timecol);
	cols.add(seats_leftcol);
	cols.add(scrn_idcol);
	cols.add(mov_idcol);
	
	return cols;
	
}
public int getScrn_id() {
	return scrn_id;
}
public void setScrn_id(int scrn_id) {
	this.scrn_id = scrn_id;
}
public int getMov_id() {
	return mov_id;
}
public void setMov_id(int mov_id) {
	this.mov_id = mov_id;
}
 
}
