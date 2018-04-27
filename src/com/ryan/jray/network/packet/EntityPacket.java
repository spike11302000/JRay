package com.ryan.jray.network.packet;

import com.ryan.jray.entity.Entity;

public class EntityPacket extends Packet {
	public Entity entity;
	public EntityPacket(Entity ent) {
		this.entity = ent;
	}

}
