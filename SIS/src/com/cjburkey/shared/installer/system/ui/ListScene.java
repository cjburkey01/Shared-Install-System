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

public class ListScene extends ParentScene {
	
	public ListScene(Parent root) {
		super(root);
	}
	
	public final void init(Stage s, VBox root) {
		HBox buttons = new HBox();
		String txt = "Once you click the 'Next' button, do not stop the installer.  If you do, your files may become corrupted.";
		Label text = new Label(Text.wrap(txt, 50));
		Button next = new Button("Next");
		Button cancel = new Button("Cancel");
		
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
				InstallScene installScene = new InstallScene(r);
				installScene.init(s, r);
			}
		});
	}
	
}