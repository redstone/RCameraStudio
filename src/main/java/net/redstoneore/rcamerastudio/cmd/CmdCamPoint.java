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
		
		this.traveller().get().add(player.getLocation());
		
		msg(GREEN, "Point ", AQUA, this.traveller().get().size(), GREEN, " has been set.");
		
	}
	
}
