package com.ryan.jray.utils;

public class MathUtils {
	public static int map(int x, int in_min, int in_max, int out_min, int out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	public static double lerp(double d1, double d2, double f) {
		return d1 + f * (d2 - d1);
	}
	
	public static int lerp(int i1, int i2, double f) {
		return (int) (i1 + f * (i2 - i1));
	}
}
