package eu.crushedpixel.camerastudio.cmd;

public class CmdCamGoto extends CameraStudioCommand<CmdCamGoto> {

	private static CmdCamGoto instance = new CmdCamGoto();
	public static CmdCamGoto get() { return instance; }
	
	public CmdCamGoto() {
		this.aliases("g", "goto");
		
		this.reqArg("point");
	}
	
	@Override
	public void exec() {
		if (this.points().get().size() == 0) {
			msg(RED, "You don't have any points set yet.");
			return;
		}
		
		Integer point = this.arg(Integer.class, 0, null);
		if (point == null) {
			msg(RED, this.arg(String.class, 0, "?"), " is not a number");
			return;
		}
		
		if (this.points().get().size() < point) {
			msg(RED, "Point ", AQUA, point, RED, " does not exist, you only have ",  this.points().get().size(), " points.");
			return;
		}
		
		this.player().get().teleport(this.points().get().get(point -1));
		
		msg(GREEN, "Teleported to point ", point);
	}

}
