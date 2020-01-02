import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Person {
	private int empid;
	private String fname,empcat,passwd;
	
	
	public Person(int empid,String fname,String empcat,String passwd) {
		super();
		this.fname = fname;
		this.empid=empid;
		this.empcat=empcat;
		this.passwd=passwd;
	}
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public String getFname() {
		return fname;
	}
	
	
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	
	
	
	/*@Override
	 * public String toString() {
		return fname + ","+ lname + "," + bdate;
	}*/
	
	public static ArrayList<TableColumn<Person,?>> getColumn(TableView<Person> table){
		int i;
		ArrayList<TableColumn<Person,?>> cols =  new ArrayList<TableColumn<Person,?>>();
		String[] colNames = {"Emp Id","Name","Emp Cat","Password"};
		String[] varNames =  {"empid","fname","empcat","passwd"};
		Integer[] colWidth = {25,25,25,25};
		
		i=0;
		TableColumn<Person,Integer> empidcol = new TableColumn<>(colNames[i++]);
		TableColumn<Person,String> fnamecol = new TableColumn<>(colNames[i++]);
		TableColumn<Person,String> empcatcol = new TableColumn<>(colNames[i++]);
		TableColumn<Person,String> passwdcol = new TableColumn<>(colNames[i++]);


		i=0;
		empidcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		fnamecol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		empcatcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));
		passwdcol.prefWidthProperty().bind(table.widthProperty().divide(100 / colWidth[i++]));

		i=0;
		empidcol.setCellValueFactory(new PropertyValueFactory<Person,Integer>(varNames[i++]));
		fnamecol.setCellValueFactory(new PropertyValueFactory<Person,String>(varNames[i++]));
		empcatcol.setCellValueFactory(new PropertyValueFactory<Person,String>(varNames[i++]));
		passwdcol.setCellValueFactory(new PropertyValueFactory<Person,String>(varNames[i++]));

		
		cols.add(empidcol);
		cols.add(fnamecol);
		cols.add(empcatcol);
		cols.add(passwdcol);

		return cols;
		
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getEmpcat() {
		return empcat;
	}

	public void setEmpcat(String empcat) {
		this.empcat = empcat;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}