package com.ryan.jray.network.packet;

public class PacketMessage extends Packet {
	public String msg;

	public PacketMessage(String msg) {
		this.msg = msg;
	}

	public PacketMessage(String msg, String from) {
		this.msg = msg;
		this.from = from;
	}

}
