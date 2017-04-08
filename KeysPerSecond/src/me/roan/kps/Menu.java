package me.roan.kps;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import me.roan.kps.Main.Key;
import me.roan.kps.Main.KeyInformation;

public class Menu {
	
	/**
	 * The right click menu
	 */
	protected static final JPopupMenu menu = new JPopupMenu();
	
	private static final JMenu configure = new JMenu("Configure");
	private static final JMenu general = new JMenu("General");
	private static final JMenuItem snap = new JMenuItem("Snap to edges");
	private static final JMenuItem exit = new JMenuItem("Exit");
	private static final JMenuItem pause = new JMenuItem("Pause/resume");
	private static final JMenuItem sreset = new JMenuItem("Reset stats");
	private static final JMenuItem treset = new JMenuItem("Reset totals");
	private static final JMenuItem tAll = new JCheckBoxMenuItem("Track all keys");
	private static final JMenuItem overlay = new JCheckBoxMenuItem("Overlay osu!");
	private static final JMenuItem precision = new JMenu("Precision");
	private static final JCheckBoxMenuItem p0 = new JCheckBoxMenuItem("No digits beyond the decimal point");
	private static final JCheckBoxMenuItem p1 = new JCheckBoxMenuItem("1 digit beyond the decimal point");
	private static final JCheckBoxMenuItem p2 = new JCheckBoxMenuItem("2 digits beyond the decimal point");
	private static final JCheckBoxMenuItem p3 = new JCheckBoxMenuItem("3 digits beyond the decimal point");
	private static final JCheckBoxMenuItem max = new JCheckBoxMenuItem("Show max");
	private static final JCheckBoxMenuItem avg = new JCheckBoxMenuItem("Show average");
	private static final JCheckBoxMenuItem cur = new JCheckBoxMenuItem("Show current");
	private static final JCheckBoxMenuItem graph = new JCheckBoxMenuItem("Show graph");
	private static final JCheckBoxMenuItem keys = new JCheckBoxMenuItem("Show keys");
	
	protected static final void repaint(){
		snap.setForeground(Main.config.foreground);
		exit.setForeground(Main.config.foreground);
		pause.setForeground(Main.config.foreground);
		menu.setForeground(Main.config.foreground);
		menu.setBackground(Main.config.background);
		treset.setForeground(Main.config.foreground);
		sreset.setForeground(Main.config.foreground);
		tAll.setSelected(Main.config.trackAll);
		
		menu.setBorder(BorderFactory.createLineBorder(Main.config.foreground));
	}

	protected static final void createMenu(){
		snap.addActionListener((e)->{
			Point loc = Main.frame.getLocationOnScreen();
			Rectangle bounds = Main.frame.getGraphicsConfiguration().getBounds();	
			Main.frame.setLocation(Math.abs(loc.x - bounds.x) < 100 ? bounds.x : 
							  Math.abs((loc.x + Main.frame.getWidth()) - (bounds.x + bounds.width)) < 100 ? bounds.x + bounds.width - Main.frame.getWidth() : loc.x, 
							  Math.abs(loc.y - bounds.y) < 100 ? bounds.y : 
							  Math.abs((loc.y + Main.frame.getHeight()) - (bounds.y + bounds.height)) < 100 ? bounds.y + bounds.height - Main.frame.getHeight() : loc.y);
		});
		exit.addActionListener((e)->{
			Main.exit();
		});
		pause.addActionListener((e)->{
			Main.suspended = !Main.suspended;
		});
		sreset.addActionListener((e)->{
			Main.resetStats();
		});
		treset.addActionListener((e)->{
			Main.resetTotals();
		});
		tAll.setSelected(Main.config.trackAll);
		tAll.addActionListener((e)->{
			Main.config.trackAll = tAll.isSelected();
			Iterator<Entry<Integer, Key>> iter = Main.keys.entrySet().iterator();
			while(iter.hasNext()){
				Entry<Integer, Key> key = iter.next();
				for(KeyInformation info : Main.config.keyinfo){
					if(info.keycode == key.getKey()){
						continue;
					}
				}
				iter.remove();
			}
		});
		overlay.setSelected(Main.config.overlay);
		overlay.addActionListener((e)->{
			Main.config.overlay = overlay.isSelected();
			Main.frame.setAlwaysOnTop(Main.config.overlay);
		});
		precision.add(p0);
		precision.add(p1);
		precision.add(p2);
		precision.add(p3);
		p0.addActionListener((e)->{
			Main.config.precision = 0;
			p0.setSelected(true);
			p1.setSelected(false);
			p2.setSelected(false);
			p3.setSelected(false);
		});
		p1.addActionListener((e)->{
			Main.config.precision = 1;
			p0.setSelected(false);
			p1.setSelected(true);
			p2.setSelected(false);
			p3.setSelected(false);
		});
		p2.addActionListener((e)->{
			Main.config.precision = 2;
			p0.setSelected(false);
			p1.setSelected(false);
			p2.setSelected(true);
			p3.setSelected(false);
		});
		p3.addActionListener((e)->{
			Main.config.precision = 3;
			p0.setSelected(false);
			p1.setSelected(false);
			p2.setSelected(false);
			p3.setSelected(true);
		});
		switch(Main.config.precision){
		case 0:
			p0.setSelected(true);
			break;
		case 1:
			p1.setSelected(true);
			break;
		case 2:
			p2.setSelected(true);
			break;
		case 3:
			p3.setSelected(true);
			break;
		}
		max.setSelected(Main.config.showMax);
		max.addActionListener((e)->{
			Main.config.showMax = max.isSelected();
			Main.reconfigure();
		});
		avg.setSelected(Main.config.showAvg);
		avg.addActionListener((e)->{
			Main.config.showAvg = avg.isSelected();
			Main.reconfigure();
		});
		cur.setSelected(Main.config.showCur);
		cur.addActionListener((e)->{
			Main.config.showCur = cur.isSelected();
			Main.reconfigure();
		});
		keys.setSelected(Main.config.showKeys);
		keys.addActionListener((e)->{
			Main.config.showKeys = keys.isSelected();
			Main.reconfigure();
		});
		graph.setSelected(Main.config.showGraph);
		graph.addActionListener((e)->{
			Main.config.showGraph = graph.isSelected();
			Main.reconfigure();
		});
		
		general.add(max);
		general.add(avg);
		general.add(cur);
		general.add(keys);
		general.add(graph);
		general.add(overlay);
		general.add(tAll);
		
		configure.add(general);
		configure.add(precision);
		
		menu.add(configure);
		menu.add(snap);
		menu.add(treset);
		menu.add(sreset);
		menu.add(pause);
		menu.add(exit);
	}
}
