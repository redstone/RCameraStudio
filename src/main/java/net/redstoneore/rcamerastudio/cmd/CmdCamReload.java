package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.config.Config;
import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

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
			this.msg(RText.of("Failed to reload the config.json file").colour(RColour.impl().RED));
			return;
		}
		
		this.msg(RText.of("Configuration reloaded!").colour(RColour.impl().GREEN));
	}

}
