package application;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;


public class FileNodeTree extends Application {
	static StringBuilder appendedString = new StringBuilder();

	static Text text = new Text("Choose an action:");

	@Override
	public void start(Stage primaryStage) throws Exception {

		//Creates scene
		Button button1 = new Button("Get User Local Directory");
		Button button2 = new Button("Get User Home Directory");
		Button button3 = new Button("Get Java Directory");
		Button button4 = new Button("See Java Version");
		Button button5 = new Button("See Current OS");
		
		text.setFont(new Font(14));

		GridPane layout = new GridPane();
		layout.setVgap(5);
		layout.setHgap(5);
		layout.setAlignment(Pos.CENTER);
		layout.add(button1, 0, 1);
		layout.add(button2, 0, 2);
		layout.add(button3, 0, 3);
		layout.add(button4, 0, 4);
		layout.add(button5, 0, 5);
		layout.add(text, 0, 0);

		Scene scene = new Scene(layout, 400, 200);
		primaryStage.setTitle("File Tree Platform");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// =============================================EVENT HANDLER TO GET LOCAL DIRECTORY=====================================================//

		EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				StringBuilder returned = null;
				Path currentPath = Paths.get(System.getProperty("user.dir"));
				
				try {
					 returned = fileTree(currentPath, 0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				HBox hbox = new HBox();
				hbox.setAlignment(Pos.CENTER);
				
				Text text = new Text(returned.toString());
				Font myFont = new Font("Tahoma", 16);
				text.setFont(myFont);
				hbox.getChildren().add(text);
				
				primaryStage.setTitle("Home Directory");
				primaryStage.setScene(new Scene((hbox),600,600));
				primaryStage.show();
				
			}
		};
		button1.addEventFilter(ActionEvent.ACTION, eventHandler);

		// =============================================EVENT HANDLER TO GET HOME DIRECTORY=====================================================//

		EventHandler<ActionEvent> eventHandler2 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				System.out.println(System.getProperty("user.home"));

				HBox hbox = new HBox();
				hbox.setAlignment(Pos.CENTER);
				
				Text text = new Text(System.getProperty("user.home"));
				Font myFont = new Font("Tahoma", 16);
				text.setFont(myFont);
				hbox.getChildren().add(text);
				
				primaryStage.setTitle("Home Directory");
				primaryStage.setScene(new Scene((hbox),400,200));
				primaryStage.show();
				
			}
		};
		button2.addEventFilter(ActionEvent.ACTION, eventHandler2);

		
		// =============================================EVENT HANDLER TO GET JAVA DIRECTORY=====================================================//

		EventHandler<ActionEvent> eventHandler3 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				StringBuilder returned = null;
				Path currentPath = Paths.get(System.getProperty("java.home"));
				
				try {
					 returned = fileTree(currentPath, 0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ScrollBar sc = new ScrollBar();
				sc.setOrientation(Orientation.VERTICAL);
				
				HBox hbox = new HBox();
				hbox.setAlignment(Pos.CENTER);
				
				Text text = new Text(returned.toString());
				Font myFont = new Font("Tahoma", 16);
				text.setFont(myFont);
				hbox.getChildren().addAll(text, sc);
				
				primaryStage.setTitle("Home Directory");
				primaryStage.setScene(new Scene((hbox),700,2000));
				primaryStage.show();
				
			}
		};
		button3.addEventFilter(ActionEvent.ACTION, eventHandler3);
		
		
		// =============================================EVENT HANDLER TO JAVA VERSION=====================================================//

		EventHandler<ActionEvent> eventHandler4 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				System.out.println(System.getProperty("java.version"));
				
				HBox hbox = new HBox();
				hbox.setAlignment(Pos.CENTER);
				
				Text text = new Text(System.getProperty("java.version"));
				Font myFont = new Font("Tahoma", 16);
				text.setFont(myFont);
				hbox.getChildren().add(text);
				
				primaryStage.setTitle("Home Directory");
				primaryStage.setScene(new Scene((hbox),400,200));
				primaryStage.show();
				
			}
		};
		button4.addEventFilter(ActionEvent.ACTION, eventHandler4);
		
		// =============================================EVENT HANDLER TO GET OPERATING SYSTEM=====================================================//

		EventHandler<ActionEvent> eventHandler5 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				System.out.println(System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + System.getProperty("os.arch"));
				
				HBox hbox = new HBox();
				hbox.setAlignment(Pos.CENTER);
				
				Text text = new Text(System.getProperty("os.name") + " " + System.getProperty("os.version")+ " " + System.getProperty("os.arch"));
				Font myFont = new Font("Tahoma", 16);
				text.setFont(myFont);
				hbox.getChildren().add(text);
				
				primaryStage.setTitle("Home Directory");
				primaryStage.setScene(new Scene((hbox),400,200));
				primaryStage.show();
				
			}
		};
		button5.addEventFilter(ActionEvent.ACTION, eventHandler5);


	}
	
	
	// filTree receives path and iterates through directory
	private static StringBuilder fileTree(Path path, int indentAmount) throws Exception {
		
		BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
		StringBuilder indent = new StringBuilder();

		
		// If directory filetree() method is called as recursion
		if(attr.isDirectory()) {
			
			DirectoryStream<Path> pathStream = Files.newDirectoryStream(path);
	
			for(int i=0; i < indentAmount;i++) {
				indent.append("  ");
			}
			
			long fileSize = Files.size(path); 
			System.out.println(indent.toString() + "*" + path.getFileName() + " - ("+ fileSize +" bytes)");
			appendedString.append(indent.toString() + "*" +path.getFileName() + " - ("+ fileSize +" bytes)"+'\n');
			
			for(Path currentPath: pathStream) {
				
				fileTree(currentPath, indentAmount+1);
				
			}
		} else {
			 
			 for(int i=0; i < indentAmount;i++) {
					indent.append("  ");
			 }
			 long fileSize = Files.size(path); 
			 System.out.println(indent.toString() + " --- " + path.getFileName() + " - ("+ fileSize +" bytes)");
			 appendedString.append(indent.toString() + " --- " +path.getFileName() + " - ("+ fileSize +" bytes)"+'\n');
		}
		
		// Built string is sent back to event handler
		return appendedString;
		
	}
	

	public static void main(String[] args) throws FileNotFoundException {
		launch(args);
	}
	
}
