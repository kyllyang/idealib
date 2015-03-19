package org.kyll.idea.busi.metdl.handler;

public class DownloadHandlerFactory {
	public static DownloadHandler createDownloadHandler(String url) {
		DownloadHandler downloadHandler = null;
		if (url.startsWith("http://oron.com/")) {
			downloadHandler = new OronDownloadHandler();
		}
		if (downloadHandler != null) {
			downloadHandler.setUrlStr(url);
		}
		return downloadHandler;
	}
}
