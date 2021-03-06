package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.Util;
import net.redstoneore.rcamerastudio.replay.Replay;
import net.redstoneore.rcamerastudio.replay.Replays;
import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public class CmdCamReplay extends CameraStudioPlayerCommand<CmdCamReplay> {

	private static CmdCamReplay instance = new CmdCamReplay();
	public static CmdCamReplay get() { return instance; }
	
	public CmdCamReplay() {
		this.aliases("replay");
		this.description("run a replay without loading it");
		
		this.permission("camerastudio.replay");
		
		this.reqArg("name");
		
		// TODO: properly implement time and silent
		this.optArg("time");
		this.optArg("silent");
		
	}

	@Override
	public void exec() {
		String replayName = this.arg(String.class, 0, null);
		
		if (replayName == null || !Replays.get(replayName).isPresent()) {
			this.msg(RText.of("Unknown replay ", replayName).colour(RColour.impl().RED));
			return;
		}
		
		Replay replay = Replays.get(replayName).get();
		
		Integer time;
		
		try {
			time =  Util.parseTimeString(this.arg(String.class, 1, "1m"));
		} catch (Exception e) {
			this.msg(RText.of("The format of that time was incorrect.").colour(RColour.impl().RED));
			return;
		}
		
		Boolean silent = this.arg(Boolean.class, 2, false);
		
		this.getTraveller().travel(replay.points, time, silent);
	}
	
}
