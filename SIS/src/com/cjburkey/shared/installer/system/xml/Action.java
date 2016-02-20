package com.cjburkey.shared.installer.system.xml;

public class Action {
	
	public String url;
	public String place;
	public ActionTypes type;
	
	public Action() {  }
	
	public Action(String url, String place, ActionTypes type) {
		this.url = url;
		this.place = place;
		this.type = type;
	}
	
	public static enum ActionTypes {
		download,
		extract
	}
	
}