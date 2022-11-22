package application;
	
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource("home.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("home.fxml").toExternalForm());
			primaryStage.setTitle("Ristorante");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	// FXML Elements
	@FXML 
	private Button addDishBtn;
	@FXML
	private Button beefBtn;
	@FXML
	private Button boiledBtn;
	@FXML
	private Button bowlBtn;
	@FXML
	private Button burritoBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button chickenBtn;
	@FXML
	private Button chimichangaBtn;
	@FXML
	private Label currentOrder;
	@FXML
	private TextField customerName;
	@FXML
	private Button enchiladaBtn;
	@FXML
	private Button finalizeBtn;
	@FXML
	private Button fishBtn;
	@FXML
	private Button friedBtn;
	@FXML
	private Button grilledBtn;
	@FXML
	private Button noMeatBtn;
	@FXML
	private ListView<?> orderList;
	@FXML
	private Button porkBtn;
	@FXML
	private Button quesadillaBtn;
	@FXML
	private Button steakBtn;
	@FXML
	private Button tortaBtn;
}
