package com.ryan.jray.network.packet;

import com.ryan.jray.utils.Vector2;

public class PacketSetPlayer extends Packet {
	public Vector2 position;
	public double rotation;
	public int health;
	public PacketSetPlayer(Vector2 pos,double rot,int h) {
		this.rotation = rot;
		this.position = pos;
		this.health = h;
	}
}
