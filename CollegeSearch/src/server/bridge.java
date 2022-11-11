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
			String tail = "&per_page=5&api_key=qinqQmQLaAi6LkMHWnddUduplNjPdqLU3jzsRaIL";
			String state = "al";
			String fields = "&fields=id,school.name,school.city,school.state,school.zip,school.school_url,latest.admissions.admission_rate.overall,latest.completion.transfer_rate.4yr.full_time_pooled,latest.cost.tuition.in_state,latest.cost.tuition.out_of_state";
			String source = "https://api.data.gov/ed/collegescorecard/v1/schools.json?school.state=";
			URL url = new URL(source+state+fields+tail);
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
				//latest.school.degree_awarded,latest.cost.tuition.in_state,latest.cost.tuition.out_of_state
				JsonNode jsonNameNode = array.get(i);
				System.out.println("-----");
				//ID
				JsonNode schoolIDNode = jsonNameNode.get("id");
				System.out.println(schoolIDNode.asText());
				//NAME
				JsonNode nameNode = jsonNameNode.get("school.name");
				System.out.println(nameNode.asText());
				//CITY
				JsonNode cityNode = jsonNameNode.get("school.city");
				System.out.println(cityNode.asText());
				//STATE
				JsonNode stateNode = jsonNameNode.get("school.state");
				System.out.println(stateNode.asText());
				//ZIP
				JsonNode zipNode = jsonNameNode.get("school.zip");
				System.out.println(zipNode.asText());
				//URL
				JsonNode urlNode = jsonNameNode.get("school.school_url");
				System.out.println(urlNode.asText());
				//ADMISSION RATE
				JsonNode adminNode = jsonNameNode.get("latest.admissions.admission_rate.overall");
				System.out.println(adminNode.asText());
				//TRANSFER RATE
				JsonNode transferNode = jsonNameNode.get("latest.completion.transfer_rate.4yr.full_time_pooled");
				System.out.println(transferNode.asText());
				//IN-STATE-TUITION
				JsonNode inStateNode = jsonNameNode.get("latest.cost.tuition.in_state");
				System.out.println(inStateNode.asText());
				//OUT-OF-STATE-TUITION
				JsonNode outStateNode = jsonNameNode.get("latest.cost.tuition.out_of_state");
				System.out.println(outStateNode.asText());
				//Insert elements
				insert(schoolIDNode,nameNode,cityNode,stateNode,zipNode,urlNode,adminNode,transferNode,inStateNode,outStateNode);
				
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
			statement.executeUpdate("DROP TABLE Schools");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Schools"
					+ "(ID INTEGER PRIMARY KEY UNIQUE, "
					+ "Name varchar(50), "
					+ "City varchar(30),"
					+ "State varchar(20),"
					+ "Zip INTEGER,"
					+ "URL TEXT varchar(50),"
					+ "AdmissionRate FLOAT,"
					+ "TransferRate FLOAT,"
					+ "InState INTEGER,"
					+ "OutState INTEGER"
					+ ")");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
		
	}
	public static void insert(JsonNode id,JsonNode name,JsonNode city,JsonNode state,JsonNode zip,JsonNode url,JsonNode admin,JsonNode transfer, JsonNode inState,JsonNode outState) throws SQLException {
		double adminRate = admin.asDouble()*100;
		double transferRate = transfer.asDouble()*100;
		try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("MyDB.sqlite");
			// create a statement object from the connection
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			// create a table Users
			statement.executeUpdate("INSERT INTO Schools "
					+ "(ID, Name, City, State, Zip, URL, AdmissionRate, TransferRate, InState, OutState)"
					+ "VALUES ("+id+", "+name+", "+city+","+state+","+zip+","+url+","+adminRate+","+transferRate+","+inState+","+outState+")");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
		
	}
	
	
}
