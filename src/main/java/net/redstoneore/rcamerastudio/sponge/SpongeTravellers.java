package net.redstoneore.rcamerastudio.sponge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.redstoneore.rcamerastudio.Traveller;
import net.redstoneore.rcamerastudio.Travellers;

public class SpongeTravellers extends Travellers {
	
	private Map<UUID, SpongeTraveller> travellerMap = new HashMap<UUID, SpongeTraveller>();
	
	private SpongeTraveller consoleTraveller = new SpongeTraveller();
	
	@Override
	public Traveller get(UUID uuid) {
		if (uuid == null) {
			return this.consoleTraveller;
		}
		
		if (!this.travellerMap.containsKey(uuid)) {
			this.travellerMap.put(uuid, new SpongeTraveller(uuid));
		}
		return this.travellerMap.get(uuid);
	}

}
