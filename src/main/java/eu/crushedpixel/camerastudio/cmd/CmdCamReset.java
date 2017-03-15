package eu.crushedpixel.camerastudio.cmd;

public class CmdCamReset extends CameraStudioCommand<CmdCamReset> {

	private static CmdCamReset instance = new CmdCamReset();
	public static CmdCamReset get() { return instance; }
	
	public CmdCamReset() {
		this.aliases("res", "reset");
	}

	@Override
	public void exec() {
		this.points().get().clear();
		msg(GREEN, "Successfully removed all points");
	}
	
}
