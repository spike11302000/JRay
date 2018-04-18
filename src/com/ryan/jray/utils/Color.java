package com.ryan.jray.utils;

public class Color {
	public int r;
	public int g;
	public int b;
	
	public Color() {
		this.r = 0;
		this.g = 0;
		this.b = 0;
	}
	public Color(int red,int green,int blue) {
		this.r = red;
		this.g = green;
		this.b = blue;
	}
	public int toInt() {
		int rgb = this.r;
		rgb = (rgb << 8) + this.g;
		rgb = (rgb << 8) + this.b;
		return rgb;
	}
	public Color lerp(Color c1,Color c2,double f){
		int r = (int) MathUtils.lerp(c1.r, c2.r, f);
		int g = (int) MathUtils.lerp(c1.g, c2.g, f);
		int b = (int) MathUtils.lerp(c1.b, c2.b, f);
		return new Color(r,g,b);
	}
}
