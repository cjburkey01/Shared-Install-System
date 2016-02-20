package com.cjburkey.shared.installer.system.xml;

public class Runnable {
	
	public RunnableTypes os;
	public String file;
	
	public Runnable() {  }
	public Runnable(RunnableTypes os, String file) {
		this.os = os;
		this.file = file;
	}
	
	static enum RunnableTypes {
		win,
		mac,
		lin,
	}
	
}