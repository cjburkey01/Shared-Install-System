package com.cjburkey.shared.installer.system.util;

public class Text {
	
	public static final String wrap(String txt, int wpl) {
		return txt.replaceAll("(.{1," + wpl + "})\\s+", "$1\n") + "\n";
	}
	
}