package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	File file = new File("deneme.txt");
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			Huffman h = new Huffman(file);
			h.operate();
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
