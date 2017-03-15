package eu.crushedpixel.camerastudio.cmd;

import java.util.List;

import org.bukkit.Location;

import eu.crushedpixel.camerastudio.CameraStudio;
import eu.crushedpixel.camerastudio.Util;

public class CmdCamStart extends CameraStudioCommand<CmdCamStart> {
	
	private static CmdCamStart instance = new CmdCamStart();
	public static CmdCamStart get() { return instance;}

	public CmdCamStart() {
		this.aliases("start");
		
		this.optArg("duration");
		this.optArg("silent");		
	}

	@Override
	public void exec() {
		Integer time = -1;
		
		try {
			time =  Util.parseTimeString(this.arg(String.class, 0, "1m30s"));
		} catch (Exception e) {
			msg(RED, "Format of that time was incorrect.");
			return;
		}
		
		Boolean silent = this.arg(Boolean.class, 1, false);
		
		if (CameraStudio.isTravelling(this.player().get())) {
			msg(RED, "You are already travelling");
			return;
		}
		
		if (this.points().get().size() == 0) {
			msg(RED, "There are no points set yet.");
			return;
		}
		
		List<Location> locations = this.points().get().getAll();
		
		CameraStudio.travel(this.player().get(), locations, time, silent);
	}
	
}

