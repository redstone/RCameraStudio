package eu.crushedpixel.camerastudio.cmd;

import eu.crushedpixel.camerastudio.replay.Replay;
import eu.crushedpixel.camerastudio.replay.Replays;

public class CmdCamReplays extends CameraStudioCommand<CmdCamReplays> {
	
	private static CmdCamReplays instance = new CmdCamReplays();
	public static CmdCamReplays get() { return instance; }
	
	public CmdCamReplays() {
		this.aliases("replays");
		this.permission("camerastudio.replay");
	}
	
	@Override
	public void exec() {
		if (Replays.all().isEmpty()) {
			msg(RED, "There are no saved replays.");
			return;
		}
		
		msg(WHITE, BOLD, "Existing: Replays:");
		for (Replay replay : Replays.all()) {
			msg(WHITE, " - " + replay.name);
		}
		msg(" ");
	}
	
}
