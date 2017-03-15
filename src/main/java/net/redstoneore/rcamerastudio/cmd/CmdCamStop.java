package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.RCameraStudio;

public class CmdCamStop extends CameraStudioCommand<CmdCamStop> {

	private static CmdCamStop instance = new CmdCamStop();
	public static CmdCamStop get() { return instance;}
	
	public CmdCamStop() {
		this.aliases("stop");
		this.description("stop travelling");
	}

	@Override
	public void exec() {
		RCameraStudio.stop(this.player().get());
		msg(GREEN, "Travelling has been cancelled");
	}
	
}
