package application;	
import server.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import server.ConnectionUtilities;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.MenuBarSkin;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class Main extends Application implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String name;
	private String state;
	@FXML
    private BorderPane borderPane = new BorderPane();
	static Connection connection = null;
	static Statement statement = null;
	String css = this.getClass().getResource("application.css").toExternalForm();
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage loginStage) {
		try {
			borderPane = FXMLLoader.load(getClass().getResource("home.fxml"));
			loginStage.setTitle("CollegeSearchTest");
			Scene scene = new Scene(borderPane,600,700);
			loginStage.setScene(scene);
			loginStage.show();
			pane = FXMLLoader.load(getClass().getResource("login.fxml"));
			borderPane.setCenter(pane);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// Login elements
	
    @FXML
    private AnchorPane pane;
    @FXML
    private Label heading;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passWordField;
    @FXML
    private Label passWordLabel;
    @FXML
    private Label query;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField userNameField;
    @FXML
    private Label userNameLabel;
    @FXML
    public void login(ActionEvent event) throws IOException, SQLException { 
    	String userName = userNameField.getText().toString().trim();
    	String passWord = passWordField.getText().toString().trim();
    	if (loginCheck(userName,passWord) == true) {
    		borderPane = FXMLLoader.load(getClass().getResource("home.fxml"));
        	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      	  	scene = new Scene(borderPane, 600, 700);
      	  	stage.setScene(scene);
      	  	pane = FXMLLoader.load(getClass().getResource("/application/search.fxml"));
      	  	paneSwitch(pane);
      	  
      	  	menuBar.setDisable(false);
      	  	menuBar.setVisible(true);
    	}
    	else {
    		heading.setText("Username or Password were entered incorrectly, please try again.");
    		// Set text to red
    		heading.setTextFill(Color.color(1, 0, 0));
    	}
    	
    }
    
    private boolean loginCheck(String userName, String passWord) throws SQLException {
    	Boolean bool = false;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("Users.sqlite");
			// create a statement object from the connection
			statement = connection.createStatement();
			String query = ("SELECT * FROM Users WHERE UserName LIKE ? and Password LIKE ?");
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, passWord);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()==true) {
				bool = true;
			} else {
				bool = false;
			}
			
		} catch (SQLException e) {
			bool = false;
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return bool;
	}
    
    // Create elements
    @FXML
    private Button back2login;
    @FXML
    private TextField confirmWord;
    @FXML
    private Button createAccountButton;
    @FXML
    private Label createHeading;
    @FXML
    private TextField emailField;
    @FXML
    private TextField createName;
    @FXML
    private TextField createWord;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;

    @FXML
    void create(ActionEvent event) throws IOException {
    	borderPane = FXMLLoader.load(getClass().getResource("home.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  	  	scene = new Scene(borderPane, 600, 400);
  	  	stage.setScene(scene);
  	  	pane = FXMLLoader.load(getClass().getResource("/application/create.fxml"));
  	  	paneSwitch(pane);
    }
    @FXML
    void createAccount(ActionEvent event) throws SQLException {
    	String firstName = firstNameField.getText().toString().trim();
    	String lastName = lastNameField.getText().toString().trim();
    	String email = emailField.getText().toString().trim();
    	String userName = createName.getText().toString().trim();
    	String passWord = createWord.getText().toString().trim();
    	String confirmedWord = confirmWord.getText().toString().trim();
    	// Password checker
    	Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasLetter = letter.matcher(passWord);
        Matcher hasDigit = digit.matcher(passWord);
        Matcher hasSpecial = special.matcher(passWord);
    	if (!passWord.equals(confirmedWord)) {
    		createHeading.setText("Passwords do not match");
    		// Set text to red
    		createHeading.setTextFill(Color.color(1, 0, 0));
    	}
    	else if (passWord.length()<8) {
    		createHeading.setText("Password is too short");
    		// Set text to red
    		createHeading.setTextFill(Color.color(1, 0, 0));
    	}
    	else if (passWord.length()>8 && hasLetter.find() && hasDigit.find() && hasSpecial.find()) {
    		createUser(firstName,lastName,email,userName,passWord);
    	}
    	else {
    		createHeading.setText("Password must contain a number and special character");
    		// Set text to red
    		createHeading.setTextFill(Color.color(1, 0, 0));
    	}
    }


    private void createUser(String firstName, String lastName, String email, String userName, String passWord) throws SQLException {
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("Users.sqlite");
			// create a statement object from the connection
			statement = connection.createStatement();
			// insert records into Users
			statement.executeUpdate("INSERT INTO Users "
					+ "(FirstName, LastName, email, UserName, Password) "
					+ "VALUES ('"+firstName+"', '"+lastName+"', '"+email+"' , '"+userName+"', '"+passWord+"')");
			createHeading.setText("Account creation succesful");
    		// Set text to red
    		createHeading.setTextFill(Color.color(0, 1, 0));
		} catch (Exception e) {
			createHeading.setText("Was not able to create user or user already exists");
    		// Set text to red
    		createHeading.setTextFill(Color.color(1, 0, 0));
		} finally {
			connection.close();
		}
	}
	// Search elements
    @FXML
    private ListView<String> resultsList = new ListView<String>();
    String[] states = {"AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY" };
    @FXML
    private Label nameLabel;
    @FXML
    private Button searchButton;
    @FXML
    private ChoiceBox<String> stateBox = new ChoiceBox();
     @FXML
    private Label stateLabel;
    @Override
	public void initialize(URL arg0, ResourceBundle arg1){
    	stateBox.getItems().addAll(states);
    	
		
	}
    @FXML
    void search(ActionEvent event) throws IOException, SQLException {
    	name = nameField.getText();
    	state  = stateBox.getValue();
    	/*borderPane = FXMLLoader.load(getClass().getResource("home.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  	  	scene = new Scene(borderPane);
  	  	stage.setScene(scene);
    	results = FXMLLoader.load(getClass().getResource("/application/results.fxml"));
    	paneSwitch(results);*/
  	  	displaySchool(name, state);
    }
	
	// Results elements
    @FXML
    private Button backButton;
    @FXML
    private Label resultsLabel;
   //This is where the magic happens
	public void displaySchool(String name, String state) throws SQLException {
		resultsList.getItems().clear();
		try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("MyDB.sqlite");
			// create a statement object from the connection
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			// create a table Users
			ResultSet rs = statement.executeQuery("SELECT * FROM Schools WHERE State = '"+state+"' OR NAME LIKE '%"+name+"%'");
			
			// extract and display data from resultset
			while(rs.next()) {
				resultsList.getItems().add(rs.getString("Name") + "      State: " + rs.getString("State"));
				System.out.println("Name: " + rs.getString("Name"));
				System.out.println("ID: " + rs.getString("ID"));
				System.out.println("State: " + rs.getString("State"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
	}
    // Account elements
	@FXML
	private Label currentEmail;
	@FXML
    private Label currentFirst;
    @FXML
    private Label currentLast;
    @FXML
    private Label currentUsername;
    // Edit elements
    @FXML
    private TextField confrimNewPassword;
    @FXML
    private Button editButton;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newUsername;
    @FXML
    void edit(ActionEvent event) {

    }
    // Menu
	@FXML
	private Menu accounMenu;
	@FXML 
	private Menu homeMenu;
	@FXML 
	private MenuBar menuBar;
	@FXML
	private MenuItem logOutMenu;
	@FXML
	private TextField nameField;
	@FXML
	private MenuItem editAccountMenu;
	@FXML
    private MenuItem favoritesMenu;
	@FXML
    private MenuItem searchMenu;
	@FXML
    void logOut(ActionEvent event) throws IOException {
		borderPane = FXMLLoader.load(getClass().getResource("home.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  	  	scene = new Scene(borderPane, 600, 700);
  	  	stage.setScene(scene);
  	  	pane = FXMLLoader.load(getClass().getResource("/application/login.fxml"));
  	  	paneSwitch(pane);
	}
	@FXML
    void editFlip(ActionEvent event) throws IOException {
		pane = FXMLLoader.load(getClass().getResource("/application/edit.fxml"));
  	  	paneSwitch(pane);
    }

    @FXML
    void favoritesFlip(ActionEvent event) throws IOException {
    	pane = FXMLLoader.load(getClass().getResource("/application/favorites.fxml"));
  	  	paneSwitch(pane);
    }
	@FXML
	void searchFlip(ActionEvent event) throws IOException {
		pane = FXMLLoader.load(getClass().getResource("/application/search.fxml"));
  	  	paneSwitch(pane);
	}
	@FXML
	void homeFlip(ActionEvent event) throws IOException {
		
	}
	@FXML
	void detailsFlip(ActionEvent event) throws IOException {
		pane = FXMLLoader.load(getClass().getResource("/application/details.fxml"));
  	  	paneSwitch(pane);
	}
	@FXML
	void loginFlip(ActionEvent event) throws IOException {
		pane = FXMLLoader.load(getClass().getResource("/application/login.fxml"));
  	  	paneSwitch(pane);
	}
	@FXML
	void paneSwitch(Pane pane)  {
		borderPane.setCenter(pane);
	}

}
