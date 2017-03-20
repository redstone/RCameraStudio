package net.redstoneore.rcamerastudio.sponge;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

import net.redstoneore.rcamerastudio.Travellers;
import net.redstoneore.rcamerastudio.config.ConfigBuilder;

@Plugin(
	id = "rcamerastudio", 
	name = "RCameraStudio", 
	description = "This plugin gives server owners the ability to control a players camera server-side."
)
public class RCameraStudioSponge {
	
	private static RCameraStudioSponge instance;
	public static RCameraStudioSponge get() { return instance; }
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;
	
	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		instance = this;
		
		ConfigBuilder.configPath = Paths.get(configDir.toString(), "config.json");
		
		Travellers.impl(new SpongeTravellers());
		
		CommandSpec camCommandSpec = CommandSpec.builder()
				.arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("cmd"))))
				.executor(new SpongeCommandManager())
				.build();

		Sponge.getCommandManager().register(this, camCommandSpec, "cam", "camerastudio");
	}
	
	public Path getConfigDir() {
		return this.configDir;
	}
	
}
