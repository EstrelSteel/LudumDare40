package com.estrelsteel.ld40;

import java.awt.Graphics2D;
import java.text.DecimalFormat;

import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.window.WindowSettings;

public class LD40 implements StartListener, StopListener, TickListener, RenderListener {
	
	/*
	 * 
	 * 
	 * 
	 * BY: ESTRELSTEEL
	 * LUDUM DARE 40 - COMPO
	 * 
	 * THEME: 
	 * 
	 * (..2017)
	 * 
	 */
	
	private Launcher l;
	
	public static void main(String[] args) {
		new LD40();
	}
	
	@SuppressWarnings("static-access")
	public LD40() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 950, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 960, 640);
		}
		l.getEngine().setWindowSettings(new WindowSettings(size, "LD40 - EstrelSteel", "v1.0a", 0));
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		
		l.getEngine().ROUNDING_FORMAT = new DecimalFormat("0.##");
		
		l.getEngine().development = true;
		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD40/LD40";
//		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}

	
	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		return ctx;
	}
	
	@Override
	public void tick() {
		
	}
}
