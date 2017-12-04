package com.estrelsteel.ld40.actors;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.file.Saveable;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.Rotation;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Button extends Actor implements Saveable {

	private boolean pressed;
	
	public Button(String name, Rectangle loc) {
		super(name, loc);
		setPressed(false);
		
		getAnimations().add(0, new Animation("button_unpressed", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(6 * 16, 0 * 16, 16, 16)));
		getAnimations().add(1, new Animation("button_pressed", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(7 * 16, 0 * 16, 16, 16)));

		setCollision(new Collision(true, new RectangleCollideArea(loc)));
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	@Override
	public String getIdentifier() {
		return "BUT";
	}

	@Override
	public Object load(GameFile file, int line) {
		String args[] = file.getLines().get(line).split(" ");
		if(args[0].equalsIgnoreCase(getIdentifier())) {
			Button b = new Button(args[1], QuickRectangle.location(Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5])));
			b.setRunningAnimationNumber(0);
			b.setRotation(new Rotation(Double.parseDouble(args[6])));
			return b;
			
		}
		return null;
	}

	@Override
	public GameFile save(GameFile file) {
		return file;
	}

}
