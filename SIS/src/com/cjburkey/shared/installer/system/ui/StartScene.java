package com.cjburkey.shared.installer.system.ui;

import com.cjburkey.shared.installer.system.util.Stop;
import com.cjburkey.shared.installer.system.util.Text;
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

public class StartScene extends ParentScene {
	
	public StartScene(Parent root) {
		super(root);
	}
	
	public final void init(Stage s, VBox root) {
		HBox buttons = new HBox();
		String txt = "The following installer will download and extract the necessary components to install your application.  Click the 'Next' button to continue or the 'Cancel' button to stop the installation process.";
		Label text = new Label(Text.wrap(txt, 50));
		Button next = new Button("Next");
		Button cancel = new Button("Cancel");
		
		text.setWrapText(true);
		
		buttons.getChildren().addAll(next, cancel);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		buttons.setSpacing(10);
		buttons.setPadding(new Insets(10d));
		
		root.setSpacing(10d);
		root.setPadding(new Insets(10d));
		root.getChildren().addAll(text, buttons);
		
		s.setScene(this);
		s.sizeToScene();
		s.centerOnScreen();
		
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Stop.stop();
				return;
			}
		});
		
		next.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				VBox r = new VBox();
				ListScene listScene = new ListScene(r);
				listScene.init(s, r);
			}
		});
	}
	
}