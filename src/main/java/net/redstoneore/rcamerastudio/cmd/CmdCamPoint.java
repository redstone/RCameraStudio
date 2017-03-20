package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public class CmdCamPoint extends CameraStudioPlayerCommand<CmdCamPoint> {

	private static CmdCamPoint instance = new CmdCamPoint();
	public static CmdCamPoint get() { return instance; }
	
	public CmdCamPoint() {
		this.aliases("point", "p");
		this.description("store a point");
	}

	@Override
	public void exec() {
		this.getTraveller().addPoint(this.getTraveller().getLoc());
		
		this.msg(
			RText.of("Point ").colour(RColour.impl().GREEN).then(
				RText.of(this.getTraveller().countPoints()).colour(RColour.impl().AQUA).then(
					RText.of(" has been set.").colour(RColour.impl().GREEN)
				)
			)
		);	
	}
	
}
