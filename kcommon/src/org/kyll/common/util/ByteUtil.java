package org.kyll.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public final class ByteUtil {
	public static byte[] fromInputStream(InputStream in) {
		byte[] buffer = new byte[4096];
		int readLength;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			while ((readLength = in.read(buffer)) != -1) {
				out.write(buffer, 0, readLength);
			}

			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] fromHttp(String url) {
		byte[] buffer = new byte[4096];
		int readLength;
		InputStream in = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			URLConnection conn = new URL(url).openConnection();
			conn.setConnectTimeout(1000 * 60);
			in = conn.getInputStream();

			while ((readLength = in.read(buffer)) != -1) {
				out.write(buffer, 0, readLength);
			}

			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private ByteUtil() {
	}
}
