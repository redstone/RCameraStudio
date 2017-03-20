package net.redstoneore.rcamerastudio.bukkit;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import com.google.common.collect.Lists;

import net.redstoneore.rcamerastudio.api.Traveller;
import net.redstoneore.rcamerastudio.api.Travellers;
import net.redstoneore.rcamerastudio.cmd.CmdCam;
import net.redstoneore.rcamerastudio.config.Config;

public class BukkitCommandManager implements Listener {

	// -------------------------------------------------- //
	// INSTANCE
	// -------------------------------------------------- // 

	private static BukkitCommandManager instance = new BukkitCommandManager();
	public static BukkitCommandManager get() { return instance; }
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- // 
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String msg = event.getMessage();
		CommandSender sender = event.getPlayer();
		
		this.handle(msg, sender, event);
	}
	
	@EventHandler
	public void onCommand(ServerCommandEvent event) {
		String msg = "/" + event.getCommand();
		CommandSender sender = event.getSender();
		
		this.handle(msg, sender, event);
	}
	
	
	public void handle(String msg, CommandSender sender, Cancellable event) {
		if (Config.get().hiddenMode && !sender.hasPermission("camerastudio.use") && !sender.isOp()) return;
		
		if (msg.startsWith("/cam") || msg.startsWith("/camerastudio")) {
			
			event.setCancelled(true);
			List<String> args = Lists.newArrayList(msg.split(" "));
			
			if (args.size() > 1) {
				args.remove(0);
			}
			
			Traveller traveller = null;
			
			if (sender instanceof ConsoleCommandSender) {
				traveller = new BukkitTraveller(null);
			} else {
				Player player = (Player) sender;
				traveller = Travellers.impl().get(player.getUniqueId());
			}
			CmdCam.get().preExec(traveller, args);
		}
	}

}
