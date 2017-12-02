package com.estrelsteel.ld40;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class InputHandler implements KeyListener {

	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean attack;
	
	public InputHandler() {
		up = false;
		down = false;
		left = false;
		right = false;
		attack = false;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 87 || e.getKeyCode() == 38) {
			up = true;
		}
		if(e.getKeyCode() == 83 || e.getKeyCode() == 40) {
			down = true;
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			left = true;
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			right = true;
		}
		if(e.getKeyCode() == 32 || e.getKeyCode() == 16) {
			attack = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 87 || e.getKeyCode() == 38) {
			up = false;
		}
		if(e.getKeyCode() == 83 || e.getKeyCode() == 40) {
			down = false;
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			left = false;
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			right = false;
		}
		if(e.getKeyCode() == 32 || e.getKeyCode() == 16) {
			attack = false;
		}
	}

}
