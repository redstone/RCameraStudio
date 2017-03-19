package net.redstoneore.rcamerastudio.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.redstoneore.rcamerastudio.Traveller;
import net.redstoneore.rcamerastudio.Travellers;

public class BukkitTravellers extends Travellers {

	private Map<UUID, BukkitTraveller> travellerMap = new HashMap<UUID, BukkitTraveller>();
	
	@Override
	public Traveller get(UUID uuid) {
		if (!this.travellerMap.containsKey(uuid)) {
			this.travellerMap.put(uuid, new BukkitTraveller(uuid));
		}
		return this.travellerMap.get(uuid);
	}

}
