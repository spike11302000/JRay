package com.ryan.jray.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Config {
	private HashMap<String, String> config = new HashMap<String, String>();

	public Config(String path) {
		FileReader file = null;
		try {
			file = new FileReader(path);
		} catch (FileNotFoundException e) {
			System.out.println("could not find config: " + path);
			System.exit(0);
		}
		BufferedReader input = new BufferedReader(file);
		Object[] lines = input.lines().toArray();
		for (Object l : lines) {
			String line = (String) l;
			if (!line.equals("")) {
				String[] c = line.split("=");
				if (c.length > 2 || c.length < 2) {
					System.out.println("invalid line in config: " + path + ", " + line);
				} else
					this.config.put(c[0], c[1]);
			}
		}
	}

	public String getString(String name) {
		return this.config.get(name);
	}

	public int getInt(String name) {
		return Integer.parseInt(this.config.get(name));
	}

	public boolean getBoolean(String name) {
		return Boolean.parseBoolean(this.config.get(name));
	}

	public double getDouble(String name) {
		return Double.parseDouble(this.config.get(name));
	}

	public String toString() {
		String o = "";
		for (Map.Entry<String, String> entry : this.config.entrySet()) {
			o += entry.getKey() + ":" + entry.getValue() + "\n";
		}
		return o;
	}
}
