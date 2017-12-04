package com.estrelsteel.ld40.actors;

import java.awt.Color;
import java.util.ArrayList;

import com.estrelsteel.engine2.actor.Text;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.file.Saveable;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.ld40.LDWorld;
import com.estrelsteel.ld40.actors.guard.AISwitch;
import com.estrelsteel.ld40.actors.guard.Guard;

public class ActorLoader {

	private ArrayList<Saveable> actors;
	
	public ActorLoader() {
		actors = new ArrayList<Saveable>();
		
		actors.add(new AISwitch(null, 0, 0));
		actors.add(new Guard(null, null, 0, 0));
		actors.add(new Player(null, null));
		actors.add(new LaserField(null, null));
		actors.add(new Button(null, null));
		actors.add(new Door(null, null));
		actors.add(new Text(null, null));
		actors.add(new Exit(null));
	}
	
	public String getIdentifier() {
		return "ACT";
	}

	public LDWorld load(GameFile file, int line, LDWorld w) {
		if(file.getLines().get(line).equalsIgnoreCase(getIdentifier())) {
			String args[];
			Object s;
			for(int i = ++line; i < file.getLines().size(); i++) {
				args = file.getLines().get(i).split(" ");
				for(int j = 0; j < actors.size(); j++) {
					if(actors.get(j).getIdentifier().trim().equalsIgnoreCase(args[0].trim())) {
						s = actors.get(j).load(file, i);
						if(s instanceof Gunman) {
							w.getObjects().add((Gunman) s);
							w.getGunmen().add((Gunman) s);
						}
						else if(s instanceof AISwitch) {
							w.getAiSwitches().add((AISwitch) s);
						}
						else if(s instanceof Text) {
							((Text) s).setColour(Color.WHITE);
							w.getText().add((Text) s);
						}
						else if(s instanceof Renderable) {
							w.getObjects().add((Renderable) s);
						}
						break;
					}
				}
			}
		}
		return w;
	}

	public GameFile save(GameFile file) {
		return null;
	}

}
