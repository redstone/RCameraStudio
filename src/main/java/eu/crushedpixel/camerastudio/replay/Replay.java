package eu.crushedpixel.camerastudio.replay;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import net.redstoneore.rson.Rson;

public class Replay extends Rson<Replay> {

	public String name = "";
	public List<Location> points = new ArrayList<Location>();
	
	
}
