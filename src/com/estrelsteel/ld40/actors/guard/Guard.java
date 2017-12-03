package com.estrelsteel.ld40.actors.guard;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.Rotation;
import com.estrelsteel.engine2.shape.collide.PerspectiveRectangleArea;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.ld40.LDWorld;
import com.estrelsteel.ld40.actors.Bullet;
import com.estrelsteel.ld40.actors.Gunman;
import com.estrelsteel.ld40.actors.Player;

public class Guard extends Gunman {

	private int turnCooldown;
	private double movex;
	private double movey;
	private GuardStrat strat;
	private GuardState state;
	private boolean combat;
	private boolean lost;
	private double sightRange;
	
	private Rectangle originalLocation;
	private double originalMovex;
	private double originalMovey;
	
	public Guard(String name, Rectangle loc, double movex, double movey) {
		super(name, loc);
		
		getAnimations().add(0, new Animation("guard_up", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(0 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(1 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).setMaxWaitTime(16);
		getAnimations().add(1, new Animation("guard_down", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(0 * 16, 1 * 16, 16, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(1 * 16, 1 * 16, 16, 16)));
		getAnimations().get(1).setMaxWaitTime(16);
		getAnimations().add(2, new Animation("guard_left", 2));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(0 * 16, 4 * 16, 16, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(1 * 16, 4 * 16, 16, 16)));
		getAnimations().get(2).setMaxWaitTime(16);
		getAnimations().add(3, new Animation("guard_right", 3));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(0 * 16, 3 * 16, 16, 16)));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(1 * 16, 3 * 16, 16, 16)));
		getAnimations().get(3).setMaxWaitTime(16);
		getAnimations().add(4, new Animation("guard_sleep", 4));
		getAnimations().get(4).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(0 * 16, 5 * 16, 16, 16)));
		getAnimations().get(4).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(1 * 16, 5 * 16, 16, 16)));
		getAnimations().get(4).setMaxWaitTime(16);
		getAnimations().add(5, new Animation("guard_ghost", 5));
		getAnimations().get(5).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(0 * 16, 6 * 16, 16, 16)));
		getAnimations().get(5).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/guard0.png", QuickRectangle.location(1 * 16, 6 * 16, 16, 16)));
		getAnimations().get(5).setMaxWaitTime(16);
		
		turnCooldown = 0;
		this.movex = movex;
		this.movey = movey;
		this.originalMovex = movex;
		this.originalMovey = movey;

		state = GuardState.ACTIVE;
		strat = GuardStrat.ATTACK;
		combat = false;
		lost = false;
		sightRange = 160;
		originalLocation = loc;
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

	public GuardStrat getGuardStrategy() {
		return strat;
	}

	public void setGuardStrategy(GuardStrat strat) {
		this.strat = strat;
	}

	public boolean isCombat() {
		return combat;
	}

	public void setCombat(boolean combat) {
		this.combat = combat;
	}

	public boolean isLost() {
		return lost;
	}

	public void setLost(boolean lost) {
		this.lost = lost;
	}

	public double getSightRange() {
		return sightRange;
	}

	public void setSightRange(double sightRange) {
		this.sightRange = sightRange;
	}
	
	public GuardState getGuardState() {
		return state;
	}
	
	public void setGuardState(GuardState state) {
		this.state = state;
	}

	public Rectangle getOriginalLocation() {
		return originalLocation;
	}

	public void setOriginalLocation(Rectangle originalLocation) {
		this.originalLocation = originalLocation;
	}
	
	public double getOriginalMoveX() {
		return originalMovex;
	}

	public void setOriginalMoveX(double originalMovex) {
		this.originalMovex = originalMovex;
	}

	public double getOriginalMoveY() {
		return originalMovey;
	}

	public void setOriginalMoveY(double originalMovey) {
		this.originalMovey = originalMovey;
	}
	
	public void damage(double damage) {
		super.damage(damage);
		combat = true;
		System.out.println("entering combat");
	}

	public Guard determineAction(Player p, LDWorld w) {
		if(state == GuardState.ACTIVE) {
			if(PointMaths.getDistanceTo(PointMaths.getCentre(getLocation()), PointMaths.getCentre(p.getLocation())) < sightRange) {
				combat = true;
			}
			if(!combat && !lost) {
				return walk(true, w);
			}
			else if(!combat && lost) {
				if(new RectangleCollideArea(originalLocation).checkCollision(getLocation())) {
					lost = false;
					setLocation(originalLocation);
					movex = originalMovex;
					movey = originalMovey;
					return walk(true, w);
				}
				Rotation r = PointMaths.getDirectionTowards(PointMaths.getCentre(getLocation()), PointMaths.getCentre(originalLocation));
				movex = -getWalkspeed() * Math.cos(r.getRadians());
				movey = getWalkspeed() * Math.sin(r.getRadians());
				return walk(true, w);
			}
			else if(combat) {
				if(strat == GuardStrat.ATTACK) {
					if(PointMaths.getDistanceTo(PointMaths.getCentre(getLocation()), PointMaths.getCentre(p.getLocation())) > 960) {
						combat = false;
						lost = true;
						return determineAction(p, w);
					}
					
					AbstractedPoint playermid = PointMaths.getCentre(p.getLocation());
					if(Math.abs(getLocation().getX() - p.getLocation().getX()) > Math.abs(getLocation().getY() - p.getLocation().getY())) { // correct y then
						if(getLocation().getY() + getLocation().getHeight() / 2 > playermid.getY()) {
							movex = 0;
							movey = -getWalkspeed();
						}
						else if(getLocation().getY() + getLocation().getHeight() / 2  < playermid.getY()) {
							movex = 0;
							movey = getWalkspeed();
						}
						if(getLocation().getY() + getLocation().getHeight() / 2  <= playermid.getY() + 48 && getLocation().getY() + getLocation().getHeight() / 2  >= playermid.getY() - 48) {
							walk(false, w);
							aim(p, true);
							Bullet b = fire();
							if(b != null) {
								w.getObjects().add(b);
	//							w.getObjects().add(new LightSource("bullet_flash", QuickRectangle.location(b.getLocation().getX() - 12, b.getLocation().getY(), 32, 32), 4));
							}
						}
						else {
							walk(true, w);
						}
					}
					else { //correct x then
						if(getLocation().getX() + getLocation().getWidth() / 2  > playermid.getX()) {
							movex = -getWalkspeed();
							movey = 0;
						}
						else if(getLocation().getX() + getLocation().getWidth() / 2 < playermid.getX()) {
							movex = getWalkspeed();
							movey = 0;
						}
						if(getLocation().getX() + getLocation().getWidth() / 2 <= playermid.getX() + 48 && getLocation().getX() + getLocation().getWidth() / 2 >= playermid.getX() - 48) {
							movex = 0;
							movey = 0;
							walk(false, w);
							aim(p, false);
							Bullet b = fire();
							if(b != null) {
								w.getObjects().add(b);
							}
						}
						else {
							walk(true, w);
						}
					}
				}
			}
		}
		else if(state == GuardState.GHOST) {
			if(!new RectangleCollideArea(p.getLocation()).checkCollision(getLocation())) {
				Rotation r = PointMaths.getDirectionTowards(PointMaths.getCentre(getLocation()), PointMaths.getCentre(p.getLocation()));
				movex = getWalkspeed() * Math.cos(r.getRadians());
				movey = getWalkspeed() * Math.sin(r.getRadians());
				return walk(false, w);
			}
			else {
				movex = 0;
				movey = 0;
			}
		}
		return this;
	}
	
	public Guard aim(Player p, boolean aimX) {
		//aimX = is it aiming along the x axis
		if(aimX) {
			if(p.getLocation().getX() > getLocation().getX()) {
				setRunningAnimationNumber(3);
			}
			else {
				setRunningAnimationNumber(2);
			}
		}
		else {
			if(p.getLocation().getY() > getLocation().getY()) {
				setRunningAnimationNumber(1);
			}
			else {
				setRunningAnimationNumber(0);
			}
		}
		return this;
	}

	public Guard walk(boolean changeFace, LDWorld w) {
		Rectangle oldLoc = getLocation();
		setLocation(QuickRectangle.location(getLocation().getX() + movex, getLocation().getY() + movey, getLocation().getWidth(), getLocation().getHeight()));
		if(w.checkCollide(new PerspectiveRectangleArea(getLocation()), this) != null) {
			setLocation(oldLoc);
		}
		if(changeFace) {
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
		}
		return this;
	}

}
