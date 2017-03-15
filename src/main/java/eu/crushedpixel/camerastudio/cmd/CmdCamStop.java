package eu.crushedpixel.camerastudio.cmd;

import eu.crushedpixel.camerastudio.CameraStudio;

public class CmdCamStop extends CameraStudioCommand<CmdCamStop> {

	private static CmdCamStop instance = new CmdCamStop();
	public static CmdCamStop get() { return instance;}
	
	public CmdCamStop() {
		this.aliases("stop");
	}

	@Override
	public void exec() {
		CameraStudio.stop(this.player().get());
		msg(GREEN, "Travelling has been cancelled");
	}
	
}
