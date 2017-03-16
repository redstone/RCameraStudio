package net.redstoneore.rcamerastudio.cmd;

public class CmdCamStop extends CameraStudioCommand<CmdCamStop> {

	private static CmdCamStop instance = new CmdCamStop();
	public static CmdCamStop get() { return instance;}
	
	public CmdCamStop() {
		this.aliases("stop");
		this.description("stop travelling");
	}

	@Override
	public void exec() {
		this.traveller().get().travelling(false);
		msg(GREEN, "Travelling has been cancelled");
	}
	
}
