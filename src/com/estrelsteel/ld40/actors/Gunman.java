package com.estrelsteel.ld40.actors;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.file.Saveable;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.Rotation;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public abstract class Gunman extends Actor implements Saveable {
	
	private double hp;
	private double maxhp;
	
	private double walkspeed;
	
	private int bullets;
	private int clips;
	private int fireRate;
	private int fireCooldown;
	private int reloadRate;
	private int reloadCooldown;

	public Gunman(String name, Rectangle loc) {
		super(name, loc);
		
		this.bullets = 4;
		this.clips = 100000;
		this.fireRate = 45;
		this.fireCooldown = 0;
		this.reloadRate = 120;
		this.reloadCooldown = 0;
		
		this.hp = 100;
		this.maxhp = 100;
		this.walkspeed = 5;
		
		setSortable(true);
	}

	public int getBullets() {
		return bullets;
	}

	public void setBullets(int bullets) {
		this.bullets = bullets;
	}

	public int getClips() {
		return clips;
	}

	public void setClips(int clips) {
		this.clips = clips;
	}

	public int getFireRate() {
		return fireRate;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	public int getFireCooldown() {
		return fireCooldown;
	}

	public void setFireCooldown(int fireCooldown) {
		this.fireCooldown = fireCooldown;
	}

	public int getReloadRate() {
		return reloadRate;
	}

	public void setReloadRate(int reloadRate) {
		this.reloadRate = reloadRate;
	}

	public int getReloadCooldown() {
		return reloadCooldown;
	}

	public void setReloadCooldown(int reloadCooldown) {
		this.reloadCooldown = reloadCooldown;
	}
	
	public Bullet fireAt(Rotation r) {
		Bullet temp_bullet = fire();
		if(temp_bullet != null) {
			temp_bullet.setRotation(r); 
		}
		return temp_bullet;
	}
	
	public Bullet fire() {
		Bullet temp_bullet = null;
		int type = 1;
		double speed = 7;
		if(this instanceof Player) {
			type = 2;
			speed = 10;
		}
		if(getFireCooldown() <= 0 && getReloadCooldown() <= 0) {
			if(getBullets() > 0) {
				int temp_direction = getRunningAnimationNumber() % 4;
				AbstractedPoint temp_point = PointMaths.getCentre(getLocation());
				switch(temp_direction) {
				case 0:
					temp_bullet = new Bullet("bullet", QuickRectangle.location(temp_point.getX() - 16, temp_point.getY(), 32, 32), temp_direction, type, this);
					break;
				case 1:
					temp_bullet = new Bullet("bullet", QuickRectangle.location(temp_point.getX() + 16, temp_point.getY(), 32, 32), temp_direction, type, this);
					break;
				case 2:
					temp_bullet = new Bullet("bullet", QuickRectangle.location(temp_point.getX(), temp_point.getY() + 16, 32, 32), temp_direction, type, this);
					break;
				case 3:
					temp_bullet = new Bullet("bullet", QuickRectangle.location(temp_point.getX(), temp_point.getY() - 16, 32, 32), temp_direction, type, this);
					break;
				default:
					temp_bullet = null;
					break;
				}
				if(temp_bullet != null) {
					temp_bullet.setSpeed(speed);
					setFireCooldown(getFireRate());
					setBullets(getBullets() - 1);
				}
			}
			else {
				if(getClips() > 0) {
					setReloadCooldown(getReloadRate());
					setBullets(4);
					setClips(getClips() - 1);
					System.out.println("reloading");
				}
			}
		}
		return temp_bullet;
	}

	public double getHP() {
		return hp;
	}

	public void setHP(double hp) {
		this.hp = hp;
	}

	public double getMaxHP() {
		return maxhp;
	}

	public void setMaxHP(double maxhp) {
		this.maxhp = maxhp;
	}
	
	public void damage(double damage) {
		this.hp = hp - damage;
	}

	public double getWalkspeed() {
		return walkspeed;
	}

	public Gunman setWalkspeed(double walkspeed) {
		this.walkspeed = walkspeed;
		return this;
	}

}
