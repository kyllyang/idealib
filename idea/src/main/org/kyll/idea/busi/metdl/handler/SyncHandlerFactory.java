package org.kyll.idea.busi.metdl.handler;

public class SyncHandlerFactory {
	public static SyncHandler createSyncHandler(String url) {
		SyncHandler syncHandler = null;
		if (url.startsWith("picvideo")) {
			syncHandler = new PicvideoSyncHandler();
		} else if (url.startsWith("blogxpic")) {
			syncHandler = new BlogxpicSyncHandler();
		} else if (url.startsWith("yaoblue")) {
			syncHandler = new YaoblueSyncHandler();
		}
		return syncHandler;
	}
}
