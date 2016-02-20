package com.cjburkey.shared.installer.system.util;

import javafx.application.Platform;

public class Stop {
	
	public static final void stop() {
		Log.print("Closing application...");
		Platform.exit();
		return;
	}
	
	public static final void force(int code) {
		Log.print("Force closing application!");
		Log.print("Close Code: " + code);
		System.exit(code);
		return;
	}
	
}