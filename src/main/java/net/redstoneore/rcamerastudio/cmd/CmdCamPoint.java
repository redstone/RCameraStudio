package net.redstoneore.rcamerastudio.cmd;

import org.bukkit.entity.Player;


public class CmdCamPoint extends CameraStudioCommand<CmdCamPoint> {

	private static CmdCamPoint instance = new CmdCamPoint();
	public static CmdCamPoint get() { return instance; }
	
	public CmdCamPoint() {
		this.aliases("point", "p");
		this.description("store a point");
	}

	@Override
	public void exec() {
		Player player = this.player().get();
		
		this.points().get().add(player.getLocation());
		
		msg(GREEN, "Point ", AQUA, this.points().get().size(), GREEN, " has been set.");
		
	}
	
}
