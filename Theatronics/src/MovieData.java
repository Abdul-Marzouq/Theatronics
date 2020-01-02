import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovieData {
private static MovieData instance =  new MovieData();
private Connection con;
	
	private List<Movie> Movies;
	private DateTimeFormatter formatter;
	
	public static  MovieData getInstance() {
		return instance;
	}
	private MovieData() {
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}
	public ObservableList<Movie> getMovies(){
		return (ObservableList<Movie>)Movies;
	}
	public void setMovie(List<Movie> Movies)
	{
		this.Movies =Movies;
	}
	
	public ObservableList<String> getmovid(){
		
		ObservableList<String> ids = FXCollections.observableArrayList();
		for(int i=0;i<Movies.size();++i)
		{
			ids.add(Movies.get(i).getMovId()+"-"+Movies.get(i).getMovName());
		}
		return ids;
	}
	public void loadMovie() throws IOException, SQLException{
		Movies = FXCollections.observableArrayList();
		Movies.clear();
		String checksql = "select Movie_ID, Movie_Name, No_of_Runs, Max_Runs, Collection, Movie_Type, Start_Date, End_Date from Movies order by Movie_ID";
		
		if(con != null) {
			try {
			Statement selstmt = con.createStatement();
		
			ResultSet result = selstmt.executeQuery(checksql);
		
			while(result.next()) {
				int id = result.getInt("Movie_ID");
				String name = result.getString("Movie_Name");
				int runs = result.getInt("No_of_Runs");
				int maxruns = result.getInt("Max_Runs");
				int collection = result.getInt("Collection");
				String movietype = result.getString("Movie_Type");
				java.sql.Date startdate = result.getDate("Start_Date");
				java.sql.Date enddate = result.getDate("End_Date");
				
			
				Movie movie = new Movie(id,name,movietype,(startdate.toLocalDate()),(enddate.toLocalDate()),collection,runs,maxruns);
				Movies.add(movie);
			}
			selstmt.close();
		}  catch(SQLException e) {
			String createsql = "CREATE TABLE `Movies` (`Movie_ID` int(11) NOT NULL, `Movie_Name` varchar(40) NOT NULL,`No_of_Runs` int(11) DEFAULT '0',`Max_runs` int(11) DEFAULT NULL, `Collection` bigint(20) DEFAULT NULL,`Movie_Type` varchar(40) DEFAULT NULL,`Start_Date` date DEFAULT NULL,`End_Date` date DEFAULT NULL, PRIMARY KEY (`Movie_ID`));";
			PreparedStatement createstmt = con.prepareStatement(createsql);
			createstmt.executeUpdate();
			createstmt.close();
		}
		}
	}
	public void addMovie(Movie item) throws SQLException {
		Movies.add(item);
		insert(item);
	}
	public void upMovie(Movie item,int ind) throws SQLException {
		Movies.get(ind).setBegdate(item.getBegdate());
		Movies.get(ind).setCollection(item.getCollection());
		Movies.get(ind).setEnddate(item.getEnddate());
		Movies.get(ind).setMaxRuns(item.getMaxRuns());
		Movies.get(ind).setNumOfRuns(item.getNumOfRuns());
		Movies.get(ind).setMovieType(item.getMovieType());
		Movies.get(ind).setMovName(item.getMovName());

		update(item);
	}
	public void update(Movie newMovie) throws SQLException {
		
		String updatesql = "update Movies set Movie_Name = ?,Movie_Type = ?,No_of_Runs = ?,Max_runs = ?,Collection = ?,Start_Date = ?,End_Date = ? where Movie_ID = ?";
		PreparedStatement updstmt = con.prepareStatement(updatesql);
		int col = 1;
		updstmt.setString(col++, newMovie.getMovName());
		updstmt.setString(col++, newMovie.getMovieType());
		updstmt.setInt(col++, newMovie.getNumOfRuns());
		updstmt.setInt(col++, newMovie.getMaxRuns());
		updstmt.setInt(col++, newMovie.getCollection());
		updstmt.setDate(col++, Date.valueOf(newMovie.getBegdate()));
		updstmt.setDate(col++, Date.valueOf(newMovie.getEnddate()));

		updstmt.setInt(col++, newMovie.getMovId());
		updstmt.executeUpdate();
		updstmt.close();
	}
	
	public void insert(Movie newMovie) throws SQLException {
		
		String insertsql = "insert into Movies values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insstmt = con.prepareStatement(insertsql);
		int col = 1;
		insstmt.setInt(col++, newMovie.getMovId());
		insstmt.setString(col++, newMovie.getMovName());
		insstmt.setInt(col++, newMovie.getNumOfRuns());
		insstmt.setInt(col++, newMovie.getMaxRuns());
		insstmt.setInt(col++, newMovie.getCollection());
		insstmt.setString(col++, newMovie.getMovieType());
		insstmt.setDate(col++, Date.valueOf(newMovie.getBegdate()));
		insstmt.setDate(col++, Date.valueOf(newMovie.getEnddate()));
		insstmt.executeUpdate();
		insstmt.close();
	}
	
	public void delMovie(int ind) throws SQLException {
		int iddel = Movies.get(ind).getMovId();
		if(!Movies.isEmpty())
		{
			Movies.remove(ind);
		}
		delete(iddel);
		ShowData.getInstance().deleteShowbyMovieID(iddel);
	}
	
	public void delete(int id) throws SQLException {
		String deletesql = "delete from Movies where Movie_ID = ?";
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
	public void setNumOfRunsMov(int id) {
		Movie temp = new Movie();
		for(int i=0;i<Movies.size();i++) {
			if(Movies.get(i).getMovId() == id) {
					temp = Movies.get(i);
					break;
			}
		}	
		temp.setNumOfRuns(temp.getNumOfRuns() + 1);
	}
	
	public Movie getMoviebyID(int id) {
		Movie movie = new Movie();
		for(int i=0;i<Movies.size();i++) {
			if(Movies.get(i).getMovId() == id)
				return Movies.get(i);
		}
		return movie;
	}
	
}
