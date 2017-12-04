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

public class LaserField extends Actor implements Saveable {

	public LaserField(String name, Rectangle loc) {
		super(name, loc);
		
		getAnimations().add(0, new Animation("laser_field_updown", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(1 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(2 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(3 * 16, 2 * 16, 16, 16)));
		getAnimations().add(1, new Animation("laser_field_leftright", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(4 * 16, 2 * 16, 16, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(5 * 16, 2 * 16, 16, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(6 * 16, 2 * 16, 16, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(7 * 16, 2 * 16, 16, 16)));
		
		getAnimations().get(0).setMaxWaitTime(4);
		
		setCollision(new Collision(true, new RectangleCollideArea(loc)));
	}

	@Override
	public String getIdentifier() {
		return "LRF";
	}

	@Override
	public Object load(GameFile file, int line) {
		String args[] = file.getLines().get(line).split(" ");
		if(args[0].equalsIgnoreCase(getIdentifier())) {
			LaserField lf = new LaserField("laser_field", QuickRectangle.location(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])));
			lf.setRunningAnimationNumber(Integer.parseInt(args[5]));
			return lf;
			
		}
		return null;
	}

	@Override
	public GameFile save(GameFile file) {
		return file;
	}

}
