package com.cjburkey.shared.installer.system.ui;

import com.cjburkey.shared.installer.system.util.Log;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class InstallerWindow {
	
	private static boolean op = false;
	
	public static final void open(Stage s) {
		if(op) { Log.print("Window already open?"); return; }
		op = true;
		
		VBox root = new VBox();
		StartScene start = new StartScene(root);
		start.init(s, root);
		
		s.initStyle(StageStyle.UNDECORATED);
		s.setScene(start);
		s.sizeToScene();
		s.centerOnScreen();
		s.setTitle("Installer Window");
		s.setResizable(false);
		s.show();
		
		s.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent e) {
				e.consume();
			}
		});
	}
	
}