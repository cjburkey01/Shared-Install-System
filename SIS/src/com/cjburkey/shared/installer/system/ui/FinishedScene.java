package com.cjburkey.shared.installer.system.ui;

import com.cjburkey.shared.installer.system.util.Stop;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FinishedScene extends ParentScene {
	
	public FinishedScene(Parent root) {
		super(root);
	}
	
	public final void init(Stage s, VBox root, boolean worked) {
		HBox buttons = new HBox();
		String txt;
		if(worked) {
			txt = "The application was installed successfully!";
		} else {
			txt = "There was an error downloading your application.  Please try again.";
		}
		Label text = new Label(txt);
		Button close = new Button("Close");
		
		text.setWrapText(true);
		
		buttons.getChildren().addAll(close);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		buttons.setSpacing(10);
		buttons.setPadding(new Insets(10d));
		
		root.setSpacing(10d);
		root.setPadding(new Insets(10d));
		root.getChildren().addAll(text, buttons);
		
		s.setScene(this);
		s.sizeToScene();
		s.centerOnScreen();
		
		close.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Stop.stop();
				return;
			}
		});
	}
	
}