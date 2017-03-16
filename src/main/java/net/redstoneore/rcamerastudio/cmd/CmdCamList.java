package net.redstoneore.rcamerastudio.cmd;

import org.bukkit.Location;

import net.redstoneore.rcamerastudio.Util;

public class CmdCamList extends CameraStudioCommand<CmdCamList> {

	private static CmdCamList instance = new CmdCamList();
	public static CmdCamList get() { return instance; }
	
	public CmdCamList() {
		this.aliases("list");
		this.description("list of defined points");
	}
	
	@Override
	public void exec() {
		if (this.traveller().get().size() == 0) {
			msg(RED, "You don't have any points set yet.");
			return;
		}

		int pointNo = 1;
		for (Location loc : this.traveller().get().getAll()) {
			Double x = Util.round(loc.getX(), 1);
			Double y = Util.round(loc.getX(), 1);
			Double z = Util.round(loc.getX(), 1);
			
			Double yaw = Util.round(loc.getYaw(), 1);
			Double pitch = Util.round(loc.getPitch(), 1);
			
			msg(WHITE, "Point ", AQUA, pointNo, WHITE, ": ", x, ", ", y, ", ", z , 
				" (", yaw, ", ", pitch, ")");
			
			pointNo++;
		}		
	}

}
