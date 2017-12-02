package com.estrelsteel.ld40;

import java.awt.Graphics2D;
import java.text.DecimalFormat;

import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.window.WindowSettings;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.ld40.actors.Bullet;
import com.estrelsteel.ld40.actors.Gunman;
import com.estrelsteel.ld40.actors.Player;
import com.estrelsteel.ld40.actors.guard.AISwitch;
import com.estrelsteel.ld40.actors.guard.Guard;

public class LD40 implements StartListener, StopListener, TickListener, RenderListener {
	
	/*
	 * 
	 * 
	 * 
	 * BY: ESTRELSTEEL
	 * LUDUM DARE 40 - COMPO
	 * 
	 * THEME: The More You Have, The Worse It Is
	 * 
	 * (..2017)
	 * 
	 */
	
	private Launcher l;
	private InputHandler ih;
	
	private LDWorld w;
	private Player p;
	
	private AbstractedPoint temp_point;
	private double temp_x;
	private double temp_y;
	private Bullet temp_bullet;
	private Renderable temp_renderable;
	
	public static void main(String[] args) {
		new LD40();
	}
	
	@SuppressWarnings("static-access")
	public LD40() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 630, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 640, 640);
		}
		l.getEngine().setWindowSettings(new WindowSettings(size, "LD40 - EstrelSteel", "v1.0a", 0));
		
		ih = new InputHandler();
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		l.getEngine().addKeyListener(ih);
		
		l.getEngine().ROUNDING_FORMAT = new DecimalFormat("0.##");
		
		l.getEngine().development = true;
		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD40/LD40";
//		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}

	
	@Override
	public void start() {
		w = new LDWorld(new PixelGrid());
		p = new Player("PLAYER", QuickRectangle.location(0, 0, 64, 64));
		w.getGunmen().add(p);
		w.getObjects().add(p);
		Guard g = new Guard("guard", QuickRectangle.location(0, 240, 64, 64)).setMoveX(5).setMoveY(0);
		w.getGunmen().add(g);
		w.getObjects().add(g);
		AISwitch s = new AISwitch(QuickRectangle.location(240, 240, 64, 64), -5, 0);
		w.getAiSwitches().add(s);
		w.getObjects().add(s);
		s = new AISwitch(QuickRectangle.location(-240, 240, 64, 64), 5, 0);
		w.getAiSwitches().add(s);
		w.getObjects().add(s);
	}

	@Override
	public void stop() {
		
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
//		ctx.setColor(Color.BLACK);
//		ctx.fillRect(0, 0, 640, 640);
		w.renderWorld(ctx);
		return ctx;
	}
	
	@Override
	public void tick() {
		if(ih.up) {
			temp_y = temp_y + -p.getWalkspeed();
			p.setRunningAnimationNumber(0);
		}
		if(ih.down) {
			temp_y = temp_y + p.getWalkspeed();
			p.setRunningAnimationNumber(1);
		}
		if(ih.left) {
			temp_x = temp_x + -p.getWalkspeed();
			p.setRunningAnimationNumber(2);
		}
		if(ih.right) {
			temp_x = temp_x + p.getWalkspeed();
			p.setRunningAnimationNumber(3);
		}
		
		if(temp_x != 0 || temp_y != 0) {
			p.setLocation(QuickRectangle.location(p.getLocation().getX() + temp_x, p.getLocation().getY() + temp_y, p.getLocation().getWidth(), p.getLocation().getHeight()));
			temp_x = 0;
			temp_y = 0;
		}
		
		temp_point = PointMaths.getCentre(p.getLocation());
		w.getMainCamera().getLocation().setX(temp_point.getX() - l.getEngine().getWidth() / 2);
		w.getMainCamera().getLocation().setY(temp_point.getY() - l.getEngine().getHeight() / 2);
		
		if(ih.attack) {
			temp_bullet = p.fire();
			if(temp_bullet != null) {
				w.getObjects().add(temp_bullet);
			}
		}
		
		w.sortObjects();
		if(p.getFireCooldown() > 0) {
			p.setFireCooldown(p.getFireCooldown() - 1);
		}
		else if(p.getReloadCooldown() > 0) {
			p.setReloadCooldown(p.getReloadCooldown() - 1);
		}
		
		if(p.getHP() <= 0) {
			System.out.println("GAME OVER YEAH!");
		}
		
		for(int i = 0; i < w.getObjects().size(); i++) { 
			if(w.getObjects().get(i) instanceof Actor) {
				((Actor) w.getObjects().get(i)).getRunningAnimation().run();
				if(w.getObjects().get(i) instanceof Bullet) {
					
					temp_renderable = World.checkCollideIgnoreDeclaration(w.getGunmen(), ((Bullet) w.getObjects().get(i)).getHitArea(), null);
					if(temp_renderable != null && temp_renderable instanceof Gunman) {
						((Gunman) temp_renderable).setHP(((Gunman) temp_renderable).getHP() - ((Bullet) w.getObjects().get(i)).getDamage());
					}
					if(!((Bullet) w.getObjects().get(i)).travel(w.getMainCamera())) {
						w.getObjects().remove(i);
						i--;
					}
				}
				else if(w.getObjects().get(i) instanceof Gunman) {
					if(((Gunman) w.getObjects().get(i)).getHP() <= 0 && !(w.getObjects().get(i) instanceof Player)) {
						w.getGunmen().remove(w.getObjects().remove(i));
						i--;
					}
					if(w.getObjects().get(i) instanceof Guard) {
						((Guard) w.getObjects().get(i)).walk();
						temp_renderable = World.checkCollideIgnoreDeclaration(w.getAiSwitches(), new RectangleCollideArea(w.getObjects().get(i).getLocation()), null);
						if(temp_renderable != null && temp_renderable instanceof AISwitch) {
							((Guard) w.getObjects().get(i)).setMoveX(((AISwitch) temp_renderable).getX());
							((Guard) w.getObjects().get(i)).setMoveY(((AISwitch) temp_renderable).getY());
						}
					}
				}
			}
		}
	}
}
