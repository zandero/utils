package com.zandero.utils;

import java.io.*;

public class StreamUtils {

	private StreamUtils() {
		// hiding constructor
	}

	public static String getString(final InputStream is) {
		return getString(is, 1000);
	}

	public static String getString(final InputStream is, final int bufferSize) {
		return getString(is, bufferSize, EncodeUtils.UTF_8);
	}

	public static String getString(final InputStream is, final int bufferSize, String encoding) {
		if (is == null) {
			return null;
		}
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		try {
			try (Reader in = new InputStreamReader(is, encoding)) {
				for (; ; ) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0) {
						break;
					}
					out.append(buffer, 0, rsz);
				}
			}
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return out.toString();
	}

	public static byte[] getBytes(InputStream is) {

		return getBytes(is, 100000);
	}

	public static byte[] getBytes(final InputStream is, final int bufferSize) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[bufferSize];
		try {

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
		} catch (IOException ignored) {
		}

		return buffer.toByteArray();
	}
}
