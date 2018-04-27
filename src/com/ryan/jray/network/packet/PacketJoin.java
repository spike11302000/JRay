package com.ryan.jray.network.packet;

public class PacketJoin extends Packet {
	public String UserName;

	public PacketJoin(String username) {
		this.UserName = username;
	}
}
