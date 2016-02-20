package com.cjburkey.shared.installer.system.ui;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ProgressIndicator extends StackPane {
	
	final private ProgressBar bar = new ProgressBar();
	final private Text text = new Text("Loading...");
	
	public ProgressIndicator(int width) {
		
		this.bar.setMinWidth(width);
		this.bar.setMaxWidth(width);
		this.bar.setPrefWidth(width);
		
		this.getChildren().addAll(bar, text);
	}
	
	public final ProgressBar getProgBar() { return this.bar; }
	public final String getText() { return this.text.getText(); }
	public final void setText(String txt) { this.text.setText(txt); }
	
}