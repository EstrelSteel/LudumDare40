package com.estrelsteel.ld40;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean attack1;
	public boolean attack2;
	public boolean sneak;
	public boolean reload;
	
	public int x;
	public int y;
	
	public InputHandler() {
		up = false;
		down = false;
		left = false;
		right = false;
		attack1 = false;
		attack2 = false;
		reload = false;
		
		x = 0;
		y = 0;
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
		if(e.getKeyCode() == 16 || e.getKeyCode() == 17 || e.getKeyCode() == 96) {
			sneak = true;
		}
		if(e.getKeyCode() == 27) {
			reload = true;
			System.out.println("restarting");
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
		if(e.getKeyCode() == 16 || e.getKeyCode() == 17 || e.getKeyCode() == 96) {
			sneak = false;
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(e.getButton() == MouseEvent.BUTTON1) {
			attack1 = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			attack2 = true;
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(e.getButton() == MouseEvent.BUTTON1) {
			attack1 = false;
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			attack2 = false;
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
