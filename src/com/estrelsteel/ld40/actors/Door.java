package com.estrelsteel.ld40.actors;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.file.Saveable;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Door extends Actor implements Saveable {

	private boolean open;
	
	public Door(String name, Rectangle loc) {
		super(name, loc);
		open = false;
		
		getAnimations().add(0, new Animation("door_closed", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 3 * 16, 32, 16)));
		getAnimations().add(1, new Animation("door_opening", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 3 * 16, 32, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(2 * 16, 3 * 16, 32, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 3 * 16, 32, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 3 * 16, 32, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 3 * 16, 32, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 3 * 16, 32, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 3 * 16, 32, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 3 * 16, 32, 16)));
		getAnimations().add(2, new Animation("door_closing", 2));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 3 * 16, 32, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(2 * 16, 3 * 16, 32, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 3 * 16, 32, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 3 * 16, 32, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 3 * 16, 32, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 3 * 16, 32, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 3 * 16, 32, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 3 * 16, 32, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 3 * 16, 32, 16)));
		getAnimations().add(3, new Animation("door_open", 3));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 3 * 16, 32, 16)));
		
		getAnimations().get(1).setMaxWaitTime(15);
		getAnimations().get(2).setMaxWaitTime(15);
		setCollision(new Collision(true, new RectangleCollideArea(loc)));
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public String getIdentifier() {
		return "DOR";
	}

	@Override
	public Object load(GameFile file, int line) {
		String args[] = file.getLines().get(line).split(" ");
		if(args[0].equalsIgnoreCase(getIdentifier())) {
			Door d = new Door(args[1], QuickRectangle.location(Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5])));
			return d;
			
		}
		return null;
	}

	@Override
	public GameFile save(GameFile file) {
		return file;
	}

}
