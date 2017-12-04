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

public class Exit extends Actor implements Saveable {

	public Exit(Rectangle loc) {
		super("exit", loc);
		
		setSortable(true);
		setCollision(new Collision(true, new RectangleCollideArea(loc)));
		
		getAnimations().add(0, new Animation("teleport", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 1 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(1 * 16, 1 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(2 * 16, 1 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(3 * 16, 1 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 1 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(5 * 16, 1 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(6 * 16, 1 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(7 * 16, 1 * 16, 16, 16)));
		
		getAnimations().get(0).setMaxWaitTime(10);
	}

	@Override
	public String getIdentifier() {
		return "EXT";
	}

	@Override
	public Object load(GameFile file, int line) {
		String args[] = file.getLines().get(line).split(" ");
		if(args[0].trim().equalsIgnoreCase(getIdentifier().trim())) {
			return new Exit(QuickRectangle.location(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])));
		}
		return null;
	}

	@Override
	public GameFile save(GameFile file) {
		return file;
	}

}
