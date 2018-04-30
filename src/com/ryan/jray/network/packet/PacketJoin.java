package com.ryan.jray.network.packet;

public class PacketJoin extends Packet {
	public PacketJoin(String username) {
		this.from = username;
	}
}
