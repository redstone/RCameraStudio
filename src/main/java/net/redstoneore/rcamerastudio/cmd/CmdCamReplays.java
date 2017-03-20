package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.replay.Replay;
import net.redstoneore.rcamerastudio.replay.Replays;
import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

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
			this.msg(RText.of("There are no saved replays.").colour(RColour.impl().RED));
			return;
		}
		
		this.msg(RText.of("Replays:").colour(RColour.impl().WHITE));

		for (Replay replay : Replays.all()) {
			this.msg(RText.of(" - ", replay.name).colour(RColour.impl().WHITE));
		}
	}
	
}
