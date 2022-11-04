package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {

	public static void main(String[] args) {
		Connection connection  = null;
		
		try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("MyDB.sqlite");
			
			// create a statement object from the connection
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			// create a table Users
			statement.executeUpdate("DROP TABLE IF EXISTS Users");
			statement.executeUpdate("CREATE TABLE Schools "
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "FirstName VARCHAR(50), "
					+ "LastName VARCHAR(50),"
					+ "UserName VARCHAR(50) NOT NULL UNIQUE,"
					+ "Password VARCHAR(50) NOT NULL"
					+ ")");
			// insert records into Users
			statement.executeUpdate("INSERT INTO Users "
					+ "(FirstName, LastName, UserName, Password) "
					+ "VALUES ('John', 'Doe', 'jdoe', 'jd123')");
			
			statement.executeUpdate("INSERT INTO Users "
					+ "(FirstName, LastName, UserName, Password) "
					+ "VALUES ('Joey', 'Smith', 'jsmith', 'js456')");
			
//			// retrieve resultset
//			ResultSet rs = statement.executeQuery("SELECT * FROM Users");
//			
//			// extract and display data from resultset
//			while(rs.next()) {
//				System.out.print("\nFirst Name: " + rs.getString("FirstName"));
//				System.out.print("\tLast Name: " + rs.getString("LastName"));
//				System.out.print("\tUser Name: " + rs.getString("UserName"));
//				System.out.println("\tPassword: " + rs.getString("Password"));
//			}
//			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
		
		try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("MyDB.sqlite");
			
			// create a statement object from the connection
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			// No need to create a table Users
			
			// insert records into Users
			statement.executeUpdate("INSERT INTO Users "
					+ "(FirstName, LastName, UserName, Password) "
					+ "VALUES ('Jane', 'Doe', 'jdoe2', 'password123')");
			
			statement.executeUpdate("INSERT INTO Users "
					+ "(FirstName, LastName, UserName, Password) "
					+ "VALUES ('Zack', 'Smith', 'zsmith', 'zs789')");
			
			// retrieve resultset
			ResultSet rs = statement.executeQuery("SELECT * FROM Users");
			
			// extract and display data from resultset
			while(rs.next()) {
				System.out.print("\nFirst Name: " + rs.getString("FirstName"));
				System.out.print("\tLast Name: " + rs.getString("LastName"));
				System.out.print("\tUser Name: " + rs.getString("UserName"));
				System.out.println("\tPassword: " + rs.getString("Password"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
	}

}
