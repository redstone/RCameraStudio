package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

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
			this.msg(RText.of("You don't have any points set yet.").colour(RColour.impl().RED));
			return;
		}
		
		Integer point = this.arg(Integer.class, 0, this.getTraveller().countPoints());
		
		if (this.getTraveller().countPoints() < point) {
			this.msg(
				RText.of("Can't remove point ").colour(RColour.impl().RED).then(
					RText.of(point).colour(RColour.impl().AQUA).then(
						RText.of(". You only have").colour(RColour.impl().RED).then(
							RText.of(this.getTraveller().countPoints()).colour(RColour.impl().AQUA).then(
								RText.of(" points").colour(RColour.impl().RED)
							)
						)
					)
				)
			);
			return;
		}
		
		this.getTraveller().removePoint(point);
		
		if (this.getTraveller().countPoints() == 0) {
			this.msg(RText.of("Point ").colour(RColour.impl().GREEN).then(
				RText.of(point).colour(RColour.impl().AQUA).then(
					RText.of(" remove. you have no points left.").colour(RColour.impl().GREEN)
				)
			));
		} else {
			this.msg(RText.of("Point ").colour(RColour.impl().GREEN).then(
				RText.of(point).colour(RColour.impl().AQUA).then(
					RText.of(" of ").colour(RColour.impl().GREEN).then(
						RText.of(this.getTraveller().countPoints()).colour(RColour.impl().AQUA).then(
							RText.of(" has been removed. ").colour(RColour.impl().RED)
						)
					)
				)
			));
		}
	}

}
