import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketData {
	private Connection con;
	private static TicketData instance =  new TicketData();
	
	public static  TicketData getInstance() {
		return instance;
	}
	
	public Ticket insert(int showid,int empid) throws SQLException {
		
		String retsql = "select s.No_of_Seats, p.Seats_Left from Screens s, Shows p where p.Show_ID = ? and p.Screen_ID = s.Screen_ID";
		PreparedStatement retstmt = con.prepareStatement(retsql);
		retstmt.setInt(1,showid);
		ResultSet result = retstmt.executeQuery();
	
		int seatnum = 0,col = 1;
		while(result.next()) {
			int numofseats = result.getInt("No_of_Seats");
			int seatsleft = result.getInt("Seats_Left");
			seatnum =  numofseats - seatsleft + 1;
		}
		retstmt.close();
		
		try {
		String insertsql = "insert  into `Tickets`(`Show_ID`,`Emp_ID`,`Seat_Num`) values (?, ?, ?)"; 
		PreparedStatement insstmt = con.prepareStatement(insertsql);
		col = 1;
		insstmt.setInt(col++, showid);
		insstmt.setInt(col++, empid);
		insstmt.setInt(col++, seatnum);
		insstmt.executeUpdate();
		insstmt.close();
		}  catch (SQLException e) {
			String createsql = "CREATE TABLE `Tickets` (`Show_ID` int(11) NOT NULL,`Emp_ID` int(11) NOT NULL,`Ticket_ID` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,`Seat_Num` int(11) NOT NULL, KEY `Booked by` (`Emp_ID`),CONSTRAINT `Booked by` FOREIGN KEY (`Emp_ID`) REFERENCES `Employees` (`Emp_ID`),CONSTRAINT `Tickets` FOREIGN KEY (`Show_ID`) REFERENCES `Shows` (`Show_ID`) ON DELETE CASCADE ON UPDATE CASCADE);";
			PreparedStatement createstmt = con.prepareStatement(createsql);
			createstmt.executeUpdate();
			createstmt.close();
			String insertsql = "insert  into `Tickets`(`Show_ID`,`Emp_ID`,`Seat_Num`) values (?, ?, ?)"; 
			PreparedStatement insstmt = con.prepareStatement(insertsql);
			col = 1;
			insstmt.setInt(col++, showid);
			insstmt.setInt(col++, empid);
			insstmt.setInt(col++, seatnum);
			insstmt.executeUpdate();
			insstmt.close();
		}
		
		String updatesql = "update Movies set Collection = Collection + 100 where Movie_ID in (select Movie_ID from Shows where Show_ID = ?)";
		PreparedStatement updstmt = con.prepareStatement(updatesql);
		col = 1;
		updstmt.setInt(col++, showid);
		updstmt.executeUpdate();
		updstmt.close();
		
		String updatesql1 = "update Shows set Seats_Left = Seats_Left - 1 where Show_ID = ?";
		PreparedStatement updstmt1 = con.prepareStatement(updatesql1);
		MovieData.getInstance().getMoviebyID(showid).setCollection(MovieData.getInstance().getMoviebyID(showid).getCollection() + 100);
		
		col = 1;
		updstmt1.setInt(col++, showid);
		updstmt1.executeUpdate();
		updstmt1.close();
		ShowData.getInstance().getShowbyID(showid).setSeats_left(ShowData.getInstance().getShowbyID(showid).getSeats_left()-1);
		
		String retsql1 = "select Ticket_ID from Tickets where Show_ID = ? and Emp_ID = ? and Seat_Num = ?";
		PreparedStatement retstmt1 = con.prepareStatement(retsql1);
		retstmt1.setInt(1,showid);
		retstmt1.setInt(2,empid);
		retstmt1.setInt(3,seatnum);
		ResultSet result1 = retstmt1.executeQuery();
		
	
		int ticketid = 0;
		while(result1.next()) {
		ticketid= result1.getInt("Ticket_ID");
		}
		
		retstmt1.close();
		
		Ticket retticket = new Ticket(ticketid,seatnum,showid,empid);
		return retticket;
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
