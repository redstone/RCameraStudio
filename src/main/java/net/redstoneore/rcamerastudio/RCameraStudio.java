/**
 *  RCamera Studio is a developer friendly version of CPCameraStudioReborn
 *  Copyright (C) 2017  Mark Hughes <m@rkhugh.es>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package net.redstoneore.rcamerastudio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.redstoneore.rcamerastudio.cmd.CommandManager;

public class RCameraStudio extends JavaPlugin {
	
	// -------------------------------------------------- //
	// INSTANCE
	// -------------------------------------------------- //
	
	private static RCameraStudio instance = null;
	public RCameraStudio() { instance = this; }
	public static RCameraStudio get() { return instance; }
	
	// -------------------------------------------------- //
	// STATIC FIELDS
	// -------------------------------------------------- //
	
	public static final String prefix = ChatColor.AQUA + "[CameraStudio] " + ChatColor.GREEN;
		
	// -------------------------------------------------- //
	// STATIC METHODS
	// -------------------------------------------------- //
	
	public static void travel(Player player, List<Location> locations, Integer time, Boolean silent) {
		if (silent) {
			travel(player, locations, time);
		} else {
			travel(player, locations, time, ChatColor.RED + "An error occured during traveling", "");
		}
	}

	/**
	 * Make a player travel between a list of locations, with no messages
	 * @param player      Player to travel
	 * @param locations   Locations to travel across
	 * @param time        Time of executing
	 */
	public static void travel(Player player, List<Location> locations, Integer time) {
		travel(player, locations, time, null, null);
	}
	
	/**
	 * Make a player travel between a list of locations, with messages
	 * @param player      Player to travel
	 * @param locations   Locations to travel across
	 * @param time        Time of executing
	 */
	public static void travel(Player player, List<Location> locations, Integer time, String failMessage, String completedMessage) {
		List<Double> diffs = new ArrayList<Double>();
		List<Integer> travelTimes = new ArrayList<Integer>();

		Double totalDiff = 0.0D;

		for (int i = 0; i < locations.size() - 1; i++) {
			Location s = (Location) locations.get(i);
			Location n = (Location) locations.get(i + 1);
			
			Double diff = Util.positionDifference(s, n);
			totalDiff += diff;
			diffs.add(diff);
		}

		for (Iterator<Double> n = diffs.iterator(); n.hasNext();) {
			double d = ((Double) n.next()).doubleValue();
			travelTimes.add(Integer.valueOf((int) (d / totalDiff * time)));
		}

		final List<Location> tps = new ArrayList<Location>();

		World world = player.getWorld();

		for (int i = 0; i < locations.size() - 1; i++) {
			Location s = (Location) locations.get(i);
			Location n = (Location) locations.get(i + 1);
			int t = ((Integer) travelTimes.get(i)).intValue();

			double moveX = n.getX() - s.getX();
			double moveY = n.getY() - s.getY();
			double moveZ = n.getZ() - s.getZ();
			double movePitch = n.getPitch() - s.getPitch();

			double yawDiff = Math.abs(n.getYaw() - s.getYaw());
			double c = 0.0D;

			if (yawDiff <= 180.0D) {
				if (s.getYaw() < n.getYaw()) {
					c = yawDiff;
				} else {
					c = -yawDiff;
				}
			} else if (s.getYaw() < n.getYaw()) {
				c = -(360.0D - yawDiff);
			} else {
				c = 360.0D - yawDiff;
			}

			double d = c / t;

			for (int x = 0; x < t; x++) {
				Location l = new Location(world, s.getX() + moveX / t * x, s.getY() + moveY / t * x,
						s.getZ() + moveZ / t * x, (float) (s.getYaw() + d * x),
						(float) (s.getPitch() + movePitch / t * x));
				tps.add(l);
			}
			
		}
		
		try {
			player.setAllowFlight(true);
			player.teleport((Location) tps.get(0));
			player.setFlying(true);
			
			Traveller.get(player).travelling(true);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(RCameraStudio.get(), new Runnable() {
				private int ticks = 0;

				public void run() {
					if (this.ticks < tps.size()) {

						player.teleport((Location) tps.get(this.ticks));

						if (Traveller.get(player).travelling()) {
							Bukkit.getServer().getScheduler()
									.scheduleSyncDelayedTask(RCameraStudio.get(), this, 1L);
						} 
						this.ticks += 1;
					} else {
						Traveller.get(player).travelling(false);
						
						if (completedMessage != null) {
							player.sendMessage(completedMessage);
						}
					}
				}
			});
		} catch (Exception e) {
			if (failMessage != null) {
				player.sendMessage(failMessage);
			}
		}
	}
	
	
	// -------------------------------------------------- //
	// PLUGIN METHODS
	// -------------------------------------------------- // 
	
	// Plugin enable
	@Override
	public void onEnable() {			
		this.getServer().getPluginManager().registerEvents(CommandManager.get(), this);
	}
	
}