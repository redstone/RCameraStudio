package net.redstoneore.rcamerastudio.cmd;

public class CmdCamGoto extends CameraStudioCommand<CmdCamGoto> {

	private static CmdCamGoto instance = new CmdCamGoto();
	public static CmdCamGoto get() { return instance; }
	
	public CmdCamGoto() {
		this.aliases("goto");
		this.description("teleport to an already defined point");
		this.reqArg("point");
	}
	
	@Override
	public void exec() {
		if (this.traveller().get().size() == 0) {
			msg(RED, "You don't have any points set yet.");
			return;
		}
		
		Integer point = this.arg(Integer.class, 0, null);
		if (point == null) {
			msg(RED, this.arg(String.class, 0, "?"), " is not a number");
			return;
		}
		
		if (this.traveller().get().size() < point) {
			msg(RED, "Point ", AQUA, point, RED, " does not exist, you only have ",  this.traveller().get().size(), " points.");
			return;
		}
		
		this.player().get().teleport(this.traveller().get().get(point -1));
		
		msg(GREEN, "Teleported to point ", point);
	}

}
