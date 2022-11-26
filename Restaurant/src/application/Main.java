package application;
	
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;


public class Main extends Application implements Initializable{
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
	String[] dishes = {"Bowl", "Burrito", "Enchiladas", "Gorditas", "Quesadilla", "Torta"};
	String[] meats = {"NoMeat", "Barbacoa", "Beef", "Birria", "Chicken", "Fish", "Pork", "Shrimp", "Steak"};
	@FXML
    private Button addToListBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField customerField;
    @FXML
    private ChoiceBox<String> dishBox;
    @FXML
    private Label dishLabel;
    @FXML
    private Button finalizeBtn;
    @FXML
    private ChoiceBox<String> meatBox;
    @FXML
    private Label meatLabel;
    @FXML
    private Rectangle orderList;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dishBox.getItems().addAll(dishes);
		meatBox.getItems().addAll(meats);
	}
}
