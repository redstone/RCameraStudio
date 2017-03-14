package eu.crushedpixel.camerastudio;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CmdCam implements CommandExecutor {

	// -------------------------------------------------- //
	// INSTANCE
	// -------------------------------------------------- // 

	private static CmdCam instance = new CmdCam();
	public static CmdCam get() { return instance; }
		
	// -------------------------------------------------- //
	// STATIC FIELDS
	// -------------------------------------------------- // 
	
	private static String prefix = CameraStudio.prefix;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- // 
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Player only command
		if (!(sender instanceof Player)) {
			sender.sendMessage("This is a player only command.");
			return true;
		}
		
		final Player player = (Player) sender;
		
		if (!player.hasPermission("camerastudio")) {
			player.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command.");
			return true;
		}
		
		if (args.length == 0) {
			player.sendMessage(
					prefix + ChatColor.RED + "Type " + ChatColor.WHITE + "/cam help" + ChatColor.RED + " for details");
			return true;
		}
		
		String subcmd = args[0];

		String[] newArgs = new String[args.length - 1];
		for (int p = 1; p < args.length; p++) {
			newArgs[(p - 1)] = args[p];
		}

		args = newArgs;

		if (subcmd.equalsIgnoreCase("p") && (player.hasPermission("camerastudio.point"))) {
			Points.get(player).add(player.getLocation());
			
			player.sendMessage(prefix + "Point " + Points.get(player).size() + " has been set");

			return true;
		}

		if (subcmd.equalsIgnoreCase("r") && (player.hasPermission("camerastudio.remove"))) {
			
			if (args.length == 0) {
				if (Points.get(player).size() > 0) {
					int remove = Points.get(player).size() - 1;
					Points.get(player).remove(remove);
					player.sendMessage(prefix + "Point " + remove + " has been removed");
				} else {
					player.sendMessage(prefix + ChatColor.RED + "You don't have any points set");
					return true;
				}
			} else if (args.length == 1) {
				try {
					int pos = Integer.valueOf(args[0]).intValue();
					if (Points.get(player).size() >= pos) {
						Points.get(player).remove(pos - 1);
						player.sendMessage(prefix + "Point " + pos + " has been removed");
					} else {
						if (Points.get(player).size() == 1) {
							player.sendMessage(prefix + ChatColor.RED + "You only have 1 point set");
							return true;
						} else {
							player.sendMessage(prefix + ChatColor.RED + "You only have " + Points.get(player).size() + " points set");
							return true;
						}
					}
				} catch (Exception e) {
					player.sendMessage(prefix + ChatColor.RED + args[0] + " is not a valid number");
					return true;
				}
			}

			return true;
		}

		if (subcmd.equalsIgnoreCase("list") && (player.hasPermission("camerastudio.list"))) {
			if (Points.get(player).size() == 0) {
				player.sendMessage(prefix + ChatColor.RED + "You don't have any points set");
				return true;
			}

			int i = 1;
			for (Location loc : Points.get(player).getAll()) {
				player.sendMessage(prefix + "Point " + i + ": " + Util.round(loc.getX(), 1) + ", "
						+ Util.round(loc.getY(), 1) + ", " + Util.round(loc.getZ(), 1) + " ("
						+ Util.round(loc.getYaw(), 1) + ", " + Util.round(loc.getPitch(), 1) + ")");
				i++;
			}
			return true;
		}

		if (subcmd.equalsIgnoreCase("reset") && (player.hasPermission("camerastudio.reset"))) {
			Points.get(player).clear();
			player.sendMessage(prefix + "Successfully removed all points");
			return true;
		}

		if (subcmd.equalsIgnoreCase("reload")) {
			if (sender.hasPermission("camerastudio.admin")) {
				CameraStudio.get().reloadConfig();
				sender.sendMessage(prefix + ChatColor.YELLOW + "The configuration files have been reloaded!");
				return true;
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command.");
				return true;
			}
		}

		if (subcmd.equalsIgnoreCase("goto") && (player.hasPermission("camerastudio.goto"))) {
			if (args.length == 1) {
				try {
					int pos = Integer.valueOf(args[0]).intValue();
					if ((Points.get(player).size() >= pos)) {
						player.teleport((Location) Points.get(player).get(pos - 1));
						player.sendMessage(prefix + "Teleported to Point " + pos);
						return true; // This was "break label1152;"
					}
					if (Points.get(player).size() == 0) {
						player.sendMessage(prefix + ChatColor.RED + "You don't have any points set");
						return true;
					}
					player.sendMessage(prefix + ChatColor.RED + "You only have " + Points.get(player).size() + " points set");
					return true;
				} catch (Exception e) {
					player.sendMessage(prefix + ChatColor.RED + args[0] + " is not a valid number");
					return true;
				}
			}
			player.sendMessage(prefix + ChatColor.RED + "You must specify the point you want to teleport to");

			// Says it wasn't referenced, I just removed it:
			// label1152:
			// return true;
			return true;
		}

		if (subcmd.equalsIgnoreCase("stop") && (player.hasPermission("camerastudio.stop"))) {
			CameraStudio.stop(player.getUniqueId());
			player.sendMessage(prefix + "Travelling has been cancelled");
			return true;
		}

		if (subcmd.equalsIgnoreCase("help") && (player.hasPermission("camerastudio.help"))) {
			if (args.length == 0) {
				player.performCommand("help CPCameraStudioReborn");
				return true;
			} else {
				if (args.length == 1) {
					try {
						player.performCommand("help CPCameraStudioReborn " + Integer.parseInt(args[0]));
						return true;
					} catch (NumberFormatException e) {
						player.sendMessage(prefix + ChatColor.YELLOW + args[0] + ChatColor.RED + "is not a number!");
						return true;
					}
				} else {
					player.sendMessage(prefix + ChatColor.RED + "Too many arguements! Usage: " + ChatColor.YELLOW
							+ "/cam help <pagenumber>");
				}
			}
			return true;
		}

		if ((subcmd.equalsIgnoreCase("start") && (player.hasPermission("camerastudio.start")))
				|| (subcmd.equalsIgnoreCase("preview") && (player.hasPermission("camerastudio.preview")))) {

			if (args.length == 0 && !subcmd.equalsIgnoreCase("preview")) {
				player.sendMessage(prefix + ChatColor.RED + "You must specify the travel duration. " + ChatColor.YELLOW
						+ "Example: /start 3m10s");
			} else {

				List<String> argsList = new ArrayList<String>();
				List<Location> listOfLocs = new ArrayList<Location>();
				if (Points.get(player).size() > 0) {
					listOfLocs.addAll(Points.get(player).getAll());
				}
				boolean fileLoaded = false;
				Player currentPlayer = player;
				for (String string : args) {
					argsList.add(string);

					File file = new File(CameraStudio.get().getDataFolder() + "/SavedPaths", string + ".yml");
					YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
					List<String> ListOfLocationsStrings = yaml.getStringList("Locations");
					if (!ListOfLocationsStrings.isEmpty()) {
						listOfLocs.clear();
						for (String string2 : ListOfLocationsStrings) {
							listOfLocs.add(Util.getDeserializedLocation(string2));
						}
						fileLoaded = true;
					}

					if (Bukkit.getPlayer(string) != null)
						currentPlayer = Bukkit.getPlayer(string);
				}
				
				if (CameraStudio.isTravelling(currentPlayer.getUniqueId())) {
					currentPlayer.sendMessage(prefix + ChatColor.RED + "You are already travelling");
					return true;
				}
				
				if (listOfLocs.isEmpty()) {
					if (fileLoaded)
						player.sendMessage(prefix + ChatColor.RED + "File specified was either invalid or empty.");
					else
						player.sendMessage(prefix + ChatColor.RED + "Not enough points set.");
					return true;
				}

				if (listOfLocs.size() <= 1) {
					if (fileLoaded)
						player.sendMessage(prefix + ChatColor.RED + "Not enough points set in file.");
					else
						player.sendMessage(prefix + ChatColor.RED + "Not enough points set.");
					return true;
				}
				try {
					int previewTime = Util.parseTimeString(CameraStudio.get().getConfig().getString("preview-time"));

					int time = previewTime * (listOfLocs.size() - 1);
					if (subcmd.equalsIgnoreCase("start")) {
						time = Util.parseTimeString(args[0]);
					}
					if (argsList.contains("silent")) {

						CameraStudio.travel(currentPlayer, listOfLocs, time, null, null);
					} else {
						CameraStudio.travel(currentPlayer, listOfLocs, time,
								prefix + ChatColor.RED + "An error occured during traveling",
								prefix + "Travelling finished");
					}
				} catch (ParseException e) {
					player.sendMessage(prefix + ChatColor.RED + "You must specify the travel duration. "
							+ ChatColor.YELLOW + "Example: /start 3m10s");
				}
				return true;
			}
			if (subcmd.equalsIgnoreCase("save") && (player.hasPermission("camerastudio.save"))) {
				if (args.length == 1) {
					if (Points.get(player).size() > 0) {

						File file = new File(CameraStudio.get().getDataFolder() + "/SavedPaths", args[0] + ".yml");

						try {
							file.getParentFile().mkdirs();
							file.createNewFile();
						} catch (IOException e) {
							player.sendMessage(prefix + ChatColor.RED
									+ "An error occured while creating the file! Is there enough space left?");
							return true;
						}

						YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

						Collection<String> ListOfLocations = new ArrayList<String>();
						for (Location loc : Points.get(player).getAll()) {
							ListOfLocations.add(Util.getSerializedLocation(loc));
						}

						yaml.set("Locations", ListOfLocations);

						try {
							yaml.save(file);
						} catch (IOException e) {
							player.sendMessage(prefix + ChatColor.RED
									+ "An error occured while creating the file! Is there enough space left?");
							return true;
						}

						player.sendMessage(prefix + ChatColor.YELLOW + "Path: " + ChatColor.BLUE + args[0]
								+ ChatColor.YELLOW + " has been saved!");
						return true;
					} else {
						player.sendMessage(prefix + ChatColor.RED + "You do not have any points set to save!");
					}
				} else {
					if (args.length >= 2) {
						player.sendMessage(prefix + ChatColor.RED + "Too many arguements! Usage: " + ChatColor.YELLOW
								+ "/cam save <savename>");
						return true;
					} else {
						player.sendMessage(prefix + ChatColor.RED + "Too few arguements! Usage: " + ChatColor.YELLOW
								+ "/cam save <savename>");
						return true;
					}
				}
			}

			if (subcmd.equalsIgnoreCase("load") && (player.hasPermission("camerastudio.load"))) {
				if (CameraStudio.isTravelling(player.getUniqueId())) {
					player.sendMessage(prefix + ChatColor.RED + "You are currently travelling");
					return true;
				}
				if (args.length == 1) {
					Points.get(player).clear();
					
					File file = new File(CameraStudio.get().getDataFolder() + "/SavedPaths", args[0] + ".yml");
					YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
					
					for (String string : yaml.getStringList("Locations")) {
						Location location = Util.getDeserializedLocation(string);
						Points.get(player).add(location);
					}
					if (Points.get(player).size() == 0) {
						player.sendMessage(prefix + ChatColor.RED + "File " + ChatColor.GREEN + args[1] + ChatColor.RED
								+ " was either invalid or empty. No points have been loaded!");
						return true;
					}
					player.sendMessage(prefix + ChatColor.YELLOW + "Path: " + ChatColor.BLUE + args[0]
							+ ChatColor.YELLOW + " has been loaded!");
					return true;
				} else {
					if (args.length >= 1) {
						player.sendMessage(prefix + ChatColor.RED + "Too many arguements! Usage: " + ChatColor.YELLOW
								+ "/cam save <savename>");
						return true;
					} else {
						player.sendMessage(prefix + ChatColor.RED + "Too few arguements! Usage: " + ChatColor.YELLOW
								+ "/cam save <savename>");
						return true;
					}
				}
			}
		}
		return false;
	}
}
