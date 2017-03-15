package eu.crushedpixel.camerastudio.cmd;

import org.bukkit.Location;

import eu.crushedpixel.camerastudio.Util;

public class CmdCamList extends CameraStudioCommand<CmdCamList> {

	private static CmdCamList instance = new CmdCamList();
	public static CmdCamList get() { return instance; }
	
	public CmdCamList() {
		this.aliases("l", "list");
	}
	
	@Override
	public void exec() {
		if (this.points().get().size() == 0) {
			msg(RED, "You don't have any points set yet.");
			return;
		}

		int pointNo = 1;
		for (Location loc : this.points().get().getAll()) {
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
