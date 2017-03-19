package net.redstoneore.rcamerastudio.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.redstoneore.rcamerastudio.Traveller;

public abstract class CameraStudioCommand<T> {

	// -------------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------------- // 

	protected static final String BLACK = new String(new char[] {'\u00A7', '0'});
	protected static final String DARK_GRAY = new String(new char[] {'\u00A7', '8'});
	protected static final String GRAY = new String(new char[] {'\u00A7', '7'});
	protected static final String WHITE = new String(new char[] {'\u00A7', 'f'});

	protected static final String DARK_RED = new String(new char[] {'\u00A7', '4'});
	protected static final String RED = new String(new char[] {'\u00A7', 'c'});

	protected static final String DARK_GREEN = new String(new char[] {'\u00A7', '2'});
	protected static final String GREEN = new String(new char[] {'\u00A7', 'a'});

	protected static final String BLUE = new String(new char[] {'\u00A7', '9'});
	protected static final String DARK_BLUE = new String(new char[] {'\u00A7', '1'});

	protected static final String AQUA = new String(new char[] {'\u00A7', 'b'});
	protected static final String DARK_AQUA = new String(new char[] {'\u00A7', '3'});

	protected static final String DARK_PURPLE = new String(new char[] {'\u00A7', '5'});
	protected static final String LIGHT_PURPLE = new String(new char[] {'\u00A7', 'd'});
	
	protected static final String GOLD = new String(new char[] {'\u00A7', '6'});
	protected static final String YELLOW = new String(new char[] {'\u00A7', 'e'});
	
	protected static final String BOLD = new String(new char[] {'\u00A7', 'l'});
	protected static final String ITALIC = new String(new char[] {'\u00A7', 'o'});
	protected static final String UNDERLINE = new String(new char[] {'\u00A7', 'n'});
	protected static final String STRIKETHROUGH = new String(new char[] {'\u00A7', 'm'});

	protected static final String MAGIC = new String(new char[] {'\u00A7', 'k'});

	protected static final String RESET = new String(new char[] {'\u00A7', 'r'});

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- // 
	
	private List<String> aliases = new ArrayList<String>();
	private String permission = null;
	private String desc = "";
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
	
	// description
	public void description(String desc) {
		this.desc = desc;
	}
	
	public String description() {
		return this.desc;
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
	
	public void preExec(Traveller traveller, List<String> arguments) {
		this.arguments = arguments;
		this.traveller = traveller;
		
		// Check permission
		if (this.permission().isPresent() && !traveller.hasPermission(this.permission().get())) {
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
						command.preExec(traveller, arguments);
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
	private Traveller traveller = null;
	
	// -------------------------------------------------- //
	// RUNTIME METHODS
	// -------------------------------------------------- // 

	@SuppressWarnings("unchecked")
	public T msg(Object... msgParts) {
		StringBuilder sb = new StringBuilder();
		
		for (Object msgPart : msgParts) {
			sb.append(msgPart.toString());
		}
		
		this.traveller.msg(sb.toString());
		
		return (T) this;
	}
	
	public Optional<Traveller> traveller() {
		if (this.traveller == null) return Optional.empty();
		return Optional.of(this.traveller);
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
