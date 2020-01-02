 import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShowData {
private static ShowData instance =  new ShowData();
private Connection con;
	
	private List<Show> Shows;
	private DateTimeFormatter formatter;
	
	public static  ShowData getInstance() {
		return instance;
	}
	private ShowData() {
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}
	public ObservableList<Show> getShows(){
		return (ObservableList<Show>)Shows;
	}
	public void setShow(List<Show> Shows)
	{
		this.Shows =Shows;
	}
	
	public void loadShow() throws IOException, SQLException{
		Shows = FXCollections.observableArrayList();
		Shows.clear();
		if(con != null) {
			
			try {
				Statement selstmt = con.createStatement();
				String checksql = "select Show_ID, Date, Time, Seats_Left, Screen_ID, Movie_ID from Shows order by Show_ID";
				ResultSet result = selstmt.executeQuery(checksql);
				while(result.next()) {
					int id = result.getInt("Show_ID");
					Date date = result.getDate("Date");
						String time = result.getString("Time");
					int seatsleft = result.getInt("Seats_Left");
					int screenid = result.getInt("Screen_ID");
					int movieid = result.getInt("Movie_ID");
					Show show = new Show(id,(date.toLocalDate()),time,seatsleft,screenid,movieid);
					Shows.add(show);
				}
				selstmt.close();

			} 
			catch(SQLException e) {
				String createsql = "CREATE TABLE `Shows` (`Show_ID` int(11) NOT NULL,`Date` date DEFAULT NULL,`Time` varchar(10) DEFAULT NULL,`Seats_Left` int(11) DEFAULT NULL,`Screen_ID` int(11) DEFAULT NULL,`Movie_ID` int(11) DEFAULT NULL, PRIMARY KEY (`Show_ID`), UNIQUE KEY `Unique Screen Movie` (`Date`,`Time`,`Screen_ID`), KEY `Hosted on`(`Screen_ID`), KEY `Premiered at` (`Movie_ID`), CONSTRAINT `Hosted on` FOREIGN KEY (`Screen_ID`) REFERENCES `Screens` (`Screen_ID`) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT `Premiered at` FOREIGN KEY (`Movie_ID`) REFERENCES `Movies` (`Movie_ID`) ON DELETE CASCADE ON UPDATE CASCADE);";
				PreparedStatement createstmt = con.prepareStatement(createsql);
				createstmt.executeUpdate();
				createstmt.close();
			}
		}
	}
	
	public void addShow(Show item) throws SQLException {
		Shows.add(item);
		insert(item);
		MovieData.getInstance().setNumOfRunsMov(item.getMov_id());
	}
	public void upShow(Show item,int ind) throws SQLException {
		Shows.get(ind).setShowTime(item.getShowTime());
		Shows.get(ind).setShowDate(item.getShowDate());
		Shows.get(ind).setSeats_left(item.getSeats_left());
		Shows.get(ind).setScrn_id(item.getScrn_id());
		Shows.get(ind).setMov_id(item.getMov_id());

		update(item);
	}
	
	public void update(Show newShow) throws SQLException {
		
		String updatesql = "update Shows set Date = ?,Time = ?,Seats_Left = ?,Screen_ID = ? where Show_ID = ?";
		PreparedStatement updstmt = con.prepareStatement(updatesql);
		int col = 1;
		updstmt.setDate(col++, Date.valueOf(newShow.getShowDate()));
		updstmt.setString(col++, newShow.getShowTime());
		updstmt.setInt(col++, newShow.getSeats_left());
		updstmt.setInt(col++, newShow.getScrn_id());
		
		updstmt.setInt(col++, newShow.getShowId());
		updstmt.executeUpdate();
		updstmt.close();
	}
	public void insert(Show newShow) throws SQLException {
		
		String insertsql = "insert into Shows values (?, ?, ?, ?, ?, ?)";
		PreparedStatement insstmt = con.prepareStatement(insertsql);
		int col = 1;
		insstmt.setInt(col++, newShow.getShowId());
		insstmt.setDate(col++, Date.valueOf(newShow.getShowDate()));
		insstmt.setString(col++, newShow.getShowTime());
		insstmt.setInt(col++, newShow.getSeats_left());
		insstmt.setInt(col++, newShow.getScrn_id());
		insstmt.setInt(col++, newShow.getMov_id());
		insstmt.executeUpdate();
		insstmt.close();
		
		PreparedStatement movrunstmt = con.prepareStatement("update Movies set No_of_Runs = No_of_Runs + 1 where Movie_ID = ?");
		movrunstmt.setInt(1,newShow.getMov_id());
		movrunstmt.executeUpdate();
		movrunstmt.close();
	}
	
	public void delShow(int ind) throws SQLException {
		int iddel = Shows.get(ind).getShowId();
		if(!Shows.isEmpty())
		{
			Shows.remove(ind);
		}
		delete(iddel);
	}
	
	public void delete(int id) throws SQLException {
		String deletesql = "delete from Shows where Show_ID = ?";
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
	
	public List<Show> retbyMovieID(int movid) throws SQLException {
		List<Show> retshow = FXCollections.observableArrayList();;
		String retsql = "select * from Shows where Movie_ID = ?";
		PreparedStatement retstmt = con.prepareStatement(retsql);
		retstmt.setInt(1,movid);
		
		ResultSet result = retstmt.executeQuery();
	
		while(result.next()) {
			int id = result.getInt("Show_ID");
			Date date = result.getDate("Date");
			String time = result.getString("Time");
			int seatsleft = result.getInt("Seats_Left");
			int screenid = result.getInt("Screen_ID");
			int movieid = result.getInt("Movie_ID");
						
			Show show = new Show(id,(date.toLocalDate()),time,seatsleft,screenid,movieid);
			retshow.add(show);
		}
		retstmt.close();
		return retshow;
	}

	public List<Show> retbyDate(LocalDate sdate) throws SQLException {
	List<Show> retshow = FXCollections.observableArrayList();;
	String retsql = "select * from Shows where Date = ?";
	PreparedStatement retstmt = con.prepareStatement(retsql);
	retstmt.setDate(1,Date.valueOf(sdate));
	
	ResultSet result = retstmt.executeQuery();

	while(result.next()) {
		int id = result.getInt("Show_ID");
		Date date = result.getDate("Date");
		String time = result.getString("Time");
		int seatsleft = result.getInt("Seats_Left");
		int screenid = result.getInt("Screen_ID");
		int movieid = result.getInt("Movie_ID");
					
		Show show = new Show(id,(date.toLocalDate()),time,seatsleft,screenid,movieid);
		retshow.add(show);
	}
	retstmt.close();
	return retshow;
	}
	public Show getShowbyID(int id) {
		Show show = new Show();
		for(int i=0;i<Shows.size();i++) {
			if(Shows.get(i).getShowId() == id)
				return Shows.get(i);
		}
		return show;
	}
	public void deleteShowbyMovieID(int id) {
		Show show = new Show();
		for(int i=0;i<Shows.size();i++) {
			if(Shows.get(i).getMov_id() == id)
				Shows.remove(i);
		}
	}
	public void deleteShowbyScreenID(int id) {
		Show show = new Show();
		for(int i=0;i<Shows.size();i++) {
			if(Shows.get(i).getScrn_id() == id)
				Shows.remove(i);
		}
	}
}

