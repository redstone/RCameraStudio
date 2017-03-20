package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public class CmdCamHelp extends CameraStudioCommand<CmdCamHelp> {

	private static CmdCamHelp instance = new CmdCamHelp();
	public static CmdCamHelp get() { return instance; }
	
	public CmdCamHelp() {
		this.aliases("help");
	}

	@Override
	public void exec() {
		this.msg(
			RText.of(" [ --------------- ").colour(RColour.impl().DARK_AQUA).then(
				RText.of("RCameraStudio").colour(RColour.impl().AQUA).then(
					RText.of(" --------------- ]").colour(RColour.impl().DARK_AQUA)
				)
			)
		);
		
		this.msg(
			RText.of("  /cam ").colour(RColour.impl().AQUA).then(
				RText.of(CmdCam.get().description()).colour(RColour.impl().WHITE)
			)
		);
		
		for (CameraStudioCommand<?> cmd : CmdCam.get().subcommands()) {
			RText line = RText.of("  /cam").colour(RColour.impl().AQUA);
			
			line.then(RText.of(" ", cmd.aliases().get(0)).colour(RColour.impl().AQUA));
			
			for (String arg : cmd.reqArg()) {
				line.then(RText.of(" <"+arg+">").colour(RColour.impl().DARK_AQUA));
			}
			
			for (String arg : cmd.optArg()) {
				line.then(RText.of(" ["+arg+"]").colour(RColour.impl().GRAY));
			}
			
			line.then(RText.of("  " + cmd.description()).colour(RColour.impl().WHITE));
			
			this.msg(line);
		}
		
		this.msg(RText.of(" [ ------------------------------------------- ]").colour(RColour.impl().DARK_AQUA));
	}
}
