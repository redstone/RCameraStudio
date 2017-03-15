package eu.crushedpixel.camerastudio.cmd;

public class CmdCamHelp extends CameraStudioCommand<CmdCamHelp> {

	private static CmdCamHelp instance = new CmdCamHelp();
	public static CmdCamHelp get() { return instance; }
	
	public CmdCamHelp() {
		this.aliases("help");
	}

	@Override
	public void exec() {
		msg(RED, "Help menu is not setup yet.");
	}
}
