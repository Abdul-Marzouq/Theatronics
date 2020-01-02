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

public class ScreenData {
	
	private static ScreenData instance =  new ScreenData();
	private Connection con;
	
	private List<Screen> Screens;
	private DateTimeFormatter formatter;
	
	public static  ScreenData getInstance() {
		return instance;
	}
	private ScreenData() {
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}
	public ObservableList<Screen> getScreens(){
		return (ObservableList<Screen>)Screens;
	}
	public ObservableList<Integer> getscrnid(){
		
		ObservableList<Integer> ids = FXCollections.observableArrayList();
		for(int i=0;i<Screens.size();++i)
		{
			ids.add(Screens.get(i).getScrn_id());
		}
		return ids;
	}
	public void setScreen(List<Screen> Screens)
	{
		this.Screens =Screens;
	}
	
	public void loadScreen() throws IOException, SQLException{
		Screens = FXCollections.observableArrayList();
		Screens.clear();
		String checksql = "select Screen_ID, Screen_Name, Screen_Type, No_of_Seats from Screens order by Screen_ID";		
		
		if(con != null) {
			try {
			Statement selstmt = con.createStatement();
		
			ResultSet result = selstmt.executeQuery(checksql);
		
			while(result.next()) {
				int id = result.getInt("Screen_ID");
				String name = result.getString("Screen_Name");
				String cat = result.getString("Screen_Type");
				int num = result.getInt("No_of_Seats");
	
			
				Screen screen = new Screen(id,name,cat,num);
				Screens.add(screen);
			}
			selstmt.close();
		} catch (SQLException e) {
			String createsql = "CREATE TABLE `Screens` (`Screen_ID` int(11) NOT NULL,`Screen_Name` varchar(40) DEFAULT NULL,`Screen_Type` varchar(40) DEFAULT NULL,`No_of_Seats` int(11) DEFAULT NULL,PRIMARY KEY (`Screen_ID`));";
			PreparedStatement createstmt = con.prepareStatement(createsql);
			createstmt.executeUpdate();
			createstmt.close();
		}
		}
	}
	
	public void addScreen(Screen item) throws SQLException {
		Screens.add(item);
		insert(item);
	}
	public void upScreen(Screen item,int ind) throws SQLException {
		Screens.get(ind).setScrn_name(item.getScrn_name());
		Screens.get(ind).setScrn_type(item.getScrn_type());
		Screens.get(ind).setNum_seats(item.getNum_seats());

		update(item);
	}
	
	public void update(Screen newScreen) throws SQLException {
		
		String updatesql = "update Screens set Screen_Name = ?,Screen_Type = ?,No_of_Seats = ? where Screen_ID = ?";
		PreparedStatement updstmt = con.prepareStatement(updatesql);
		int col = 1;
		updstmt.setString(col++, newScreen.getScrn_name());
		updstmt.setString(col++, newScreen.getScrn_type());
		updstmt.setInt(col++, newScreen.getNum_seats());
		
		updstmt.setInt(col++, newScreen.getScrn_id());
		updstmt.executeUpdate();
		updstmt.close();
	}
	public void insert(Screen newScreen) throws SQLException {
		
		String insertsql = "insert into Screens values (?, ?, ?, ?)";
		PreparedStatement insstmt = con.prepareStatement(insertsql);
		int col = 1;
		insstmt.setInt(col++, newScreen.getScrn_id());
		insstmt.setString(col++, newScreen.getScrn_name());
		insstmt.setString(col++, newScreen.getScrn_type());
		insstmt.setInt(col++, newScreen.getNum_seats());
		insstmt.executeUpdate();
		insstmt.close();
	}
	
	public void delScreen(int ind) throws SQLException {
		int iddel = Screens.get(ind).getScrn_id();
		if(!Screens.isEmpty())
		{
			Screens.remove(ind);
		}
		delete(iddel);
		ShowData.getInstance().deleteShowbyScreenID(iddel);
		
	}
	
	public void delete(int id) throws SQLException {
		String deletesql = "delete from Screens where Screen_ID = ?";
		PreparedStatement delstmt = con.prepareStatement(deletesql);
		delstmt.setInt(1, id);
		delstmt.executeUpdate();
		delstmt.close();
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
}
