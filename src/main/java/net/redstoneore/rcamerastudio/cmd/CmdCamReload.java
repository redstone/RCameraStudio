package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.config.Config;

public class CmdCamReload extends CameraStudioCommand<CmdCamReload> {

	private static CmdCamReload instance = new CmdCamReload();
	public static CmdCamReload get() { return instance; }
	
	public CmdCamReload() {
		this.aliases("reload");
		this.description("reload the configuration");
		this.permission("camerastudio.reload");
	}
	
	@Override
	public void exec() {
		try {
			Config.get().load();
		} catch (Exception e) {
			e.printStackTrace();
			msg(RED, "Failed to reload the config.json");
			return;
		}
		
		msg(GREEN, "Configuration reloaded!");		
	}

}
