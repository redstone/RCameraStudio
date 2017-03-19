package net.redstoneore.rcamerastudio.cmd;

public class CmdCamReset extends CameraStudioPlayerCommand<CmdCamReset> {

	private static CmdCamReset instance = new CmdCamReset();
	public static CmdCamReset get() { return instance; }
	
	public CmdCamReset() {
		this.aliases("reset");
		this.description("reset current points");
	}

	@Override
	public void exec() {
		this.getTraveller().clear();
		msg(GREEN, "Successfully removed all points");
	}
	
}
