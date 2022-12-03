package application;	
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Main extends Application implements Initializable {
	@FXML
    private BorderPane borderPane = new BorderPane();
	private Stage stage;
	private Scene scene;
	private String school = "";
	private static CurrentUser user;
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
			loginStage.setTitle("College Search Application");
			Scene scene = new Scene(borderPane,800,800);
			scene.getStylesheets().add(css);
			loginStage.setScene(scene);
			loginStage.show();
			pane = FXMLLoader.load(getClass().getResource("login.fxml"));
			borderPane.setCenter(pane);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// Login Elements and Methods
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
    		// If correct, change pane
    		borderPane = FXMLLoader.load(getClass().getResource("home.fxml"));
        	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      	  	scene = new Scene(borderPane, 800, 800);
      	  	scene.getStylesheets().add(css);
      	  	stage.setScene(scene);
      	  	pane = FXMLLoader.load(getClass().getResource("/application/search.fxml"));
      	  	paneSwitch(pane);
      	  	menuBar = new MenuBar();
    	}
    	else {
    		// Set text to red if not correct and prompt retry
    		heading.setText("Username or Password were entered incorrectly, please try again.");
    		heading.setTextFill(Color.color(1, 0, 0));
    	}
    	
    }
    
    private static boolean loginCheck(String userName, String passWord) throws SQLException {
    	Boolean bool = false;
    	PreparedStatement preparedStatement = null;
    	ResultSet rs = null;
    	try {
			// establish a connection
			connection = ConnectionUtilities.getConnection("Users.sqlite");
			// create a statement object from the connection
			statement = connection.createStatement();
			String query = ("SELECT * FROM Users WHERE UserName LIKE ? and Password LIKE ?");
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, passWord);
			rs = preparedStatement.executeQuery();
			// If user exists, create instance of current user
			if (rs.next()==true) {
				bool = true;
				user = new CurrentUser(rs.getInt("ID"),rs.getString("FirstName").toString(), rs.getString("LastName").toString(), rs.getString("UserName").toString(), rs.getString("email").toString(),rs.getString("Password").toString(),rs.getString("Favorites"));
				
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
  	  	scene = new Scene(borderPane, 800, 800);
  	  	scene.getStylesheets().add(css);
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
					+ "(FirstName, LastName, email, UserName, Password, Favorites) "
					+ "VALUES ('"+firstName+"', '"+lastName+"', '"+email+"' , '"+userName+"', '"+passWord+"', '')");
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
    @FXML
    private Label nameLabel;
    @FXML
    private Button searchButton;
    @FXML
    private ChoiceBox<String> stateBox = new ChoiceBox();
    String[] states = {"AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY" };
    @FXML
    private Label stateLabel;
    @FXML
    private ChoiceBox<String> academicBox = new ChoiceBox();
    String[] majors = {"Agriculture", "Biology", "Business", "Construction", "Communication Studies", "Computer Science",
    		"Criminal Justice", "Culinary Studies", "Education", "Engineering", "Linguistics", "Gender Studies", "Health", "History",
    		"Humanities", "Law", "Mathematics", "Philosophy", "Psychology", "Physics", "Social Sciences", "Transportation"};
   
    @FXML
    private Slider acceptanceSlider;
    @FXML
    private CheckBox largeBox = new CheckBox();
    @FXML
    private CheckBox medBox = new CheckBox();
    @FXML
    private Slider satSlider;
    @FXML
    private AnchorPane search;
    @FXML
    private CheckBox smallBox = new CheckBox();
    String[] types = {"Public", "Private for-profit", "Private nonprofit"};
    @FXML
    private ChoiceBox<String> typeBox = new ChoiceBox();
    @FXML
    private Slider transferSlider;
    // Menu Elements
    @FXML 
	private MenuBar menuBar = new MenuBar();
	@FXML
	private Menu accounMenu;
	@FXML 
	private Menu homeMenu;
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
    @Override
    // Init
	public void initialize(URL arg0, ResourceBundle arg1){
    	stateBox.getItems().addAll(states);
    	academicBox.getItems().addAll(majors);
    	typeBox.getItems().addAll(types);
    	displaySchoolData();
    	displayUserData();
    	displayFavorites();
    	
    	
	}
    // Display data methods
    private void displayFavorites() {
    	if (user!=null&&resultsList!=null) {
    		try {
    			connection = ConnectionUtilities.getConnection("Users.sqlite");
    			statement = connection.createStatement();
    			statement.setQueryTimeout(30);
    			ResultSet rs = statement.executeQuery("SELECT * FROM Users WHERE UserName ='"+user.getCurrentUserName()+"'");
    			//Display data
    			if (rs.getString("Favorites")!=null) {
    				String[] array = rs.getString("Favorites").split(",");
        			for (int i = 0;i<array.length;i++) {
        				favoritesList.getItems().add(array[i]);
        			}
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} finally {
    			ConnectionUtilities.closeConnection(connection);
    		} 
    	} 
	}
	private void displayUserData() {
    	if(user!=null) {
    		currentFirst.setText(user.getCurrentFirstName());
    		currentLast.setText(user.getCurrentLastName());
    		currentEmail.setText(user.getCurrentEmail());
    		currentUsername.setText(user.getCurrentUserName());
    		menuBar.setVisible(true);
    		menuBar.setDisable(false);
    	} else {
    		menuBar.setVisible(false);
    		menuBar.setDisable(true);
    	}
	}

	// Show data from School
    public void displaySchoolData() {
    	if (user!=null&&resultsList!=null&&user.getCurrentSchool()!=null) {
    		schoolNameLabel.setText(user.getCurrentSchool());
    		try {
    			// establish a connection
    			connection = ConnectionUtilities.getConnection("MyDB.sqlite");
    			// create a statement object from the connection
    			statement = connection.createStatement();
    			statement.setQueryTimeout(30);
    			ResultSet rs = statement.executeQuery("SELECT * FROM Schools WHERE Name ='"+user.getCurrentSchool()+"'");
    			//Display data
    			idField.setText(rs.getString("ID"));
    			locationField.setText(rs.getString("City") + ", " + rs.getString("State"));
    			zipField.setText(rs.getString("Zip"));
    			admissionField.setText(rs.getString("AdmissionRate"));
    			transferField.setText(rs.getString("TransferRate"));
    			inField.setText(rs.getString("InState"));
    			outField.setText(rs.getString("OutState"));
    			urlField.setText(rs.getString("URL"));
    			urlField.setTextFill(Color.color(0, 0, 1));
    			satField.setText(rs.getString("SAT"));
    			populationField.setText(rs.getString("Population"));
    			String[] array = rs.getString("Programs").split(",");
    			for (int i = 0;i<array.length;i++) {
    				degreesList.getItems().add(array[i]);
    				
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} finally {
    			ConnectionUtilities.closeConnection(connection);
    		}
    	}
    }
    @FXML
    void search(ActionEvent event) throws IOException, SQLException {
    	// Get name or state
    	String name = nameField.getText();//DONE
    	String state  = stateBox.getValue();//DONE
    	// Sliders
    	double admission = acceptanceSlider.getValue();
    	double transfer = transferSlider.getValue();
    	double sat = satSlider.getValue();
    	// Major
    	String major = academicBox.getValue();
    	// Checkboxes
    	Boolean small = smallBox.isSelected();//DONE
    	Boolean med = medBox.isSelected();//DONE
    	Boolean large = largeBox.isSelected();//DONE
    	String type = typeBox.getValue();
  	  	displaySchool(name, state, admission, transfer, sat, major, small, med, large, type);
    }
    //This is where the magic happens
   	public void displaySchool(String name, String state, double admission, double transfer, double sat, String major, Boolean small, Boolean med, Boolean large, String type) throws SQLException {
   		resultsList.getItems().clear();
   		ResultSet rs = null;
   		String query = "";
   		int maxSize = getMaxPopulation(small, med, large);
   		int minSize = getMinPopulation(small, med, large);
		String nameQuery = "Name LIKE '%"+name+"%'";
		String stateQuery = "State = '"+state+"'";
		String admissionQuery = "AdmissionRate > '"+(admission)+"'";
		String transferQuery = "TransferRate > '"+(transfer)+"'";
		String satQuery = "SAT BETWEEN '"+(sat-100)+"' AND '"+(sat+100)+"'";
		String majorQuery = "Programs LIKE '%"+major+"%'";
		String sizeQuery = "Population BETWEEN '"+minSize+"' AND '"+maxSize+"'";
		String typeQuery = "Ownership = '"+type+"'";
   		try {
   			connection = ConnectionUtilities.getConnection("MyDB.sqlite");
   			statement = connection.createStatement();
   			statement.setQueryTimeout(30);
   			
   			// If no specific data has been inserted 
   			if (name.isEmpty()&&state==null&&admission==100&&transfer==100&&sat==1000&&major==null&&type==null&&!small&&!med&&!large) {
   				resultsFoundLabel.setText("Enter search inputs before continuing");
   			}
   			if (!name.isEmpty()) {
   				query += nameQuery + " AND ";
   			}
   			if (state!=null) {
   				query +=  stateQuery + " AND ";
   			} 
   			if (sat!=1000) {
   				query +=  satQuery + " AND ";
   			}
   			if (major!=null) {
   				query += majorQuery + " AND ";
   			}
   			if (type!=null) {
   				query += typeQuery + " AND ";
   			}
   			
   		} catch (SQLException e) {
   			e.printStackTrace();
   		} finally {
   			if (!query.isEmpty()) {
   				rs = statement.executeQuery("SELECT * FROM Schools WHERE " + query + admissionQuery + " AND " + transferQuery + " AND " + sizeQuery);
   				addToResultsFound(rs);
   			}
   			ConnectionUtilities.closeConnection(connection);
   			
   		}
   	}
    private void addToResultsFound(ResultSet rs) throws SQLException {
    	int resultsFound = 0;
    	while(rs.next()) {
				resultsList.getItems().add(rs.getString("Name"));
				resultsFound++;
		}
    	resultsFoundLabel.setText("Results Found: " + resultsFound);
    }
   	
    private int getMaxPopulation(Boolean small, Boolean med, Boolean large) {
    	if (med&&!large) {
    		return 15000;
    	} else if (small&&!med&&!large) {
    		return 5000;
    	} else {
    		return 50000;
    	}
	}
    
    private int getMinPopulation(Boolean small, Boolean med, Boolean large) {
    	if (!small&&med) {
    		return 5000;
    	} else if (!small&&!med&&large) {
    		return 15000;
    	} else {
    		return 0;
    	}
	}
    

	@FXML
    void getResults(MouseEvent event) throws Exception {
    	try  {
    		this.school = resultsList.getSelectionModel().getSelectedItem().toString();
    		user.setCurrentSchool(school);
    		borderPane = FXMLLoader.load(getClass().getResource("home.fxml"));
        	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      	  	scene = new Scene(borderPane, 800, 800);
      	  	scene.getStylesheets().add(css);
      	  	stage.setScene(scene);
      	  	pane = FXMLLoader.load(getClass().getResource("/application/results.fxml"));
      	  	paneSwitch(pane);
    	} catch (NullPointerException e) {
    		System.out.println("No school has been selected ");
    	}
    	
    }
	// Results element
	@FXML
	private Label resultsFoundLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextArea detailsBox;
    @FXML
    private Button addToFavoritesButton;
    @FXML
    public Label schoolNameLabel = new Label();
    @FXML
    private Label admissionField = new Label();
    @FXML
    private ListView<String> degreesList = new ListView<String>();
    @FXML
    private Label idField = new Label();
    @FXML
    private Label inField = new Label();
    @FXML
    private Label locationField = new Label();
    @FXML
    private Label outField = new Label();
    @FXML
    private Label populationField = new Label();
    @FXML
    private Label satField = new Label();
    @FXML
    private Label transferField = new Label();
    @FXML
    private Label urlField = new Label();
    @FXML
    private Label zipField = new Label();
    // Favorites Elements 
    @FXML
    private ListView<String> favoritesList = new ListView<String>();
    @FXML
    void addToFavorites(ActionEvent event) {
    	String fav = schoolNameLabel.getText();
    	String prev;
    	try {
    		connection = ConnectionUtilities.getConnection("Users.sqlite");
			// create a statement object from the connection
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			String favQuery;
			PreparedStatement ps = null;
			ResultSet rs = statement.executeQuery("SELECT * FROM Users WHERE UserName = '"+user.getCurrentUserName()+"'");
			favQuery = "UPDATE Users SET Favorites = ? WHERE UserName = ? ";
			if (rs.getString("Favorites").isBlank() && !rs.getString("Favorites").contains(fav)) {
    			ps = connection.prepareStatement(favQuery);
    			ps.setString(1, fav);
    			ps.setString(2, user.getCurrentUserName());
    			ps.execute();
        		System.out.println(fav);
        	}  else if (!rs.getString("Favorites").isBlank() && !rs.getString("Favorites").contains(fav)) {
    			prev = rs.getString("Favorites");
        		String newFav = prev + ", " + fav;
        		ps = connection.prepareStatement(favQuery);
    			ps.setString(1, newFav);
    			ps.setString(2, user.getCurrentUserName());
    			ps.execute();
        		System.out.println(fav);
        	} else {
        		System.out.println("This school has already been made a favorite of yours.");
        	}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtilities.closeConnection(connection);
		}
    }
  
    // Account elements
	@FXML
	private Label currentEmail = new Label();
	@FXML
    private Label currentFirst = new Label();
    @FXML
    private Label currentLast = new Label();
    @FXML
    private Label currentUsername = new Label();
    // Edit elements
    @FXML
    private Button deleteButton;
    @FXML
    private PasswordField confirmNewPassword = new PasswordField();
    @FXML
    private Button editButton;
    @FXML
    private TextField newEmail = new TextField();
    @FXML
    private PasswordField newPassword = new PasswordField();
    @FXML
    private TextField newUsername = new TextField();
    @FXML
    private Label editLabel = new Label();
    @FXML
    private PasswordField oldPassword = new PasswordField();
    @FXML
    void delete(ActionEvent event) throws IOException  {
    	ButtonType confirm = new ButtonType("Yes, I am sure");
    	ButtonType cancel = new ButtonType("NO!!!");
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Account Deletion Confirmation");
    	alert.setHeaderText("Wait, this action is irreversible!!!");
    	alert.setContentText("Do you really want to delete this account?");
    	alert.getButtonTypes().remove(ButtonType.OK);
    	alert.getButtonTypes().remove(ButtonType.CANCEL);
    	alert.getButtonTypes().addAll(confirm, cancel);
    	alert.showAndWait();
    	if (alert.getResult() == confirm) {
    		try {
				connection = ConnectionUtilities.getConnection("Users.sqlite");
				statement = connection.createStatement();
				statement.setQueryTimeout(30);
				ResultSet rs = statement.executeQuery("DELETE FROM Users WHERE UserName = '"+user.getCurrentUserName()+"'");
			} catch (SQLException e) {
				System.out.println("");
			} finally {
				logOut(event);
				System.out.println("Account has been deleted successfully");
			}
    		
		}
    }
    @FXML
    void edit() throws SQLException, NullPointerException {
    	//Get variables
    	String newName = newUsername.getText().toString().trim();
    	String newMail = newEmail.getText().toString().trim();
    	String oldWord = oldPassword.getText().toString().trim();
    	String newWord = newPassword.getText().toString().trim();
    	String confirm = confirmNewPassword.getText().toString().trim();
    	//Connect if passwords match
    	connection = ConnectionUtilities.getConnection("Users.sqlite");
    	PreparedStatement ps = null;
    	
    	if (!oldWord.equals(user.getCurrentPassword().toString())) {
    		editLabel.setText("Old password entered incorrectly");
    		editLabel.setTextFill(Color.color(1, 0, 0));
    	}
    	else if (!newWord.equals(confirm)) {
    		editLabel.setText("New passwords do not match");
    		editLabel.setTextFill(Color.color(1, 0, 0));
    	}
    	else  {
    		String sql = ("UPDATE Users SET UserName = ?, email = ?, Password = ? WHERE UserName = ?");
        	ps = connection.prepareStatement(sql);
        	ps.setString(1, newName);
        	ps.setString(2, newMail);
        	ps.setString(3, newWord);
        	ps.setString(4, user.getCurrentUserName().toString());
        	ps.execute();
    		editLabel.setText("Success");
    		editLabel.setTextFill(Color.color(0, 1, 0));
    	}
    }
    
	@FXML
    void logOut(ActionEvent event) throws IOException {
		user = null;
		displayUserData();
		borderPane = FXMLLoader.load(getClass().getResource("home.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  	  	scene = new Scene(borderPane, 800, 800);
  	  	scene.getStylesheets().add(css);
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
		borderPane = FXMLLoader.load(getClass().getResource("home.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  	  	scene = new Scene(borderPane, 800, 800);
  	  	scene.getStylesheets().add(css);
  	  	stage.setScene(scene);
  	  	pane = FXMLLoader.load(getClass().getResource("/application/search.fxml"));
  	  	paneSwitch(pane);
	}
	@FXML
	void resultsFlip(ActionEvent event) throws IOException {
		pane = FXMLLoader.load(getClass().getResource("/application/results.fxml"));
  	  	paneSwitch(pane);
	}
	@FXML
	void detailsFlip(ActionEvent event) throws IOException {
		pane = FXMLLoader.load(getClass().getResource("/application/details.fxml"));
  	  	paneSwitch(pane);
	}
	@FXML
	void loginFlip(ActionEvent event) throws IOException {
		user = null;
		displayUserData();
		pane = FXMLLoader.load(getClass().getResource("/application/login.fxml"));
  	  	paneSwitch(pane);
	}
	@FXML
	public void paneSwitch(Pane pane)  {
		borderPane.setCenter(pane);
	}

}
