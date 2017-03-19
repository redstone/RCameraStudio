package net.redstoneore.rcamerastudio;

import java.util.UUID;

public abstract class Travellers {

	// -------------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------------- //
	
	private static Travellers impl = null;
	public static Travellers impl() { return impl; }
	public static void impl(Travellers newImpl) { impl = newImpl; }
	
	// -------------------------------------------------- //
	// ABSTRACT METHODS
	// -------------------------------------------------- //

	public abstract Traveller get(UUID uuid);
	
}