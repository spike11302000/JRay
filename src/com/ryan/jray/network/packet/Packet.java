package com.ryan.jray.network.packet;

import java.io.Serializable;

public class Packet implements Serializable{
	public String from;
	
	public Packet(String from) {
		this.from = from;
	}
}
