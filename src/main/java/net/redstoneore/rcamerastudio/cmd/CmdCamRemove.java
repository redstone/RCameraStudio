package net.redstoneore.rcamerastudio.cmd;

public class CmdCamRemove extends CameraStudioPlayerCommand<CmdCamRemove> {

	private static CmdCamRemove instance = new CmdCamRemove();
	public static CmdCamRemove get() { return instance; }
	
	public CmdCamRemove() {
		this.aliases("remove");
		this.description("remove a point");
		
		this.optArg("number");
	}
	
	@Override
	public void exec() {
		if (this.getTraveller().countPoints() == 0) {
			msg(RED, "You don't have any points set yet.");
			return;
		}
		
		Integer point = this.arg(Integer.class, 0, this.getTraveller().countPoints());
		
		if (this.getTraveller().countPoints() < point) {
			msg(RED, "Can't remove point", AQUA, point, RED, ". You only have ", AQUA, this.getTraveller().countPoints(), RED, " points.");
			return;
		}
		
		this.getTraveller().removePoint(point);
		
		if (this.getTraveller().countPoints() == 0) {
			msg(GREEN, "Point ", AQUA, point, GREEN, " removed. You have no points left.");
		} else {
			msg(GREEN, "Point ", AQUA, point, GREEN, " of ", AQUA, this.getTraveller().countPoints(), GREEN, " has been removed.");
		}
	}

}
