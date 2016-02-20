package com.cjburkey.shared.installer.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import com.cjburkey.shared.installer.system.ui.InstallerWindow;
import com.cjburkey.shared.installer.system.util.Download;
import com.cjburkey.shared.installer.system.util.Log;
import com.cjburkey.shared.installer.system.util.Stop;
import com.cjburkey.shared.installer.system.xml.Action;
import com.cjburkey.shared.installer.system.xml.XmlFunc;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Installer extends Application {
	
	private static int projid = -1;
	private static String xml = "";
	
	private static Stage stage = null;
	
	public static void main(String[] args) {
		Log.print("Installer starting...");
		Log.print("Checking arguments...");
		if(args.length < 1) { Log.print("No application found.");  Stop.stop(); return; }
		try {
			projid = Integer.parseInt(args[0]);
		} catch(Exception e) {
			Log.print("No real application found.");
			Stop.stop();
			return;
		}
		getDir(projid).mkdirs();
		xml = getDir(projid) + "/" + projid + ".xml";
		try {
			File info = new File(getDir(-1), "/" + projid + ".info");
			Task<Void> t1 = Download.startDownload(new URL("http://cjburkey.com/sis/get.php?id=" + projid), info.getPath());
			t1.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				public void handle(WorkerStateEvent e) {
					try {
						BufferedReader r = new BufferedReader(new FileReader(info));
						String url = r.readLine();
						if(url == null) {
							Log.print("Couldn't find.");
							Stop.stop();
							r.close();
							return;
						}
						r.close();
						Task<Void> t2 = Download.startDownload(new URL(url), xml);
						t2.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
							public void handle(WorkerStateEvent e) {
								ArrayList<Action> actions = XmlFunc.getTasks();
								for(Action a : actions) {
									Log.print(a.url + " - " + a.place + " - " + a.type);
								}
								InstallerWindow.open(stage);
							}
						});
						new Thread(t2).start();
					} catch (Exception e1) {
						e1.printStackTrace();
						Stop.stop();
						return;
					}
				}
			});
			new Thread(t1).start();
		} catch (Exception e) {
			e.printStackTrace();
			Stop.stop();
			return;
		}
		launch(args);
	}
	
	public void start(Stage s) throws Exception {
		Log.print("Window starting...");
		stage = s;
	}
	
	public void stop() {
		Log.print("Recieved close message.");
		Log.print("Closing now...");
	}
	
	public static final File getDir(int id) {
		return new File(System.getProperty("user.home") + "/SIS/" + id + "/");
	}
	
	public static final File getXML(int id) {
		return new File(getDir(id) + "/" + id + ".xml");
	}
	
	public static final int getProjectID() { return projid; }
	
}