package com.ryan.jray.network.packet;

import com.ryan.jray.entity.Entity;

public class PacketEntity extends Packet {
	public Entity entity;

	public PacketEntity(Entity ent) {
		this.entity = ent;
	}

	public PacketEntity(Entity ent, String from) {
		this.entity = ent;
		this.from = from;
	}

}
