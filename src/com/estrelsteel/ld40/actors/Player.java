package com.estrelsteel.ld40.actors;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Player extends Gunman {

	public Player(String name, Rectangle loc) {
		super(name, loc);
	
		getAnimations().add(0, new Animation("player_up", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(0 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(1 * 16, 2 * 16, 16, 16)));
		getAnimations().get(0).setMaxWaitTime(4);
		getAnimations().add(1, new Animation("player_down", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(0 * 16, 1 * 16, 16, 16)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(1 * 16, 1 * 16, 16, 16)));
		getAnimations().get(1).setMaxWaitTime(4);
		getAnimations().add(2, new Animation("player_left", 2));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(0 * 16, 4 * 16, 16, 16)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(1 * 16, 4 * 16, 16, 16)));
		getAnimations().get(2).setMaxWaitTime(4);
		getAnimations().add(3, new Animation("player_right", 3));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(0 * 16, 3 * 16, 16, 16)));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(1 * 16, 3 * 16, 16, 16)));
		getAnimations().get(3).setMaxWaitTime(4);
		
		this.setRunningAnimationNumber(1);
		
		this.setReloadRate(1);
	}

}
