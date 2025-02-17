/*
 * KeysPerSecond: An open source input statistics displayer.
 * Copyright (C) 2017  Roan Hofland (roan@roanh.dev).  All rights reserved.
 * GitHub Repository: https://github.com/RoanH/KeysPerSecond
 *
 * KeysPerSecond is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * KeysPerSecond is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dev.roanh.kps;

import dev.roanh.kps.panels.KeyPanel;

/**
 * This class is used to keep track
 * of how many times a specific key
 * is pressed
 * @author Roan
 */
public class Key{
	/**
	 * Whether or not this key is currently pressed
	 */
	public transient boolean down = false;
	/**
	 * The total number of times this key has been pressed
	 */
	public int count = 0;
	/**
	 * The key in string form<br>
	 * For example: X
	 */
	public String name;
	/**
	 * The graphical display for this key
	 */
	private transient KeyPanel panel = null;
	/**
	 * Whether or not alt has to be down
	 */
	protected boolean alt;
	/**
	 * Whether or not ctrl has to be down
	 */
	protected boolean ctrl;
	/**
	 * Whether or not shift has to be down
	 */
	protected boolean shift;
	
	/**
	 * Constructs a new Key object with the given name,
	 * hit count and modifier keys.
	 * @param name The name of this key.
	 * @param count The number of times this key was hit so far.
	 * @param alt Whether alt has to be down for this key.
	 * @param ctrl Whether ctrl has to be down for this key.
	 * @param shift Whether shift has to be down for this key.
	 */
	protected Key(String name, int count, boolean alt, boolean ctrl, boolean shift){
		this(name);
		this.count = count;
		this.alt = alt;
		this.ctrl = ctrl;
		this.shift = shift;
	}

	/**
	 * Constructs a new Key object
	 * for the key with the given
	 * name
	 * @param name The name of the key
	 * @see #name
	 */
	protected Key(String name){
		this.name = name;
	}

	/**
	 * Creates a new KeyPanel with this
	 * objects as its data source
	 * @param i The information object for this key
	 * @return A new KeyPanel
	 */
	protected KeyPanel getPanel(KeyInformation i){
		return panel != null ? panel : (panel = new KeyPanel(this, i));
	}

	/**
	 * Called when a key is pressed
	 */
	protected void keyPressed(){
		if(!down){
			count++;
			down = true;
			Main.tmp.incrementAndGet();
			if(panel != null){
				panel.repaint();
			}
		}
	}

	/**
	 * Called when a key is released
	 */
	protected void keyReleased(){
		if(down){
			down = false;
			if(panel != null){
				panel.repaint();
			}
		}
	}
}
