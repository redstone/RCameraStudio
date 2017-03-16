package net.redstoneore.rcamerastudio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Traveller {

	// --------------------------------------------------
	// STATIC
	// --------------------------------------------------

	private static Map<UUID, Traveller> pointsMap = new HashMap<UUID, Traveller>();
	public static Traveller get(Player player) {
		if (!pointsMap.containsKey(player.getUniqueId())) {
			pointsMap.put(player.getUniqueId(), new Traveller(player));
		}
		return pointsMap.get(player.getUniqueId());
	}
	// --------------------------------------------------
	// CONSTRUCT
	// --------------------------------------------------
	
	protected Traveller(Player player) {
		this.player = player;
	}
	
	// --------------------------------------------------
	// FIELDS
	// --------------------------------------------------
	
	private final Player player;
	
	private List<Location> pointsList = new ArrayList<Location>();
	
	private Boolean travelling = false;
	
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

	public void set(List<Location> pointsList) {
		this.pointsList = pointsList;
	}
	
	public void travelling(Boolean travelling) {
		this.travelling = travelling;
	}
	
	public Boolean travelling() {
		return this.travelling;
	}
	
}
