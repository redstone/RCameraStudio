package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.Loc;
import net.redstoneore.rcamerastudio.Util;
import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public class CmdCamList extends CameraStudioPlayerCommand<CmdCamList> {

	private static CmdCamList instance = new CmdCamList();
	public static CmdCamList get() { return instance; }
	
	public CmdCamList() {
		this.aliases("list");
		this.description("list of defined points");
	}
	
	@Override
	public void exec() {
		if (this.getTraveller().countPoints() == 0) {
			msg(RText.of("You don't have any points set yet.").colour(RColour.impl().RED));
			return;
		}

		int pointNo = 1;
		for (Loc loc : this.getTraveller().getPoints()) {
			Double x = Util.round(loc.getX(), 1);
			Double y = Util.round(loc.getX(), 1);
			Double z = Util.round(loc.getX(), 1);
			
			Double yaw = Util.round(loc.getYaw(), 1);
			Double pitch = Util.round(loc.getPitch(), 1);
			
			RText pointText = RText.of("Point ").colour(RColour.impl().WHITE);
			pointText.then(RText.of(pointNo).colour(RColour.impl().AQUA));
			pointText.then(RText.of(
				": ", 
				x, ", ", y, ", ", z , 
				" (", yaw, ", ", pitch, ")"
			).colour(RColour.impl().WHITE));
			
			this.msg(pointText);
			
			pointNo++;
		}		
	}

}
