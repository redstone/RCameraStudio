package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public class CmdCamGoto extends CameraStudioPlayerCommand<CmdCamGoto> {

	private static CmdCamGoto instance = new CmdCamGoto();
	public static CmdCamGoto get() { return instance; }
	
	public CmdCamGoto() {
		this.aliases("goto");
		this.description("teleport to an already defined point");
		this.reqArg("point");
	}
	
	@Override
	public void exec() {
		if (this.getTraveller().countPoints() == 0) {
			this.msg(RText.of("You don't have any points set yet.").colour(RColour.impl().RED));
			return;
		}
		
		Integer point = this.arg(Integer.class, 0, null);
		if (point == null || point < 1) {
			this.msg(RText.of(this.arg(String.class, 0, "?"), " is not a valid number.").colour(RColour.impl().RED));
			return;
		}
		
		if (this.getTraveller().countPoints() < point) {
			this.msg(RText.of("Point ").colour(RColour.impl().RED).then(
				RText.of(point).colour(RColour.impl().AQUA).then(
					RText.of(" does not exist, you only have ").colour(RColour.impl().RED).then(
						RText.of(this.getTraveller().countPoints()).colour(RColour.impl().AQUA).then(
							RText.of(" points.").colour(RColour.impl().RED)
						)
					)
				)
			));
			
			return;
		}
		
		this.getTraveller().teleport(this.getTraveller().get(point -1));
		
		this.msg(RText.of("Teleported to point ").colour(RColour.impl().GREEN).then(
			RText.of(point).colour(RColour.impl().AQUA).then(
				RText.of(" of ").colour(RColour.impl().RED).then(
					RText.of(this.getTraveller().countPoints()).colour(RColour.impl().AQUA)
				)
			)
		));
	}

}
