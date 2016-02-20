package com.cjburkey.shared.installer.system.ui;

import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.Stack;
import com.cjburkey.shared.installer.system.util.Download;
import com.cjburkey.shared.installer.system.util.Log;
import com.cjburkey.shared.installer.system.xml.Action;
import com.cjburkey.shared.installer.system.xml.XmlFunc;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstallScene extends ParentScene {
	
	public InstallScene(Parent root) {
		super(root);
	}
	
	boolean worked = false;
	
	public final void init(Stage s, VBox root) {
		HBox buttons = new HBox();
		Button done = new Button("Finish");
		
		done.setDisable(true);
		
		VBox progBars = new VBox();
		ProgressBar total = new ProgressBar();
		ProgressBar current = new ProgressBar();
		
		total.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().width / 2);
		current.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().width / 2);
		
		buttons.getChildren().addAll(done);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		buttons.setSpacing(10);
		buttons.setPadding(new Insets(10d));
		
		progBars.getChildren().addAll(total, current);
		progBars.setAlignment(Pos.CENTER);
		progBars.setSpacing(10);
		progBars.setPadding(new Insets(10d));
		
		root.setSpacing(10d);
		root.setPadding(new Insets(10d));
		root.getChildren().addAll(progBars, buttons);
		
		s.setScene(this);
		s.sizeToScene();
		s.centerOnScreen();
		
		done.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				VBox r = new VBox();
				FinishedScene finishScene = new FinishedScene(r);
				finishScene.init(s, r, worked);
			}
		});
		
		startDownloads(current, total, done);
	}
	
	int i;
	
	public final void startDownloads(ProgressBar current, ProgressBar total, Button done) {
		i = 0;
		Stack<Action> actions = new Stack<Action>();
		actions.addAll(XmlFunc.getTasks());
		doIt(actions.pop(), current, total, done, actions);
	}
	
	public final void doIt(Action a, ProgressBar current, ProgressBar total, Button done, Stack<Action> stack) {
		try {
			Task<Void> task = Download.startDownload(new URL(a.url), a.place);
			current.progressProperty().bind(task.progressProperty());
			task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				public void handle(WorkerStateEvent e) {
					i ++;
					total.setProgress((float) i / (float) XmlFunc.getTasks().size());
					if(i == XmlFunc.getTasks().size()) {
						done.setDisable(false);
						Log.print("Done!");
						worked = true;
					} else { doIt(stack.pop(), current, total, done, stack); }
				}
			});
			task.setOnFailed(new EventHandler<WorkerStateEvent>() {
				public void handle(WorkerStateEvent e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Error");
					a.setContentText("There was an error downloading the files.");
					a.show();
					for(Action ac : XmlFunc.getTasks()) {
						File f = new File(ac.place);
						if(f.exists()) {
							f.delete();
						}
					}
					done.setDisable(false);
				}
			});
			new Thread(task).start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}