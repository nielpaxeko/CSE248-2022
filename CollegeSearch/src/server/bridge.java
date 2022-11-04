package server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class bridge {
	static String inline = "";
	static Connection connection = null;
	static Statement statement = null;
	public static void main(String[] args) throws MalformedURLException, SQLException {
		createTable();
		try {
			//First of all, connect to api and dowload school name, city and state for now
			String key = "&api_key=qinqQmQLaAi6LkMHWnddUduplNjPdqLU3jzsRaIL";
			String state = "wy";
			String fields = "&fields=id,school.name,school.state";
			String source = "https://api.data.gov/ed/collegescorecard/v1/schools.json?school.state=";
			URL url = new URL(source+state+fields+key);
			//Check if connection is made
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responseCode = conn.getResponseCode();
			
			if (responseCode != 200) {
				throw new RuntimeException("HttpURLConnection: " + responseCode);
			}
			else {
				Scanner sc = new Scanner(url.openStream());
				while(sc.hasNextLine()) {
					inline += sc.nextLine();
				}
				sc.close();
			}
			System.out.print("Here is the raw data in JSON format: " + "\n");
			//System.out.print(inLine);
			//Parse
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.readValue(inline, JsonNode.class);
			JsonNode array = node.get("results");
			for (int i = 0; i < array.size(); i++) {
				JsonNode jsonNameNode = array.get(i);
				System.out.println("-----");
				JsonNode schoolIDNode = jsonNameNode.get("id");
				System.out.println(schoolIDNode.asText());
				JsonNode nameNode = jsonNameNode.get("school.name");
				System.out.println(nameNode.asText());
				JsonNode stateNode = jsonNameNode.get("school.state");
				System.out.println(stateNode.asText());
				//Insert elements
				insert(schoolIDNode,nameNode,stateNode);
				
			}
			System.out.println("-----");
			JsonNode child = node.get("metadata");
			JsonNode totalField = child.get("total");
			String total = totalField.asText();
			System.out.println("total = " + total);
			JsonNode pageField = child.get("page");
			String page = pageField.asText();
			System.out.println("page = " + page);
			JsonNode per_pageField = child.get("per_page");
			String per_page = per_pageField.asText();
			System.out.println("per_page=" + per_page);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

	public static void createTable() throws SQLException {
		try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("MyDB.sqlite");
			// create a statement object from the connection
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			// create a table of schools
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Schools"
					+ "(ID INTEGER PRIMARY KEY UNIQUE, "
					+ "Name varchar(50), "
					+ "State varchar(50)"
					+ ")");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
		
	}
	public static void insert(JsonNode id,JsonNode name,JsonNode state) throws SQLException {
		try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("MyDB.sqlite");
			// create a statement object from the connection
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			// create a table Users
			statement.executeUpdate("INSERT INTO Schools "
					+ "(ID, Name, State)"
					+ "VALUES ("+id+", "+name+", "+state+")");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
		
	}
	
	
}
