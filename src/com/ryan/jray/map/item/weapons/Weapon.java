package com.ryan.jray.map.item.weapons;

import com.ryan.jray.map.item.Item;

public class Weapon extends Item{
	
	public int totalCapacity;
	public int clipCapacity;
	public double bulletDamage;
	public int shotCooldown;//how many ticks between each bullet shot
	public Weapon(int ID) {
		super(ID);
	}
}
