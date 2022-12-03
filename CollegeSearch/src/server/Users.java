package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users {

	public static void main(String[] args) {
	Connection connection  = null;
		try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("Users.sqlite");
			
			// create a statement object from the connection
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			// create a table Users
			//statement.executeUpdate("DROP TABLE Users");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users "
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "FirstName VARCHAR(50) NOT NULL, "
					+ "LastName VARCHAR(50) NOT NULL,"
					+ "email VARCHAR(50) NOT NULL UNIQUE,"
					+ "UserName VARCHAR(15) NOT NULL UNIQUE,"
					+ "Password VARCHAR(15) NOT NULL,"
					+ "Favorites"
					+ ")");
			// insert records into Users
			statement.executeUpdate("INSERT INTO Users "
					+ "(FirstName, LastName, email, UserName, Password, Favorites) "
					+ "VALUES ('John', 'Doe', 'jdoe@gmail.com', 'jdoe', 'jd123', '')");
			
			statement.executeUpdate("INSERT INTO Users "
					+ "(FirstName, LastName, email, UserName, Password, Favorites) "
					+ "VALUES ('Edgar', 'Pacheco', 'edpvpro@hotmail.com', 'nielpaxeko', 'dan4096', '')");
			
			ResultSet rs = statement.executeQuery("SELECT * FROM Users");
			// Now we create a favorites list
			
			
			// extract and display data from resultset
			while(rs.next()) {
				System.out.print("\nFirst Name: " + rs.getString("FirstName"));
				System.out.print("\tLast Name: " + rs.getString("LastName"));
				System.out.print("\tEmail: " + rs.getString("email"));
				System.out.print("\tUser Name: " + rs.getString("UserName"));
				System.out.println("\tPassword: " + rs.getString("Password"));
			}
//			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
	}
	

}
