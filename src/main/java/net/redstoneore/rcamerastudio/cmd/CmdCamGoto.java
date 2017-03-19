package net.redstoneore.rcamerastudio.cmd;

public class CmdCamGoto extends CameraStudioPlayerCommand<CmdCamGoto> {

	private static CmdCamGoto instance = new CmdCamGoto();
	public static CmdCamGoto get() { return instance; }
	
	public CmdCamGoto() {
		this.aliases("goto");
		this.description("teleport to an already defined point");
		this.reqArg("point");
	}
	
	@Override
	public void exec() {
		if (this.getTraveller().size() == 0) {
			msg(RED, "You don't have any points set yet.");
			return;
		}
		
		Integer point = this.arg(Integer.class, 0, null);
		if (point == null || point < 1) {
			msg(RED, this.arg(String.class, 0, "?"), " is not a valid number.");
			return;
		}
		
		if (this.getTraveller().size() < point) {
			msg(RED, "Point ", AQUA, point, RED, " does not exist, you only have ",  this.getTraveller().size(), " points.");
			return;
		}
		
		this.getTraveller().teleport(this.getTraveller().get(point -1));
		
		msg(GREEN, "Teleported to point ", AQUA, point, GREEN, " of ", AQUA, this.getTraveller().size());
	}

}
