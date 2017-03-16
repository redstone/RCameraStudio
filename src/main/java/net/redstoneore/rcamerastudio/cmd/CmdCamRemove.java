package net.redstoneore.rcamerastudio.cmd;

public class CmdCamRemove extends CameraStudioCommand<CmdCamRemove> {

	private static CmdCamRemove instance = new CmdCamRemove();
	public static CmdCamRemove get() { return instance; }
	
	public CmdCamRemove() {
		this.aliases("remove");
		this.description("remove a point");
		
		this.optArg("number");
	}
	
	@Override
	public void exec() {
		if (this.traveller().get().size() == 0) {
			msg(RED, "You don't have any points set yet.");
			return;
		}
		
		Integer point = this.arg(Integer.class, 0, this.traveller().get().size());
		Integer index = point - 1;
		
		if (this.traveller().get().size() < index) {
			msg(RED + "You only have ", AQUA, this.traveller().get().size(), RED, " points.");
			return;
		}
		
		this.traveller().get().remove(index);
		
		if (this.traveller().get().size() == 0) {
			msg(GREEN, "All your points have been removed.");
		} else {
			msg(GREEN, "Point ", AQUA, point, GREEN, " of ", AQUA, this.traveller().get().size(), GREEN, " has been removed.");
		}
	}

}
