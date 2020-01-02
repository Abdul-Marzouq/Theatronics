import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonData {
	private static PersonData instance =  new PersonData();
	
	private Connection con;
	private Connection initcon;
	private boolean sqlpsswd;
	
	private List<Person> People;
	private DateTimeFormatter formatter;
	
	public static  PersonData getInstance() {
		return instance;
	}
	private PersonData() {
		sqlpsswd = false;
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}
	public ObservableList<Person> getPersons(){
		return (ObservableList<Person>)People;
	}
	public void setPerson(List<Person> People)
	{
		this.People =People;
	}
	public void loadPeople() throws IOException, SQLException{
		People = FXCollections.observableArrayList();

			People.clear();
			String checksql = "select Emp_ID, Emp_Name, Emp_Category, Emp_Passwd from Employees order by Emp_ID";		
			
			if(con != null) {
				try {
				Statement selstmt = con.createStatement();
			
				ResultSet result = selstmt.executeQuery(checksql);
			
				while(result.next()) {
					int id = result.getInt("Emp_ID");
					String name = result.getString("Emp_Name");
					String cat = result.getString("Emp_Category");
					String psswd = result.getString("Emp_Passwd");
		
				
					Person people = new Person(id,name,cat,psswd);
					People.add(people);
				}
				selstmt.close();
				}  catch (SQLException e) {
					String createsql = "CREATE TABLE `Employees` (`Emp_ID` int(11) NOT NULL,`Emp_Name` varchar(40) NOT NULL,`Emp_Category` varchar(40) DEFAULT NULL,`Emp_Passwd` varchar(60) DEFAULT NULL,PRIMARY KEY (`Emp_ID`));";
					PreparedStatement createstmt = con.prepareStatement(createsql);
					createstmt.executeUpdate();
					createstmt.close();
					String createsql1 = "insert  into `Employees`(`Emp_ID`,`Emp_Name`,`Emp_Category`,`Emp_Passwd`) values (1,'admin','Manager','root');";
					PreparedStatement createstmt1 = con.prepareStatement(createsql1);
					createstmt1.executeUpdate();
					createstmt1.close();
					Person people = new Person(1,"admin","Manager","root");
					People.add(people);
				}
			}
		}

	public void addPeople(Person item) throws SQLException {
		People.add(item);
		insert(item);
	}
	public void upPeople(Person item,int ind) throws SQLException {
		People.get(ind).setEmpcat(item.getEmpcat());
		People.get(ind).setFname(item.getFname());
		People.get(ind).setPasswd(item.getPasswd());

		update(item);
	}
	public void delPeople(int ind) throws SQLException {
		int iddel = People.get(ind).getEmpid();
		if(!People.isEmpty())
		{
			People.remove(ind);
		}
		delete(iddel);
	}
	public void update(Person newPerson) throws SQLException {
		
		String updatesql = "update Employees set Emp_Name = ?,Emp_Category = ?,Emp_Passwd = ? where Emp_ID = ?";
		PreparedStatement updstmt = con.prepareStatement(updatesql);
		int col = 1;
		updstmt.setString(col++, newPerson.getFname());
		updstmt.setString(col++, newPerson.getEmpcat());
		updstmt.setString(col++, newPerson.getPasswd());
		updstmt.setInt(col++, newPerson.getEmpid());
		updstmt.executeUpdate();
		updstmt.close();
	}
	public void insert(Person newPerson) throws SQLException {
		
		String insertsql = "insert into Employees values(?, ?, ?, ?)";
		PreparedStatement insstmt = con.prepareStatement(insertsql);
		int col = 1;
		insstmt.setInt(col++, newPerson.getEmpid());
		insstmt.setString(col++, newPerson.getFname());
		insstmt.setString(col++, newPerson.getEmpcat());
		insstmt.setString(col++, newPerson.getPasswd());
		insstmt.executeUpdate();
		insstmt.close();
	}
	
	public void delete(int id) throws SQLException {
		String deletesql = "delete from Employees where Emp_ID = ?";
		PreparedStatement delstmt = con.prepareStatement(deletesql);
		delstmt.setInt(1, id);
		delstmt.executeUpdate();
		delstmt.close();
	}
	
	public void initconnect(String username, String password) throws Exception {
		if(initcon == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw  new Exception("Driver not found");
			}
			String connectionUrl = "jdbc:mysql://localhost:3306";
			try {
				initcon = DriverManager.getConnection(connectionUrl,username,password);
				sqlpsswd = true;
			} catch (SQLException e1) {
				sqlpsswd = false;
			}
			if(sqlpsswd == true) {
				try {
					String createsql = "CREATE DATABASE Theatronics;";
					Statement createstmt = initcon.createStatement();
					createstmt.executeUpdate(createsql);
					sqlpsswd = true;
					createstmt.close();
					}  
					catch(SQLException e2) {
						System.out.println("fgfdgrfg");
					}
			} 
			}
		if(initcon!=null) {
			try {
				initcon.close();
				System.out.println("Init Disconnection Successful....");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void connect(String username, String password) throws Exception {
		if(con == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw  new Exception("Driver not found");
			}
			String connectionUrl = "jdbc:mysql://localhost:3306/Theatronics";
	
			con = DriverManager.getConnection(connectionUrl,username,password);
			System.out.println("Connection Successful....." + con);
			}
	}
	
	public void disconnect() {
		if(con!=null) {
			try {
				con.close();
				System.out.println("Disconnection Successful....");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public boolean retrieve(int id,String psswd) throws SQLException {
		String retsql = "select * from Employees where Emp_ID = ?";
		PreparedStatement retstmt = con.prepareStatement(retsql);
		retstmt.setInt(1,id);
		ResultSet result2 = retstmt.executeQuery();
		while(result2.next()) {
			
			String password = result2.getString("Emp_Passwd");
			String cat = result2.getString("Emp_Category");
			//System.out.print(id + " " + psswd + " " + cat + " " + password);
			retstmt.close();
			if(cat.equals("Manager") && password.equals(psswd))
				{System.out.print(true);
				return true;
				}
				else
				{System.out.print(false);
					return false;
				}
		}
		return false;
	}
	public boolean retrievecash(int id,String psswd) throws SQLException {
		String retsql = "select * from Employees where Emp_ID = ?";
		PreparedStatement retstmt = con.prepareStatement(retsql);
		retstmt.setInt(1,id);
		
		ResultSet result2 = retstmt.executeQuery();
		while(result2.next()) {
			
			String password = result2.getString("Emp_Passwd");
			String cat = result2.getString("Emp_Category");
			//System.out.print(id + " " + psswd + " " + cat + " " + password);
			retstmt.close();
			if(cat.equals("Cashier") && password.equals(psswd))
				{System.out.print(true);
				return true;
				}
				else
				{System.out.print(false);
					return false;
				}
		}
		return false;
	}
	public boolean getSqlpsswd() {
		return sqlpsswd;
	}
	
} 












