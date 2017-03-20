package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.Util;
import net.redstoneore.rcamerastudio.config.Config;
import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

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
			this.msg(RText.of("Format of that time was incorrect.").colour(RColour.impl().RED));
			return;
		}
		
		Boolean silent = this.arg(Boolean.class, 1, false);
		
		if (this.getTraveller().travelling()) {
			this.msg(RText.of("Your are already travelling.").colour(RColour.impl().RED));
			return;
		}
		
		if (this.getTraveller().countPoints() == 0) {
			this.msg(RText.of("There are no points set.").colour(RColour.impl().RED));
			return;
		}
		
		this.getTraveller().travel(time, silent);
	}
	
}

