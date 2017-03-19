package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.Util;
import net.redstoneore.rcamerastudio.config.Config;

public class CmdCamStart extends CameraStudioPlayerCommand<CmdCamStart> {
	
	private static CmdCamStart instance = new CmdCamStart();
	public static CmdCamStart get() { return instance;}

	public CmdCamStart() {
		this.aliases("start");
		this.description("start traveling the current defined points");
		
		this.optArg("duration");
		this.optArg("silent");		
	}

	@Override
	public void exec() {
		Integer time = -1;
		
		try {
			time =  Util.parseTimeString(this.arg(String.class, 0, Config.get().defaultTime));
		} catch (Exception e) {
			msg(RED, "Format of that time was incorrect.");
			return;
		}
		
		Boolean silent = this.arg(Boolean.class, 1, false);
		
		if (this.getTraveller().travelling()) {
			msg(RED, "You are already travelling");
			return;
		}
		
		if (this.getTraveller().size() == 0) {
			msg(RED, "There are no points set yet.");
			return;
		}
		
		this.getTraveller().travel(time, silent);
	}
	
}

