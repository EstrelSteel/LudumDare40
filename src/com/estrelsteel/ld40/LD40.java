package com.estrelsteel.ld40;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.text.DecimalFormat;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.Rotation;
import com.estrelsteel.engine2.shape.collide.PerspectiveRectangleArea;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.tile.Tile;
import com.estrelsteel.engine2.tile.TileType;
import com.estrelsteel.engine2.window.WindowSettings;
import com.estrelsteel.engine2.world.Level;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.ld40.actors.Bullet;
import com.estrelsteel.ld40.actors.Gunman;
import com.estrelsteel.ld40.actors.LightSource;
import com.estrelsteel.ld40.actors.Player;
import com.estrelsteel.ld40.actors.guard.AISwitch;
import com.estrelsteel.ld40.actors.guard.Guard;
import com.estrelsteel.ld40.actors.guard.GuardState;

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
	private ConfinedImage darkness;
	
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
		l.getEngine().addMouseListener(ih);
		
		l.getEngine().ROUNDING_FORMAT = new DecimalFormat("0.##");
		
		l.getEngine().development = true;
		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD40/LD40";
//		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}

	
	@Override
	public void start() {
		w = new LDWorld(new PixelGrid());
		p = new Player("PLAYER", QuickRectangle.location(320, 384, 64, 64));
		darkness = new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0, 0, 16, 16));
		darkness.loadImage();
		
		TileType tt = new TileType(-1, "null");
		tt.load(new GameFile(Engine2.devPath + "/res/tiles.txt"), 0);
		
		Level loader = new Level(w);
		GameFile f = new GameFile(Engine2.devPath + "/res/lvl/lvl0.txt");
		try {
			f.setLines(f.readFile());
			loader.load(f, 0);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		w.getGunmen().add(p);
		w.getObjects().add(p);
//		Guard g = (Guard) new Guard("guard", QuickRectangle.location(960, 480, 64, 64), 5, 0).setWalkspeed(3);
//		w.getGunmen().add(g);
//		w.getObjects().add(g);
//		AISwitch s = new AISwitch(QuickRectangle.location(1200, 480, 64, 64), -3, 0);
//		w.getAiSwitches().add(s);
//		s = new AISwitch(QuickRectangle.location(720, 480, 64, 64), 3, 0);
//		w.getAiSwitches().add(s);
	}

	@Override
	public void stop() {
		
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		ctx.setColor(Color.BLACK);
		ctx.fillRect(0, 0, 640, 640);
		w.renderWorld(ctx);
//		ctx.drawImage(darkness.getImage(), -10, -10, 650, 650, null);
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
			Rectangle oldLoc = p.getLocation();
			p.setLocation(QuickRectangle.location(p.getLocation().getX() + temp_x, p.getLocation().getY() + temp_y, p.getLocation().getWidth(), p.getLocation().getHeight()));
			temp_x = 0;
			temp_y = 0;
			p.getRunningAnimation().run();
			if(w.checkCollide(new PerspectiveRectangleArea(p.getLocation()), p) != null) {
				p.setLocation(oldLoc);
			}
			
		}
		
		temp_point = PointMaths.getCentre(p.getLocation());
		w.getMainCamera().getLocation().setX(temp_point.getX() - l.getEngine().getWidth() / 2);
		w.getMainCamera().getLocation().setY(temp_point.getY() - l.getEngine().getHeight() / 2);
		
		if(ih.attack1) {
			Rotation temp_rotation = PointMaths.getDirectionTowards(new AbstractedPoint(320, 320), new AbstractedPoint(ih.x, ih.y));
			temp_rotation.setRadians(temp_rotation.getRadians() + Math.PI / 2);
			temp_bullet = p.fireAt(temp_rotation);
			if(temp_bullet != null) {
				w.getObjects().add(temp_bullet);
//				w.getObjects().add(new LightSource("bullet_flash", QuickRectangle.location(temp_bullet.getLocation().getX() - 12, temp_bullet.getLocation().getY(), 32, 32), 16));
			}
		}
		
		w.sortObjects();
		
		
		if(p.getHP() <= 0) {
			System.out.println("GAME OVER YEAH!");
		}
		
		for(int i = 0; i < w.getObjects().size(); i++) { 
			if(w.getObjects().get(i) instanceof Actor) {
				if(!(w.getObjects().get(i) instanceof Player)) {
					((Actor) w.getObjects().get(i)).getRunningAnimation().run();
				}
				if(w.getObjects().get(i) instanceof Bullet) {
					boolean remove = false;
					temp_renderable = World.checkCollideIgnoreDeclaration(w.getGunmen(), ((Bullet) w.getObjects().get(i)).getHitArea(), ((Bullet) w.getObjects().get(i)).getOwner());
					if(temp_renderable != null && temp_renderable instanceof Gunman) {
						((Gunman) temp_renderable).damage(((Bullet) w.getObjects().get(i)).getDamage());
						remove = true;
					}
					else if(!((Bullet) w.getObjects().get(i)).travel(w.getMainCamera())) {
						remove = true;
					}
					temp_renderable = w.checkCollide(((Bullet) w.getObjects().get(i)).getHitArea(), ((Bullet) w.getObjects().get(i)));
					if(temp_renderable != null && temp_renderable instanceof Tile) {
						remove = true;
					}
					
					if(remove) {
						w.getObjects().remove(i);
						i--;
					}
				}
				else if(w.getObjects().get(i) instanceof LightSource) {
					((LightSource) w.getObjects().get(i)).decay();
					if(((LightSource) w.getObjects().get(i)).shouldRemove()) {
						w.getObjects().remove(i);
						i--;
					}
				}
				else if(w.getObjects().get(i) instanceof Gunman) {
					if(w.getObjects().get(i) instanceof Guard) {
						if(((Guard) w.getObjects().get(i)).getHP() <= 0 && ((Guard) w.getObjects().get(i)).getGuardState() != GuardState.GHOST) {
							((Guard) w.getObjects().get(i)).setGuardState(GuardState.GHOST);
							((Guard) w.getObjects().get(i)).setHP(10000);
							((Guard) w.getObjects().get(i)).setRunningAnimationNumber(5);
						}
						((Guard) w.getObjects().get(i)).determineAction(p, w);
						temp_renderable = World.checkCollideIgnoreDeclaration(w.getAiSwitches(), new RectangleCollideArea(w.getObjects().get(i).getLocation()), null);
						if(temp_renderable != null && temp_renderable instanceof AISwitch) {
							((Guard) w.getObjects().get(i)).setMoveX(((AISwitch) temp_renderable).getX());
							((Guard) w.getObjects().get(i)).setMoveY(((AISwitch) temp_renderable).getY());
						}
					}
					if(((Gunman) w.getObjects().get(i)).getFireCooldown() > 0) {
						((Gunman) w.getObjects().get(i)).setFireCooldown(((Gunman) w.getObjects().get(i)).getFireCooldown() - 1);
					}
					else if(((Gunman) w.getObjects().get(i)).getReloadCooldown() > 0) {
						((Gunman) w.getObjects().get(i)).setReloadCooldown(((Gunman) w.getObjects().get(i)).getReloadCooldown() - 1);
					}
				}
			}
		}
	}
}
