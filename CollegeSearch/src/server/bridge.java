package server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

public class bridge {
	static String inline = "";
	static Connection connection = null;
	static Statement statement = null;
	public static void main(String[] args) throws MalformedURLException, SQLException {
		//createTable();
		try {
			String tail = "&per_page=10&api_key=qinqQmQLaAi6LkMHWnddUduplNjPdqLU3jzsRaIL";
			String state = "wy";
			String fields = "&fields=id,school.name,school.city,school.state,school.zip,school.school_url,school.ownership,latest.admissions.admission_rate.overall,latest.completion.transfer_rate.4yr.full_time_pooled,latest.cost.tuition.in_state,latest.cost.tuition.out_of_state,latest.admissions.sat_scores.average.overall,latest.student.size,latest.academics.program.bachelors";
			String source = "https://api.data.gov/ed/collegescorecard/v1/schools.json?school.state=";
			//String full = "https://api.data.gov/ed/collegescorecard/v1/schools.json?school.state=al&fields=id,school.name,school.city,school.state,school.zip,school.school_url,school.ownership,latest.admissions.admission_rate.overall,latest.completion.transfer_rate.4yr.full_time_pooled,latest.cost.tuition.in_state,latest.cost.tuition.out_of_state,latest.admissions.sat_scores.average.overall,latest.student.size,latest.academics.program.bachelors&per_page=2&api_key=qinqQmQLaAi6LkMHWnddUduplNjPdqLU3jzsRaIL";
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
			/*
			 *  "TN", "TX", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY" };
    */
			
			System.out.print("Here is the raw data in JSON format: " + "\n");
			//System.out.print(inLine);
			//Parse
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectReader reader = objectMapper.reader();
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
				//OWNERSHIP
				JsonNode ownershipNode = jsonNameNode.get("school.ownership");
				String ownerShip;
				switch(ownershipNode.asInt()) {
				  case 1:
					  ownerShip = "Public";
				    break;
				  case 2:
					  ownerShip = "Private nonprofit";
				    break;
				  case 3:
					  ownerShip = "Private for-profit";
					break;
				  default:
					  ownerShip = "N/A";
				}
				System.out.println(ownershipNode.asText());
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
				//AVERAGE SAT
				JsonNode satNode = jsonNameNode.get("latest.admissions.sat_scores.average.overall");
				System.out.println(satNode.asText());
				//POPULATION
				JsonNode populationNode = jsonNameNode.get("latest.student.size");
				System.out.println(populationNode.asText());
				//BACHELOR
				LinkedList list = new LinkedList();
				bachelorCheck(jsonNameNode, list);
				String bachelor = list.toString().replace("[", "");
				String bachelors = bachelor.replace("]", "");
				System.out.print(bachelors);
				//Insert elements
				insert(schoolIDNode,nameNode,cityNode,stateNode,zipNode,urlNode, ownerShip, adminNode,transferNode,inStateNode,outStateNode,satNode,populationNode,bachelors);
				
			}
			//DISPLAY METADATA
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
					+ "City varchar(30),"
					+ "State varchar(20),"
					+ "Zip INTEGER,"
					+ "URL TEXT varchar(50),"
					+ "Ownership TEXT varchar(50),"
					+ "AdmissionRate FLOAT,"
					+ "TransferRate FLOAT,"
					+ "InState INTEGER,"
					+ "OutState INTEGER,"
					+ "SAT INTEGER,"
					+ "Population INTEGER,"
					+ "Programs TEXT varchar(1000)"
					+ ")");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
		
	}
	public static void insert(JsonNode id,JsonNode name,JsonNode city,JsonNode state,JsonNode zip,JsonNode url, String ownerShip, JsonNode admin, JsonNode transfer, JsonNode inState, JsonNode outState, JsonNode sat, JsonNode population, String list) throws SQLException {
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
					+ "(ID, Name, City, State, Zip, URL, Ownership, AdmissionRate, TransferRate, InState, OutState, SAT, Population, Programs)"
					+ "VALUES ("+id+", "+name+", "+city+","+state+","+zip+","+url+", '"+ownerShip+"' ,"+adminRate+","+transferRate+","+inState+","+outState+", "+sat+", "+population+", '"+list+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			ConnectionUtilities.closeConnection(connection);
		}
		
	}
	public static void bachelorCheck(JsonNode jsonNameNode, LinkedList list) throws SQLException {
		//Majors
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.agriculture"), list, "Bachelors in Agriculture");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.biological"), list, "Bachelors in Biology");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.business_marketing"), list,"Bachelors in Business");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.construction"), list,  "Bachelors in Construction");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.communication"), list, "Bachelors in Communication Studies");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.computer"), list, "Bachelors in Computer Science");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.security_law_enforcement"), list, "Bachelors in Criminal Justice");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.personal_culinary"), list, "Bachelors in Culinary Studies");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.education"), list, "Bachelors in Education");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.engineering"), list, "Bachelors in Engineering");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.language"), list, "Bachelors in Linguistics");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.legal"), list, "Bachelors in Culinary Studies");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.ethnic_cultural_gender"), list, "Bachelors in Gender Studies");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.health"), list,"Bachelors in Health");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.history"), list, "Bachelors in History");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.humanities"), list, "Bachelors in Humanities");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.english"), list, "Bachelors in Law");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.mathematics"), list, "Bachelors in Mathematics");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.philosophy_religious"), list, "Bachelors in Philosophy");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.psychology"), list, "Bachelors in Psychology");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.physical_science"), list, "Bachelors in Physics");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.social_science"), list, "Bachelors in Social Sciences");
		addToBachelors(jsonNameNode.get("latest.academics.program.bachelors.transportation"), list, "Bachelors in Transportation");
		
	}
	public static void addToBachelors(JsonNode node, LinkedList list, String bachelor) {
		if (node.asInt()>=1) {
			list.offer(bachelor);
		}
	}
	
	
	
}
