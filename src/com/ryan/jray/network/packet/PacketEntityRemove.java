package com.ryan.jray.network.packet;

public class PacketEntityRemove extends Packet{
	public int EntityID;
	public PacketEntityRemove(int id) {
		this.EntityID = id;
	}
}
