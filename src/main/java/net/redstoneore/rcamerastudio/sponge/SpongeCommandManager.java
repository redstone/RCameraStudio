package net.redstoneore.rcamerastudio.sponge;

import java.util.ArrayList;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import com.google.common.collect.Lists;

import net.redstoneore.rcamerastudio.api.Traveller;
import net.redstoneore.rcamerastudio.api.Travellers;
import net.redstoneore.rcamerastudio.cmd.CmdCam;

public class SpongeCommandManager implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		String argumentsLine;
		ArrayList<String> arguments;
		
		if (args.<String>getOne("cmd").isPresent()) {
			argumentsLine = args.<String>getOne("cmd").get();
			arguments = Lists.newArrayList(argumentsLine.split(" "));
		} else {
			argumentsLine = null;
			arguments = Lists.newArrayList();
		}
		
		Traveller traveller = null;
		
		if (src instanceof Player) {
		    Player player = (Player) src;
		    traveller =  Travellers.impl().get(player.getUniqueId());
		} else if(src instanceof ConsoleSource) {
			traveller =  Travellers.impl().get(null);
		} else {
			return CommandResult.builder().successCount(0).build();
		}
		
		CmdCam.get().preExec(traveller, arguments);
		
		return CommandResult.builder().successCount(1).build();
	}

}
