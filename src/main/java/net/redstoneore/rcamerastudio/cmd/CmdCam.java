package net.redstoneore.rcamerastudio.cmd;

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
		this.msg(RED, "Invalid usage! Type ", DARK_AQUA, "/cam help", RED, " for usage.");
	}

}
