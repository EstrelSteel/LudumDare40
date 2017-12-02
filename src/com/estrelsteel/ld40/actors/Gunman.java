package com.estrelsteel.ld40.actors;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public abstract class Gunman extends Actor {
	
	private double hp;
	private double maxhp;
	
	private int bullets;
	private int clips;
	private int fireRate;
	private int fireCooldown;
	private int reloadRate;
	private int reloadCooldown;

	public Gunman(String name, Rectangle loc) {
		super(name, loc);
		
		this.bullets = 8;
		this.clips = 2;
		this.fireRate = 45;
		this.fireCooldown = 0;
		this.reloadRate = 120;
		this.reloadCooldown = 0;
		
		this.hp = 100;
		this.maxhp = 100;
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
	
	public Bullet fire() {
		Bullet temp_bullet = null;
		if(getFireCooldown() <= 0 && getReloadCooldown() <= 0) {
			if(getBullets() > 0) {
				int temp_direction = getRunningAnimationNumber() % 4;
				AbstractedPoint temp_point = PointMaths.getCentre(getLocation());
				switch(temp_direction) {
				case 0:
					temp_bullet = new Bullet("bullet", QuickRectangle.location(temp_point.getX() - 4, temp_point.getY() - 5 - 48, 8, 10), temp_direction);
					break;
				case 1:
					temp_bullet = new Bullet("bullet", QuickRectangle.location(temp_point.getX() - 4, temp_point.getY() - 5 + 48, 8, 10), temp_direction);
					break;
				case 2:
					temp_bullet = new Bullet("bullet", QuickRectangle.location(temp_point.getX() - 4 - 48, temp_point.getY() - 5, 8, 10), temp_direction);
					break;
				case 3:
					temp_bullet = new Bullet("bullet", QuickRectangle.location(temp_point.getX() - 4 + 48, temp_point.getY() - 5, 8, 10), temp_direction);
					break;
				default:
					temp_bullet = null;
					break;
				}
				if(temp_bullet != null) {
					setFireCooldown(getFireRate());
					setBullets(getBullets() - 1);
				}
			}
			else {
				if(getClips() > 0) {
					setReloadCooldown(getReloadRate());
					setBullets(8);
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

}
