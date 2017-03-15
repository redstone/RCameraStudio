package eu.crushedpixel.camerastudio.cmd;

import eu.crushedpixel.camerastudio.CameraStudio;
import eu.crushedpixel.camerastudio.Util;
import eu.crushedpixel.camerastudio.replay.Replay;
import eu.crushedpixel.camerastudio.replay.Replays;

public class CmdCamReplay extends CameraStudioCommand<CmdCamReplay> {

	private static CmdCamReplay instance = new CmdCamReplay();
	public static CmdCamReplay get() { return instance; }
	
	public CmdCamReplay() {
		this.aliases("replay");
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
			msg(RED, "Unknown replay ", replayName);
			return;
		}
		
		Replay replay = Replays.get(replayName).get();
		
		Integer time;
		
		try {
			time =  Util.parseTimeString(this.arg(String.class, 1, "1m30s"));
		} catch (Exception e) {
			msg(RED, "Format of that time was incorrect.");
			return;
		}
		
		Boolean silent = this.arg(Boolean.class, 2, false);
		
		CameraStudio.travel(this.player().get(), replay.points, time, silent);
	}
	
}
