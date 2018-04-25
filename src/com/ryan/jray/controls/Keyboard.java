package com.ryan.jray.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	public boolean[] keys = new boolean[120];
	public boolean up, down, left, right, rotR, rotL;

	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		rotL = keys[KeyEvent.VK_LEFT];
		rotR = keys[KeyEvent.VK_RIGHT];

	}

	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() <= 120)
			keys[ke.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() <= 120)
			keys[ke.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent ke) {

	}

}
