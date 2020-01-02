import java.util.ArrayList;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Screen {
	private int scrn_id,num_seats;
	private String scrn_type,scrn_name;
	
	public Screen(int scrn_id,String scrn_name,String scrn_type,int num_seats) {
		super();
		this.scrn_id = scrn_id;
		this.scrn_name = scrn_name;
		this.scrn_type = scrn_type;
		this.num_seats = num_seats;
	}
	
	public static ArrayList<TableColumn<Screen,?>> getColumn(TableView<Screen> table){
		int i;
		ArrayList<TableColumn<Screen,?>> cols =  new ArrayList<TableColumn<Screen,?>>();
		String[] colNames = {"Screen Id","Screen Name","Screen Type","No of Seats"};
		String[] varNames =  {"scrn_id","scrn_name","scrn_type","num_seats"};
		Integer[] colWidth = {25,25,25,25};
		
		i=0;
		TableColumn<Screen,Integer> scrn_idcol = new TableColumn<>(colNames[i++]);
		TableColumn<Screen,String> scrn_namecol = new TableColumn<>(colNames[i++]);
		TableColumn<Screen,String> scrn_typecol = new TableColumn<>(colNames[i++]);
		TableColumn<Screen,Integer> num_seatscol = new TableColumn<>(colNames[i++]);

		i=0;
		scrn_idcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		scrn_namecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		scrn_typecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		num_seatscol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));

		i=0;
		scrn_idcol.setCellValueFactory(new PropertyValueFactory<Screen,Integer>(varNames[i++]));
		scrn_namecol.setCellValueFactory(new PropertyValueFactory<Screen,String>(varNames[i++]));
		scrn_typecol.setCellValueFactory(new PropertyValueFactory<Screen,String>(varNames[i++]));
		num_seatscol.setCellValueFactory(new PropertyValueFactory<Screen,Integer>(varNames[i++]));

		
		cols.add(scrn_idcol);
		cols.add(scrn_namecol);
		cols.add(scrn_typecol);
		cols.add(num_seatscol);
		
		return cols;
		
	}

	public int getScrn_id() {
		return scrn_id;
	}

	public void setScrn_id(int scrn_id) {
		this.scrn_id = scrn_id;
	}

	public int getNum_seats() {
		return num_seats;
	}

	public void setNum_seats(int num_seats) {
		this.num_seats = num_seats;
	}

	public String getScrn_type() {
		return scrn_type;
	}

	public void setScrn_type(String scrn_type) {
		this.scrn_type = scrn_type;
	}

	public String getScrn_name() {
		return scrn_name;
	}

	public void setScrn_name(String scrn_name) {
		this.scrn_name = scrn_name;
	}
}
