package com.cjburkey.shared.installer.system.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class ParentScene extends Scene {

	public ParentScene(Parent root) {
		super(root);
		this.getStylesheets().clear();
		this.getStylesheets().add("css/style.css");
	}
	
}