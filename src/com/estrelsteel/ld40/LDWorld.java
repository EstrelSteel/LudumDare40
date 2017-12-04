package com.estrelsteel.ld40;

import java.util.ArrayList;

import com.estrelsteel.engine2.actor.Text;
import com.estrelsteel.engine2.grid.Grid;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.world.World;

public class LDWorld extends World {

	private ArrayList<Renderable> ai_switch;
	private ArrayList<Renderable> gunmen;
	private ArrayList<Text> text;
	
	public LDWorld(Grid grid) {
		super(grid);
		
		ai_switch = new ArrayList<Renderable>();
		gunmen = new ArrayList<Renderable>();
		setText(new ArrayList<Text>());
	}

	public ArrayList<Renderable> getAiSwitches() {
		return ai_switch;
	}
	
	public void setAiSwitches(ArrayList<Renderable> ai_switch) {
		this.ai_switch = ai_switch;
	}

	public ArrayList<Renderable> getGunmen() {
		return gunmen;
	}

	public void setGunmen(ArrayList<Renderable> gunmen) {
		this.gunmen = gunmen;
	}

	public ArrayList<Text> getText() {
		return text;
	}

	public void setText(ArrayList<Text> text) {
		this.text = text;
	}
}
