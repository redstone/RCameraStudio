package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.replay.Replays;
import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public class CmdCamLoad extends CameraStudioPlayerCommand<CmdCamLoad> {

	private static CmdCamLoad instance = new CmdCamLoad();
	public static CmdCamLoad get() { return instance; }
	
	public CmdCamLoad() {
		this.aliases("load");
		this.description("load points from a replay");
		
		this.reqArg("name");
		
		this.permission("camerastudio.load");
	}

	@Override
	public void exec() {
		String name = this.arg(String.class, 0, null);
		
		if (!Replays.get(name).isPresent()) {
			this.msg(RText.of(name, " is not an existing replay.").colour(RColour.impl().RED));
			return;
		}
		
		this.getTraveller().set(Replays.get(name));
		this.msg(RText.of(name, " loaded!").colour(RColour.impl().GREEN));
	}
	
}
