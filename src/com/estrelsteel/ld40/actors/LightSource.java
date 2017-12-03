package com.estrelsteel.ld40.actors;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class LightSource extends Actor {

	private int decay;
	
	public LightSource(String name, Rectangle loc, int decay) {
		super(name, loc);
		
		getAnimations().add(0, new Animation("light_yellow", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(2 * 16, 0, 16, 16)));
		getAnimations().add(1, new Animation("light_white", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(3 * 16, 0, 16, 16)));
		
		
		this.setRunningAnimationNumber(1);
		this.decay = decay;
	}
	
	public int getDecay() {
		return decay;
	}
	
	public void setDecay(int decay) {
		this.decay = decay;
	}
	
	public boolean shouldRemove() {
		if(decay == 0) {
			return true;
		}
		return false;
	}
	
	public void decay() {
		decay--;
	}

}
