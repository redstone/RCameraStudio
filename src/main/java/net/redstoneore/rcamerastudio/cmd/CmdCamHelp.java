package net.redstoneore.rcamerastudio.cmd;

public class CmdCamHelp extends CameraStudioCommand<CmdCamHelp> {

	private static CmdCamHelp instance = new CmdCamHelp();
	public static CmdCamHelp get() { return instance; }
	
	public CmdCamHelp() {
		this.aliases("help");
	}

	@Override
	public void exec() {
		msg(DARK_AQUA, " [ --------------- ", AQUA, "RCameraStudio", DARK_AQUA, " --------------- ]");
		
		msg(AQUA, "  /cam  ", WHITE, CmdCam.get().description());

		for (CameraStudioCommand<?> cmd : CmdCam.get().subcommands()) {
			StringBuilder line = new StringBuilder();
			line.append(AQUA + "  /cam");
			line.append(" " + cmd.aliases().get(0));
			
			for (String arg : cmd.reqArg()) {
				line.append(DARK_AQUA + " <" + arg + ">");
			}
			for (String arg : cmd.optArg()) {
				line.append(GRAY + " [" + arg + "]");
			}
			line.append("  "+WHITE + cmd.description());
			
			msg(line.toString());
			

		}
		
		msg(DARK_AQUA, " [ ------------------------------------------- ]");
	}
}
