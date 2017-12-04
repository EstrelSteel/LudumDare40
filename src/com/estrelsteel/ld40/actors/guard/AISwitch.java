package com.estrelsteel.ld40.actors.guard;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.file.Saveable;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class AISwitch extends Actor implements Saveable {

	private double x;
	private double y;
	
	public AISwitch(Rectangle loc, double x, double y) {
		super("ai_switch", loc);
		
		getAnimations().add(new Animation("test", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/game.png", QuickRectangle.location(0, 0, 16, 16)));
		
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String getIdentifier() {
		return "AIS";
	}

	@Override
	public Object load(GameFile file, int line) {
		String args[] = file.getLines().get(line).split(" ");
		if(args[0].equalsIgnoreCase(getIdentifier())) {
			return new AISwitch(QuickRectangle.location(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])), 
					Double.parseDouble(args[5]), Double.parseDouble(args[6]));
		}
		return null;
	}

	@Override
	public GameFile save(GameFile file) {
		return file;
	}

}
