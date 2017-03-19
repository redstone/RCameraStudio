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
		if (this.getTraveller().size() == 0) {
			msg(RED, "You don't have any points set yet.");
			return;
		}
		
		Integer point = this.arg(Integer.class, 0, this.getTraveller().size());
		Integer index = point - 1;
		
		if (this.getTraveller().size() < index) {
			msg(RED, "Can't remove point", AQUA, index, RED, ". You only have ", AQUA, this.getTraveller().size(), RED, " points.");
			return;
		}
		
		this.getTraveller().remove(index);
		
		if (this.getTraveller().size() == 0) {
			msg(GREEN, "All your points have been removed.");
		} else {
			msg(GREEN, "Point ", AQUA, point, GREEN, " of ", AQUA, this.getTraveller().size(), GREEN, " has been removed.");
		}
	}

}
