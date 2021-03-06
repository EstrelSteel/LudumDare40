package com.estrelsteel.ld40.actors;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.actor.Camera;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.AbstractedRectangle;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Bullet extends Actor {
	
	private double speed;
	private int direction;
	private double damage;
	private Rectangle lastLocation;
	private Gunman owner;
	
	public Bullet(String name, Rectangle location, int direction, int animationNum, Gunman owner) {
		super(name, location);
		getAnimations().add(0, new Animation("bullet", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(1 * 16, 0, 16, 16)));
		getAnimations().add(1, new Animation("lazer_red", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 0, 16, 16)));
		getAnimations().add(2, new Animation("lazer_blue", 2));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(5 * 16, 0, 16, 16)));
		
		speed = 7;
		damage = 20;
		
		this.owner = owner;
		
		this.direction = direction;
		this.getRotation().setCentre(PointMaths.getCentre(location));
		this.lastLocation = location;
		
		setRunningAnimationNumber(animationNum);
		
		if(direction == 0) {
			getRotation().setDegrees(0);
		}
		if(direction == 1) {
			getRotation().setDegrees(180);
		}
		if(direction == 2) {
			getRotation().setDegrees(270);
		}
		if(direction == 3) {
			getRotation().setDegrees(90);
		}
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean travel(Camera c) {
		travel();
		if(PointMaths.getDistanceTo(PointMaths.getCentre(getLocation()), c.getLocation()) > 1280) {
			return false;
		}
		return true;
	}
	
	public RectangleCollideArea getHitArea() {
		if(direction == 0 || direction == 2) {
			AbstractedRectangle rect = new AbstractedRectangle(getLocation().getTop(), getLastLocation().getBottom());
			return new RectangleCollideArea(QuickRectangle.location(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
		}
		else if(direction == 1 || direction == 3) {
			AbstractedRectangle rect = new AbstractedRectangle(getLastLocation().getTop(), getLocation().getBottom());
			return new RectangleCollideArea(QuickRectangle.location(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
		}
		return null;
	}
	
	public void travel() {
		setLocation(QuickRectangle.location(getLocation().getX() + speed * Math.cos(getRotation().getRadians() - Math.PI / 2), 
				getLocation().getY() + speed * Math.sin(getRotation().getRadians() - Math.PI / 2), getLocation().getWidth(), getLocation().getHeight()));
	}
	
	public Rectangle getLastLocation() {
		return lastLocation;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public void setLastLocation(Rectangle lastLocation) {
		this.lastLocation = lastLocation;
	}
	
	@Override
	public void setLocation(Rectangle loc) {
		this.lastLocation = getLocation();
		super.setLocation(loc);
	}

	public Gunman getOwner() {
		return owner;
	}

	public void setOwner(Gunman owner) {
		this.owner = owner;
	}
}
