package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataConnection {
	
	private Connection c = null;
	
	public DataConnection() {
		
		try {
		   Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:myplace.db");
	    } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	}
	
	public boolean validAccount(String user, String pass) throws Exception {
		PreparedStatement account_statement = c.prepareStatement("SELECT username, password FROM accounts WHERE username = ? AND password = ?");
		account_statement.setString(1, user);
		account_statement.setString(2, pass);
		ResultSet results = account_statement.executeQuery();
		if (results.next()) {
			account_statement.close();
			results.close();
			return true;
		}
		else {
			account_statement.close();
			results.close();
			return false;
		}
	}
	
	public boolean userExists(String user) throws Exception {
		PreparedStatement username_statement = c.prepareStatement("SELECT username FROM accounts WHERE username = ?");
		username_statement.setString(1, user);
		ResultSet results = username_statement.executeQuery();
		if (results.next()) {
			username_statement.close();
			results.close();
			return true;
		}
		else {
			username_statement.close();
			results.close();
			return false;
		}
	}
	
	public int addUser(String user, String pass, String answer1, String answer2) throws Exception {
		if (user.isEmpty() || pass.isEmpty() || answer1.isEmpty() || answer2.isEmpty()) {
			return 1; //1 corresponds to fields being empty
		}
		else if (userExists(user) == true) {
			return 2; //2 corresponds to username already being taken
		}
		else {
			PreparedStatement add_statement = c.prepareStatement("INSERT INTO accounts(username, password, q1_answer, q2_answer) VALUES(?,?,?,?)");
			add_statement.setString(1, user);
			add_statement.setString(2, pass);
			add_statement.setString(3, answer1);
			add_statement.setString(4, answer2);
			add_statement.executeUpdate();
			add_statement.close();
			return 3; //3 corresponds to account being added successfully
		}
	}
	
	public String deleteUser(String user) throws Exception {
		PreparedStatement delete_statement = c.prepareStatement("DELETE FROM accounts WHERE username = ?");
		delete_statement.setString(1, user);
		delete_statement.executeUpdate();
		delete_statement.close();
		System.out.println("Your account has been deleted");
		return user;
	}
	
	/*public void createNewTable() throws Exception {
		String locations = "CREATE TABLE IF NOT EXISTS locations (\n"
				+ "name text NOT NULL, \n"
				+ "address text NOT NULL, \n"
				+ ");";
		try(Statement stmt = c.createStatement()){
			stmt.execute(locations);
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}*/
	
	//adding location to user's personal lists
	//for the list parameter, 1 is for the list of places visited and 2 for future places
	public int addLocation(Location location, int uid, int list) throws Exception {
		String query = "INSERT INTO locations(uid, name, address, list) VALUES(?,?,?,?)";
		PreparedStatement addLocation = c.prepareStatement(query);
		addLocation.setInt(1, uid);
		addLocation.setString(2, location.getName());
		addLocation.setString(3, location.getAddress());
		addLocation.setInt(4, list);
		addLocation.executeUpdate();
		addLocation.close();
		System.out.println("Successfully added location!");
		return 1; //1 corresponds to location being added successfully
	}
	
	//deletes location 
	public int deleteLocation(Location location, int uid, int list) throws Exception{
		String query = "DELETE FROM locations WHERE uid = ? AND name = ?";
		PreparedStatement delete_location = c.prepareStatement(query);
		delete_location.setInt(1, uid);
		delete_location.setString(2, location.getName());
		delete_location.executeUpdate();
		delete_location.close();
		System.out.println("Successfully deleted a location from list.");
		return 1;
	}
	
	public void test() {
		int test = 12345678;
	}
	
	public void close() throws Exception {
		c.close();
	}
}
