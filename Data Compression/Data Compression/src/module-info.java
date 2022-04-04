module Main {
	requires javafx.controls;
	requires javafx.fxml;
	requires jdk.compiler;
	
	opens application to javafx.graphics, javafx.fxml;
}
