package net.redstoneore.rcamerastudio.config;

public class Config extends ConfigBuilder {
	
	// Message sent when travelling completed.
	public String travelCompletedMessage = "<green>Travelling completed.";
	
	// Message sent when there is an error travelling.
	public String travelFailedMessage = "<red>An error occured during travelling";
	
	// If no permissions are active on the player, the command will act as if it doesn't exist.
	public Boolean hiddenMode = true;
	
	// If larger than 0, we will reset the players fly and flightAllowed settings to what they
	// were after htat many ticks.
	public Long resetAfter = 0L;
	
	// Default time for when /cam start is used.
	public String defaultTime = "1m15s";
	
}
