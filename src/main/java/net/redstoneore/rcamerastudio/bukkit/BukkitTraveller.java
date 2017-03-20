package net.redstoneore.rcamerastudio.bukkit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.redstoneore.rcamerastudio.Loc;
import net.redstoneore.rcamerastudio.Traveller;
import net.redstoneore.rcamerastudio.rtext.RText;
import net.redstoneore.rcamerastudio.rtext.RTextBukkit;

public class BukkitTraveller extends Traveller {

	public BukkitTraveller(UUID id) {
		this.player = Bukkit.getPlayer(id);
	}
	
	private Player player = null;

	@Override
	public UUID getUUID() {
		return this.player.getUniqueId();
	}

	@Override
	public Loc getLoc() {
		Location location = this.player.getLocation();
		
		return new Loc(
			location.getWorld().getUID(), 
			location.getX(), 
			location.getY(), 
			location.getZ(), 
			location.getYaw(),
			location.getPitch()
		);
	}
	
	@Override
	public void msg(RText msg) {
		RTextBukkit rTextBukkit = (RTextBukkit) msg;
		if (this.player == null) {
			Bukkit.getConsoleSender().sendMessage(rTextBukkit.message.toPlainText());
			return;
		}
		this.player.spigot().sendMessage(rTextBukkit.message);
	}

	@Override
	public void teleport(Loc loc) {
		World world = Bukkit.getWorld(loc.getWorld());
		Location location = new Location(world, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		
		this.player.teleport(location);
	}

	@Override
	public void setAllowFlight(Boolean allowFlight) {
		this.player.setAllowFlight(allowFlight);
	}

	@Override
	public void setFlying(Boolean flying) {
		this.player.setFlying(flying);
	}

	@Override
	public Boolean getAllowFlight() {
		return this.player.getAllowFlight();
	}

	@Override
	public Boolean getFlying() {
		return this.player.isFlying();
	}

	@Override
	public void runLater(Runnable task, Long delay) {
		Bukkit.getScheduler().runTaskLater(RCameraStudioBukkit.get(), task, delay);
	}

	@Override
	public Boolean hasPermission(String permission) {
		if (this.player == null) {
			return Bukkit.getConsoleSender().hasPermission(permission);
		}
		return this.player.hasPermission(permission);
	}

	@Override
	public Boolean isPlayer() {
		return this.player != null;
	}
	
}
