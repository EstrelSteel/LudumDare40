package com.estrelsteel.ld40.actors.guard;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.ld40.actors.Gunman;

public class Guard extends Gunman {

	private int turnCooldown;
	private double movex;
	private double movey;
	
	public Guard(String name, Rectangle loc) {
		super(name, loc);
		
		getAnimations().add(0, new Animation("guard_up", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(0 * 16, 0, 16, 16)));
		getAnimations().add(1, new Animation("guard_down", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(1 * 16, 0, 16, 16)));
		getAnimations().add(2, new Animation("guard_left", 2));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(2 * 16, 0, 16, 16)));
		getAnimations().add(3, new Animation("guard_right", 3));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(3 * 16, 0, 16, 16)));
		
		turnCooldown = 0;
		movex = 0;
		movey = 0;
	}

	public int getTurnCooldown() {
		return turnCooldown;
	}

	public void setTurnCooldown(int turnCooldown) {
		this.turnCooldown = turnCooldown;
	}

	public double getMoveX() {
		return movex;
	}

	public Guard setMoveX(double movex) {
		this.movex = movex;
		return this;
	}

	public double getMoveY() {
		return movey;
	}

	public Guard setMoveY(double movey) {
		this.movey = movey;
		return this;
	}
	
	public Guard walk() {
		setLocation(QuickRectangle.location(getLocation().getX() + movex, getLocation().getY() + movey, getLocation().getWidth(), getLocation().getHeight()));
		if(movey < 0) {
			setRunningAnimationNumber(0);
		}
		else if(movey > 0) {
			setRunningAnimationNumber(1);
		}
		if(movex < 0) {
			setRunningAnimationNumber(2);
		}
		else if(movex > 0) {
			setRunningAnimationNumber(3);
		}
		return this;
	}

}
