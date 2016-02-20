package com.cjburkey.shared.installer.system.xml;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.cjburkey.shared.installer.system.Installer;
import com.cjburkey.shared.installer.system.util.Log;
import com.cjburkey.shared.installer.system.util.Stop;
import com.cjburkey.shared.installer.system.xml.Action.ActionTypes;

public class XmlFunc {
	
	private static ArrayList<Action> tasks = null;
	
	public static final ArrayList<Action> getTasks() {
		if(tasks == null) {
			int id = Installer.getProjectID();
			tasks = getActions(id);
			for(Action a : tasks) {
				File dir = Installer.getDir(id);
				a.place = new File(dir, a.place).getPath();
			}
		}
		return tasks;
	}
	
	private static final ArrayList<Action> getActions(int id) {
		ArrayList<Action> done = new ArrayList<Action>();
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(Installer.getXML(id));
			NodeList actionList = doc.getElementsByTagName("action");
			for(int i = 0; i < actionList.getLength(); i ++) {
				Node node = actionList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element action = (Element) node;
					ActionTypes type = ActionTypes.valueOf(action.getAttribute("type"));
					String url = action.getAttribute("url");
					String place = action.getAttribute("save");
					done.add(new Action(url, place, type));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			Stop.stop();
		}
		
		Log.print("Loaded XML");
		return done;
	}
	
}