package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.replay.Replays;

public class CmdCamLoad extends CameraStudioCommand<CmdCamLoad> {

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
			msg(RED, name + " is not an existing save.");
			return;
		}
		
		this.points().get().pointsList = Replays.get(name).get().points;
		msg(GREEN, name + " loaded!");

	}
	
}
