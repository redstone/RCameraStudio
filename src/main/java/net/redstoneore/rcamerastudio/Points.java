package net.redstoneore.rcamerastudio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Points {

	// --------------------------------------------------
	// STATIC
	// --------------------------------------------------

	private static Map<UUID, Points> pointsMap = new HashMap<UUID, Points>();
	public static Points get(Player player) {
		if (!pointsMap.containsKey(player.getUniqueId())) {
			pointsMap.put(player.getUniqueId(), new Points(player));
		}
		return pointsMap.get(player.getUniqueId());
	}
	// --------------------------------------------------
	// CONSTRUCT
	// --------------------------------------------------
	
	protected Points(Player player) {
		this.player = player;
	}
	
	// --------------------------------------------------
	// FIELDS
	// --------------------------------------------------
	
	private final Player player;
	
	public List<Location> pointsList = new ArrayList<Location>();
	
	// --------------------------------------------------
	// METHODS
	// --------------------------------------------------
	
	public Player getPlayer() {
		return this.player;
	}
	
	public List<Location> getAll() {
		return this.pointsList;
	}

	public void add(Location location) {
		this.pointsList.add(location);
	}

	public int size() {
		return this.pointsList.size();
	}

	public void remove(int i) {
		this.pointsList.remove(i);
	}

	public void clear() {
		this.pointsList.clear();
	}

	public Location get(int i) {
		return this.pointsList.get(i);
	}
	
}
