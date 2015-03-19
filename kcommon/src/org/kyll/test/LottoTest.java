package org.kyll.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

public class LottoTest {
	public static void main(String[] args) throws Exception {
		Set<String> set = new TreeSet<String>();

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("e:\\data"))));
		String line = null;
		while ((line = in.readLine()) != null) {
			if (!set.add(line.trim())) {
				System.out.println(line);
			}
		}
		in.close();

		System.out.println(set.size());
		System.out.println(set);

		for (int i = 0; i < 1000; i++) {
			String s = "00" + String.valueOf(i);
			s = (s.substring(s.length() - 3));
			if (set.add(s)) {
				System.out.println(s);
			}
		}
	}
}
