package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.replay.Replay;
import net.redstoneore.rcamerastudio.replay.Replays;

public class CmdCamSave extends CameraStudioPlayerCommand<CmdCamSave> {

	private static CmdCamSave instance = new CmdCamSave();
	public static CmdCamSave get() { return instance; }
	
	public CmdCamSave() {
		this.aliases("save");
		this.description("save current location as a new point");
		
		this.reqArg("name");
		
		this.permission("camerastudio.save");
	}

	@Override
	public void exec() {
		String name = this.arg(String.class, 0, null);
		
		try {
			if (Replays.get(name).isPresent()) {
				Replay replay = Replays.get(name).get();
				replay.points = this.getTraveller().getAll();
				replay.save();
				msg(GREEN, "Replay ", AQUA, name, GREEN, " updated.");
			} else {
				Replay replay = Replays.create(name);
				replay.points = this.getTraveller().getAll();
				replay.save();
				msg(GREEN, "Created replay ", AQUA, name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
