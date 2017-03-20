package net.redstoneore.rcamerastudio.cmd;

import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public class CmdCamStop extends CameraStudioPlayerCommand<CmdCamStop> {

	private static CmdCamStop instance = new CmdCamStop();
	public static CmdCamStop get() { return instance;}
	
	public CmdCamStop() {
		this.aliases("stop");
		this.description("stop travelling");
	}

	@Override
	public void exec() {
		
		if (this.getTraveller().travelling()) {
			this.getTraveller().travelling(false);
			this.msg(RText.of("Travelling has been stopped.").colour(RColour.impl().GREEN));			
		} else {
			this.msg(RText.of("You are not travelling.").colour(RColour.impl().RED));
		}
	}
	
}
