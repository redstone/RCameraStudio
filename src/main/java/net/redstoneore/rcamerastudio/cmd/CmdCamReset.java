package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public class CmdCamReset extends CameraStudioPlayerCommand<CmdCamReset> {

	private static CmdCamReset instance = new CmdCamReset();
	public static CmdCamReset get() { return instance; }
	
	public CmdCamReset() {
		this.aliases("reset");
		this.description("reset current points");
	}

	@Override
	public void exec() {
		this.getTraveller().clearPoints();
		this.msg(RText.of("Successfully removed all points.").colour(RColour.impl().GREEN));
	}
	
}
