package com.estrelsteel.ld40;

import java.awt.Color;
import java.awt.Font;
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
import com.estrelsteel.engine2.image.Image;
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
import com.estrelsteel.ld40.actors.ActorLoader;
import com.estrelsteel.ld40.actors.Bullet;
import com.estrelsteel.ld40.actors.Button;
import com.estrelsteel.ld40.actors.Door;
import com.estrelsteel.ld40.actors.Exit;
import com.estrelsteel.ld40.actors.Gunman;
import com.estrelsteel.ld40.actors.LaserField;
import com.estrelsteel.ld40.actors.LightSource;
import com.estrelsteel.ld40.actors.Player;
import com.estrelsteel.ld40.actors.guard.AISwitch;
import com.estrelsteel.ld40.actors.guard.Guard;
import com.estrelsteel.ld40.actors.guard.GuardState;

public class LD40 implements StartListener, StopListener, TickListener, RenderListener {
	
	/*
	 * Robot Escape
	 * 
	 * 
	 * BY: ESTRELSTEEL
	 * LUDUM DARE 40 - COMPO
	 * 
	 * THEME: The More You Have, The Worse It Is
	 * 
	 * (03.12.2017)
	 * 
	 */
	
	private Launcher l;
	private InputHandler ih;
	
	private LDWorld w;
	private Player p;
	private Image death;
	private int level;
	private ActorLoader actorLoader;
	private Level loader;
	
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
		level = 8;
//		p = new Player("PLAYER", QuickRectangle.location(320, 384, 64, 64));
		death = new Image(Engine2.devPath + "/res/img/death.png");
		death.loadImage();
		
		TileType tt = new TileType(-1, "null");
		tt.load(new GameFile(Engine2.devPath + "/res/tiles.txt"), 0);
		
		actorLoader = new ActorLoader();
		
		loadLevel();
		
//		w.getGunmen().add(p);
//		w.getObjects().add(p);
//		w.getObjects().add(new Exit(QuickRectangle.location(640, 384, 64, 64)));
//		Guard g = (Guard) new Guard("guard", QuickRectangle.location(960, 480, 64, 64), 5, 0).setWalkspeed(3);
//		w.getGunmen().add(g);
//		w.getObjects().add(g);
//		AISwitch s = new AISwitch(QuickRectangle.location(1200, 480, 64, 64), -3, 0);
//		w.getAiSwitches().add(s);
//		s = new AISwitch(QuickRectangle.location(720, 480, 64, 64), 3, 0);
//		w.getAiSwitches().add(s);
	}
	
	public LDWorld loadLevel(int level) {
		this.level = level;
		return loadLevel();
	}
	
	public LDWorld loadLevel() {
		w = new LDWorld(new PixelGrid());
		if(p != null) {
			p.setHP(100);
		}
		loader = new Level(w);
		GameFile f = new GameFile(Engine2.devPath + "/res/lvl/lvl" + level + ".txt");
		try {
			f.setLines(f.readFile());
			loader.load(f, 0);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		f = new GameFile(Engine2.devPath + "/res/lvl/" + level + "/entities.txt");
		try {
			f.setLines(f.readFile());
			actorLoader.load(f, 0, w);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < w.getGunmen().size(); i++) {
			if(w.getGunmen().get(i) instanceof Player) {
				p = (Player) w.getGunmen().get(i);
				break;
			}
		}
		return w;
	}

	@Override
	public void stop() {
		
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		ctx.setColor(Color.BLACK);
		ctx.fillRect(0, 0, 640, 640);
		w.renderWorld(ctx);
		for(int i = 0; i < w.getText().size(); i++) {
			w.getText().get(i).render(ctx, w);
		}
		ctx.setColor(Color.BLACK);
		ctx.fillRect(18, 18, 204, 44);
		ctx.setColor(Color.RED);
		ctx.fillRect(20, 20, 200, 40);
		ctx.setColor(Color.GREEN);
		ctx.fillRect(20, 20, (int) (200 * (p.getHP() / p.getMaxHP())), 40);
		ctx.setColor(Color.BLACK);
		ctx.setFont(new Font("Menlo", Font.BOLD, 16));
		ctx.drawString("HP: " + (int) p.getHP() + "/" + (int) p.getMaxHP(), 40, 48);
//		ctx.drawImage(darkness.getImage(), -10, -10, 650, 650, null);
		if(p.getHP() <= 0) {
			ctx.drawImage(death.getImage(), -10, -10, 650, 650, null);
		}
		return ctx;
	}
	
	@Override
	public void tick() {
		if(ih.reload) {
			ih.reload = false;
			loadLevel();
		}
		
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
			temp_renderable = w.checkCollide(new PerspectiveRectangleArea(p.getLocation()), p);
			if(temp_renderable instanceof Exit) {
				loadLevel(++level);
			}
			else if(temp_renderable != null && !(temp_renderable instanceof LaserField)) {
				if(!(temp_renderable instanceof Door) || (temp_renderable instanceof Door && !((Door) temp_renderable).isOpen()))
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
				p.setFired(true);
//				w.getObjects().add(new LightSource("bullet_flash", QuickRectangle.location(temp_bullet.getLocation().getX() - 12, temp_bullet.getLocation().getY(), 32, 32), 16));
			}
			else {
				p.setFired(false);
			}
		}
		
		if(ih.attack2) {
			temp_renderable = World.checkCollideIgnoreDeclaration(w.getGunmen(), new RectangleCollideArea(
					QuickRectangle.location(temp_point.getX() - 100, temp_point.getY() - 100, 200, 200)), p);
			if(temp_renderable != null && temp_renderable instanceof Guard) {
				if(!((Guard) temp_renderable).isCombat()) {
					((Guard) temp_renderable).setGuardState(GuardState.SLEEP);
					((Guard) temp_renderable).setRunningAnimationNumber(4);
				}
			}
		}
		
		if(ih.sneak) {
			p.setSneaking(true);
			p.setWalkspeed(3.0);
		}
		else {
			p.setSneaking(false);
			p.setWalkspeed(5.0);
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
					if(temp_renderable != null && (temp_renderable instanceof Tile || temp_renderable instanceof LaserField || temp_renderable instanceof Door)) {
						if(temp_renderable instanceof Tile) {
							if(((Tile)temp_renderable).getType() != TileType.types.get(8) && ((Tile)temp_renderable).getType() != TileType.types.get(9)) {
								remove = true;
							}
						}
						else if(temp_renderable instanceof Door && ((Door) temp_renderable).getRunningAnimationNumber() == 0) {
							remove = true;
						}
						else {
							remove = true;
						}
					}
					if(temp_renderable != null && temp_renderable instanceof Button) {
						remove = true;
						((Button) temp_renderable).setPressed(!((Button) temp_renderable).isPressed());
						if(((Button) temp_renderable).isPressed()) {
							((Button) temp_renderable).setRunningAnimationNumber(1);
						}
						else {
							((Button) temp_renderable).setRunningAnimationNumber(0);
						}
						for(int j = 0; j < w.getObjects().size(); j++) {
							if(w.getObjects().get(j) instanceof Door) {
								if(((Door) w.getObjects().get(j)).getName().equals(((Button) temp_renderable).getName())) {
									((Door) w.getObjects().get(j)).setOpen(!((Door) w.getObjects().get(j)).isOpen());
									if(((Door) w.getObjects().get(j)).isOpen()) {
										((Door) w.getObjects().get(j)).setRunningAnimationNumber(1);
									}
									else {
										((Door) w.getObjects().get(j)).setRunningAnimationNumber(2);
									}
								}
							}
						}
						
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
							p.setGhosts(p.getGhosts() + 1);
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
				else if(w.getObjects().get(i) instanceof Door) {
					if(((Door) w.getObjects().get(i)).getRunningAnimationNumber() == 1) {
						if(((Door) w.getObjects().get(i)).getRunningAnimation().getCurrentFrame() > 4) {
							((Door) w.getObjects().get(i)).setRunningAnimationNumber(3);
						}
					}
					if(((Door) w.getObjects().get(i)).getRunningAnimationNumber() == 2) {
						if(((Door) w.getObjects().get(i)).getRunningAnimation().getCurrentFrame() > 4) {
							((Door) w.getObjects().get(i)).setRunningAnimationNumber(0);
						}
					}
				}
			}
		}
		
		w.sortObjects();
	}
}
