package com.ryan.jray.utils;

public class Logger {
	public static int GAME = 0;
	public static int SERVER = 1;
	public static int CLIENT = 2;
	public static int SYSTEM = 3;
	public static int DEBUG = -1;
	
	public static void print(String str, int type) {
		System.out.print(getType(type) + " " + str);
	}

	public static void println(String str, int type) {
		System.out.println(getType(type) + " " + str);
	}

	public static String getType(int type) {
		switch (type) {
		case -1:
			return "[DEBUG]";
		case 0:
			return "[Game]";
		case 1:
			return "[Server]";
		case 2:
			return "[Client]";
		case 3:
			return "[System]";
		}
		return "[Unknown]";
	}
}
