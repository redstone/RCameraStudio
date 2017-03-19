package net.redstoneore.rcamerastudio.replay;

import java.util.ArrayList;
import java.util.List;

import net.redstoneore.rcamerastudio.Loc;
import net.redstoneore.rson.Rson;

public class Replay extends Rson<Replay> {

	public String name = "";
	public List<Loc> points = new ArrayList<Loc>();
	
	
}
