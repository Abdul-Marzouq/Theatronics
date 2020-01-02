
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserShowData {
	private static UserShowData instance =  new UserShowData();
	private Connection con;

	private List<UserShow> UserShows;
	private DateTimeFormatter formatter;

	public static  UserShowData getInstance() {
		return instance;
	}
	private UserShowData() {
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}
	public ObservableList<UserShow> getUserShows(){
		return (ObservableList<UserShow>)UserShows;
	}
	public void setPerson(List<UserShow> UserShows)
	{
		this.UserShows =UserShows;
	}


	public void loadUserShowbyMovieID(int movid) throws IOException, SQLException{
		UserShows = FXCollections.observableArrayList();
		UserShows.clear();
		String checksql = "select m.Movie_Name, m.Movie_Type, c.Screen_Name, s.Date, s.Time, s.Seats_Left from Shows s, Movies m, Screens c where s.Movie_ID = m.Movie_ID and s.Screen_ID = c.Screen_ID and s.Movie_ID = ? order by s.Date";
		PreparedStatement retstmt = con.prepareStatement(checksql);
		retstmt.setInt(1,movid);
		ResultSet result = retstmt.executeQuery();
		
			while(result.next()) {
				String movname = result.getString("Movie_Name");
				String movtype = result.getString("Movie_Type");
				String scrname = result.getString("Screen_Name");
				Date date = result.getDate("Date");
				String time = result.getString("Time");
				int seatsleft = result.getInt("Seats_Left");

							
				UserShow ushow = new UserShow(movname,movtype,scrname,date.toLocalDate(),time,seatsleft);
				UserShows.add(ushow);
			}
			retstmt.close();
		
	}
	
	public void loadUserShowbyDate(LocalDate udate) throws IOException, SQLException{
		UserShows = FXCollections.observableArrayList();
		UserShows.clear();
		String checksql = "select m.Movie_Name, m.Movie_Type, c.Screen_Name, s.Date, s.Time, s.Seats_Left from Shows s, Movies m, Screens c where s.Movie_ID = m.Movie_ID and s.Screen_ID = c.Screen_ID and s.Date = ? order by c.Screen_ID";
		PreparedStatement retstmt = con.prepareStatement(checksql);
		retstmt.setDate(1,Date.valueOf(udate));
		ResultSet result = retstmt.executeQuery();
		
			while(result.next()) {
				String movname = result.getString("Movie_Name");
				String movtype = result.getString("Movie_Type");
				String scrname = result.getString("Screen_Name");
				Date date = result.getDate("Date");
				String time = result.getString("Time");
				int seatsleft = result.getInt("Seats_Left");

							
				UserShow ushow = new UserShow(movname,movtype,scrname,date.toLocalDate(),time,seatsleft);
				UserShows.add(ushow);
			}
			retstmt.close();
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
