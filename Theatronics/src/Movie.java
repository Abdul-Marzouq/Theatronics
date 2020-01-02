import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Movie {
	
private int movId;
private String movName;
private LocalDate begdate;
private LocalDate enddate;
private String movieType;


private int Collection;
private int numOfRuns;
private int maxRuns;

public Movie(int movId,String fname,String movieType,LocalDate begdate,LocalDate enddate,int Collection,int numOfRuns,int maxRuns) {
	super();
	this.movId = movId;
	this.movName = fname;
	this.movieType = movieType;
	this.begdate = begdate;
	this.enddate = enddate;
	this.Collection = Collection;
	this.numOfRuns = numOfRuns;
	this.maxRuns = maxRuns;
}


public Movie() {
	// TODO Auto-generated constructor stub
}


public String getMovName() {
	return movName;
}
public void setMovName(String movName) {
	this.movName = movName;
}

public LocalDate getEnddate() {
	return enddate;
}
public void setEnddate(LocalDate enddate) {
	this.enddate = enddate;
}
public LocalDate getBegdate() {
	return begdate;
}
public void setBegdate(LocalDate begdate) {
	this.begdate = begdate;
}

public static ArrayList<TableColumn<Movie,?>> getColumn(TableView<Movie> table){
	int i;
	ArrayList<TableColumn<Movie,?>> cols =  new ArrayList<TableColumn<Movie,?>>();
	String[] colNames = {"Movie Id","Movie Name","Movie_Type","Start Date","End Date","Collection","No of Runs","Max Runs"};
	String[] varNames =  {"movId","movName","movieType","begdate","enddate","Collection","numOfRuns","maxRuns"};
	Integer[] colWidth = {15,25,20,20,20,15,15,15};
	
	i=0;
	TableColumn<Movie,Integer> movidcol = new TableColumn<>(colNames[i++]);
	TableColumn<Movie,String> movnamecol = new TableColumn<>(colNames[i++]);
	TableColumn<Movie,String> movietypecol = new TableColumn<>(colNames[i++]);
	TableColumn<Movie,LocalDate> begdatecol = new TableColumn<>(colNames[i++]);
	TableColumn<Movie,LocalDate> enddatecol = new TableColumn<>(colNames[i++]);
	TableColumn<Movie,Integer> collectcol = new TableColumn<>(colNames[i++]);
	TableColumn<Movie,Integer> numruncol = new TableColumn<>(colNames[i++]);
	TableColumn<Movie,Integer> maxruncol = new TableColumn<>(colNames[i++]);
	

	i=0;
	movidcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	movnamecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	movietypecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	begdatecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	enddatecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	collectcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	numruncol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	maxruncol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
	

	i=0;
	movidcol.setCellValueFactory(new PropertyValueFactory<Movie,Integer>(varNames[i++]));
	movnamecol.setCellValueFactory(new PropertyValueFactory<Movie,String>(varNames[i++]));
	movietypecol.setCellValueFactory(new PropertyValueFactory<Movie,String>(varNames[i++]));
	begdatecol.setCellValueFactory(new PropertyValueFactory<Movie,LocalDate>(varNames[i++]));
    enddatecol.setCellValueFactory(new PropertyValueFactory<Movie,LocalDate>(varNames[i++]));
    collectcol.setCellValueFactory(new PropertyValueFactory<Movie,Integer>(varNames[i++]));
    numruncol.setCellValueFactory(new PropertyValueFactory<Movie,Integer>(varNames[i++]));
    maxruncol.setCellValueFactory(new PropertyValueFactory<Movie,Integer>(varNames[i++]));

    cols.add(movidcol);
	cols.add(movnamecol);
	cols.add(movietypecol);
	cols.add(begdatecol);
	cols.add(enddatecol);
	cols.add(collectcol);
	cols.add(numruncol);
	cols.add(maxruncol);
	
	return cols;
	
}


public String getMovieType() {
	return movieType;
}


public void setMovieType(String movieType) {
	this.movieType = movieType;
}


public int getMovId() {
	return movId;
}


public void setMovId(int movId) {
	this.movId = movId;
}


public int getCollection() {
	return Collection;
}


public void setCollection(int collection) {
	Collection = collection;
}


public int getNumOfRuns() {
	return numOfRuns;
}


public void setNumOfRuns(int numOfRuns) {
	this.numOfRuns = numOfRuns;
}


public int getMaxRuns() {
	return maxRuns;
}


public void setMaxRuns(int maxRuns) {
	this.maxRuns = maxRuns;
}
}
