package net.redstoneore.rcamerastudio.cmd;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.google.common.collect.Lists;

public class CommandManager implements Listener {

	// -------------------------------------------------- //
	// INSTANCE
	// -------------------------------------------------- // 

	private static CommandManager instance = new CommandManager();
	public static CommandManager get() { return instance; }
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- // 
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		String msg = event.getMessage();
		
		if (msg.startsWith("/cam") || msg.startsWith("/camerastudio")) {
			event.setCancelled(true);
			List<String> args = Lists.newArrayList(msg.split(" "));
			
			if (args.size() > 1) {
				args.remove(0);
			}
			
			CmdCam.get().preExec(event.getPlayer(), args);
		}
	}

}
