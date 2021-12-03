package com.butbetter.storage;

import java.io.*;

public class FileUtilities {
	/**
	 * method to read an entire file unsafely and give back the contents
	 *
	 * WARNING: DON'T USE THIS OUTSIDE OF TESTS
	 *
	 * @param file file handle to read from
	 * @return String content
	 * @throws IOException thrown, if file couldn't be read
	 */
	public static String readEntireFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		StringBuilder sb = new StringBuilder();
		while (br.ready()) {
			sb.append(br.readLine());
		}
		return sb.toString();
	}
}
