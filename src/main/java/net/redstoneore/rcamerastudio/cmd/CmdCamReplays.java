package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.replay.Replay;
import net.redstoneore.rcamerastudio.replay.Replays;

public class CmdCamReplays extends CameraStudioCommand<CmdCamReplays> {
	
	private static CmdCamReplays instance = new CmdCamReplays();
	public static CmdCamReplays get() { return instance; }
	
	public CmdCamReplays() {
		this.aliases("replays");
		this.description("list saved replays");
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
