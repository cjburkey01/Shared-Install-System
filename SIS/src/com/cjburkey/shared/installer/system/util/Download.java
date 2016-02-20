package com.cjburkey.shared.installer.system.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.concurrent.Task;

public class Download {
	
	public static final Task<Void> startDownload(URL url, String to) {
		return new Task<Void>() {
			public Void call() throws Exception {
				File f = new File(to);
				if(!f.exists()) {
					if(!f.getParentFile().exists()) {
						f.getParentFile().mkdirs();
					}
					f.createNewFile();
				}
				HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
				long completeFileSize = httpConnection.getContentLength();
				BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
				FileOutputStream fos = new FileOutputStream(to);
				BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
				byte[] data = new byte[1024];
				long downloadedFileSize = 0;
				int x = 0;
				while((x = in.read(data, 0, 1024)) >= 0) {
					downloadedFileSize += x;
					final int currentProgress = (int) ((((double)downloadedFileSize) / ((double)completeFileSize)) * 100d);
					bout.write(data, 0, x);
					if(isCancelled()) {
						f.delete();
						break;
					}
					updateProgress(currentProgress, 100);
				}
				bout.close();
				in.close();
				Log.print("Done with: '" + url + "'");
				return null;
			}
		};
	}
	
}