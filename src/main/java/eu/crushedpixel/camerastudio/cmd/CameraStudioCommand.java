package eu.crushedpixel.camerastudio.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.crushedpixel.camerastudio.Points;

public abstract class CameraStudioCommand<T> {

	// -------------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------------- // 

	protected static final ChatColor BLACK = ChatColor.BLACK;
	protected static final ChatColor DARK_GRAY = ChatColor.DARK_GRAY;
	protected static final ChatColor GRAY = ChatColor.GRAY;
	protected static final ChatColor WHITE = ChatColor.WHITE;

	protected static final ChatColor DARK_RED = ChatColor.DARK_RED;
	protected static final ChatColor RED = ChatColor.RED;

	protected static final ChatColor DARK_GREEN = ChatColor.DARK_GREEN;
	protected static final ChatColor GREEN = ChatColor.GREEN;

	protected static final ChatColor BLUE = ChatColor.BLUE;
	protected static final ChatColor DARK_BLUE = ChatColor.DARK_BLUE;

	protected static final ChatColor AQUA = ChatColor.AQUA;
	protected static final ChatColor DARK_AQUA = ChatColor.DARK_AQUA;

	protected static final ChatColor DARK_PURPLE = ChatColor.DARK_PURPLE;
	protected static final ChatColor LIGHT_PURPLE = ChatColor.LIGHT_PURPLE;
	
	protected static final ChatColor GOLD = ChatColor.GOLD;
	protected static final ChatColor YELLOW = ChatColor.YELLOW;
	
	protected static final ChatColor BOLD = ChatColor.BOLD;
	protected static final ChatColor ITALIC = ChatColor.ITALIC;
	protected static final ChatColor UNDERLINE = ChatColor.UNDERLINE;
	protected static final ChatColor STRIKETHROUGH = ChatColor.STRIKETHROUGH;

	protected static final ChatColor MAGIC = ChatColor.MAGIC;

	protected static final ChatColor RESET = ChatColor.RESET;

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- // 
	
	private List<String> aliases = new ArrayList<String>();
	private String permission = null;
	private List<String> requiredArguments = new ArrayList<String>();
	private List<String> optionalArguments = new ArrayList<String>();
	
	private List<CameraStudioCommand<?>> subCommands = new ArrayList<CameraStudioCommand<?>>();
	
	protected CameraStudioCommand<?> parent = null;
	
	// -------------------------------------------------- //
	// SETTERS AND GETTERS
	// -------------------------------------------------- // 
	
	// aliases
	public void aliases(String... aliases) {
		for (String alias : aliases) {
			this.aliases.add(alias);
		}
	}
	
	public List<String> aliases() {
		return this.aliases;
	}
	
	// permission
	public void permission(String permission) {
		this.permission = permission;
	}
	
	public Optional<String> permission() {
		if (this.permission == null) return Optional.empty();
		
		return Optional.of(this.permission);
	}
	
	// sub commands
	public List<CameraStudioCommand<?>> subcommands() {
		return this.subCommands;
	}
	
	public void subcommands(CameraStudioCommand<?>... subcommands) {
		for (CameraStudioCommand<?> subcommand : subcommands) {
			this.subCommands.add(subcommand);
		}
	}
	
	// required arguments
	public void reqArg(String arg) {
		this.requiredArguments.add(arg);
	}
	
	public List<String> reqArg() {
		return this.requiredArguments;
	}
	
	// optional arguments
	public void optArg(String arg) {
		this.optionalArguments.add(arg);
	}
	
	public List<String> optArg() {
		return this.optionalArguments;
	}
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- // 

	public Optional<CameraStudioCommand<?>> parent() {
		if (this.parent == null) return Optional.empty();
		return Optional.of(this.parent);
	}
	
	protected void preExec(CommandSender sender, List<String> arguments) {
		this.arguments = arguments;
		this.sender = sender;
		
		// Check permission
		if (this.permission().isPresent() && !sender.hasPermission(this.permission().get())) {
			msg(RED, "You do not have permission to use this command.");
			return;
		}
		
		// Check arguments
		if (!this.reqArg().isEmpty() && this.reqArg().size() < arguments.size()) {
			msg(RED, "Missing arguments.");
			
			StringBuilder usage = new StringBuilder();
			usage.append("/cam");
			for (String reqArg : this.reqArg()) {
				usage.append(" <" + reqArg + ">");
			}
			msg(RED, "Usage: ", AQUA, usage.toString());
			return;
		}
		
		// Check sub command
		if (!arguments.isEmpty() && !this.subcommands().isEmpty()) {
			String potentialCommand = arguments.get(0);
			
			for (CameraStudioCommand<?> command : this.subcommands()) {
				for (String alias : command.aliases()) {
					if(alias.equalsIgnoreCase(potentialCommand)) {
						arguments.remove(0);
						command.parent = this;
						command.preExec(sender, arguments);
						return;
					}
				}
			}
		}
		
		// Execute
		this.exec();
	}
	
	// -------------------------------------------------- //
	// RUNTIME FIELDS
	// -------------------------------------------------- // 

	private List<String> arguments = new ArrayList<String>();
	private CommandSender sender = null;
	
	// -------------------------------------------------- //
	// RUNTIME METHODS
	// -------------------------------------------------- // 

	@SuppressWarnings("unchecked")
	public T msg(Object... msgParts) {
		StringBuilder sb = new StringBuilder();
		
		for (Object msgPart : msgParts) {
			sb.append(msgPart.toString());
		}
		
		this.sender().sendMessage(sb.toString());
		
		return (T) this;
	}
	public CommandSender sender() {
		return this.sender;
	}
	
	public Optional<Player> player() {
		if (!(this.sender() instanceof Player)) {
			return Optional.empty();
		}
		return Optional.of((Player) this.sender());
	}
	
	public Optional<Points> points() {
		return Optional.of(Points.get(this.player().orElse(null)));
	}
	
	public List<String> args() {
		return this.arguments;
	}
	
	public String arg(Class<String> type, int index, String defaultValue) {
		if (this.args().size() - 1 >= index) {
			return this.args().get(index);
		}
		return defaultValue;
	}
	
	public Integer arg(Class<Integer> type, int index, Integer defaultValue) {
		if (this.args().size() - 1 >= index) {
			return Integer.valueOf(this.args().get(index));
		}
		return defaultValue;
	}
	
	public Boolean arg(Class<Boolean> type, int index, boolean defaultValue) {
		if (this.args().size() - 1 >= index) {
			String arg = this.args().get(index).toLowerCase();
			if (arg.startsWith("y")) return true;
			if (arg.startsWith("n")) return false;
			
			return Boolean.valueOf(this.args().get(index));
		}
		return defaultValue;
	}

	public abstract void exec();
	
}
