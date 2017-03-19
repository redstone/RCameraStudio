package net.redstoneore.rcamerastudio.cmd;

public class CmdCamPoint extends CameraStudioPlayerCommand<CmdCamPoint> {

	private static CmdCamPoint instance = new CmdCamPoint();
	public static CmdCamPoint get() { return instance; }
	
	public CmdCamPoint() {
		this.aliases("point", "p");
		this.description("store a point");
	}

	@Override
	public void exec() {
		this.getTraveller().add(this.getTraveller().getLoc());
		
		msg(GREEN, "Point ", AQUA, this.getTraveller().size(), GREEN, " has been set.");
	}
	
}
