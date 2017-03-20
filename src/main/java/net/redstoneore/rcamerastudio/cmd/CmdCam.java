package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public class CmdCam extends CameraStudioCommand<CmdCam> {

	public static CmdCam instance = new CmdCam();
	public static CmdCam get() { return instance; }
	
	public CmdCam() {
		this.aliases(
			"cam",
			"camerastudio"
		);
		this.description("base command for RCameraStudio");
		this.permission("camerastudio.use");
		
		this.subcommands(
			CmdCamPoint.get(),
			CmdCamRemove.get(),
			CmdCamList.get(),
			CmdCamReset.get(),
			CmdCamGoto.get(),
			CmdCamStart.get(),
			CmdCamStop.get(),
			CmdCamSave.get(),
			CmdCamLoad.get(),
			CmdCamReplay.get(),
			CmdCamReplays.get(),
			CmdCamHelp.get(),
			CmdCamReload.get()
		);
	}
	
	@Override
	public void exec() {
		RText message = RText.of("Invalid usage! Type ").colour(RColour.impl().RED).then(
			RText.of("/cam help").colour(RColour.impl().DARK_AQUA).then(
				RText.of(" for usage").colour(RColour.impl().RED)
			)
		);
		
		msg(message);
	}

}
