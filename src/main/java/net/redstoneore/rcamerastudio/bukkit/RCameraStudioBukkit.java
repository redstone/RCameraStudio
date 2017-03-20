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

package net.redstoneore.rcamerastudio.bukkit;

import java.nio.file.Paths;

import org.bukkit.plugin.java.JavaPlugin;

import net.redstoneore.rcamerastudio.api.Travellers;
import net.redstoneore.rcamerastudio.config.ConfigBuilder;
import net.redstoneore.rcamerastudio.rtext.RColourBukkit;
import net.redstoneore.rcamerastudio.rtext.RText;
import net.redstoneore.rcamerastudio.rtext.RTextBukkit;
import net.redstoneore.rcamerastudio.rtext.RColour;

public class RCameraStudioBukkit extends JavaPlugin {
	
	// -------------------------------------------------- //
	// INSTANCE
	// -------------------------------------------------- //
	
	private static RCameraStudioBukkit instance = null;
	public RCameraStudioBukkit() { instance = this; }
	public static RCameraStudioBukkit get() { return instance; }
		
	// -------------------------------------------------- //
	// PLUGIN METHODS
	// -------------------------------------------------- // 
	
	// Plugin enable
	@Override
	public void onEnable() {	
		ConfigBuilder.configPath = Paths.get(this.getDataFolder().toString(), "config.json");
		
		RText.impl(new RTextBukkit());
		RColour.impl(new RColourBukkit());
		Travellers.impl(new BukkitTravellers());
		
		this.getServer().getPluginManager().registerEvents(BukkitCommandManager.get(), this);
	}
	
}