package net.redstoneore.rcamerastudio;

import java.util.UUID;

public abstract class Travellers {

	private static Travellers impl = null;
	public static Travellers getImpl() { return impl; }
	public static void setImpl(Travellers impl) { Travellers.impl = impl; }
	
	public abstract Traveller get(UUID uuid);
	
}
