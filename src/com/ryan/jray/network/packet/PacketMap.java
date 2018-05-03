package com.ryan.jray.network.packet;

import java.util.ArrayList;

import com.ryan.jray.map.Light;
import com.ryan.jray.map.Map;
import com.ryan.jray.map.MapObject;

public class PacketMap extends Packet{
	public String name;
	public PacketMap(String name) {
		this.name = name;
	}
}
