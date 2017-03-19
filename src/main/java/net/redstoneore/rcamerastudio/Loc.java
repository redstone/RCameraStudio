package net.redstoneore.rcamerastudio;

import java.util.UUID;

import net.redstoneore.rson.Rson;

/**
 * This is just a cross-platform way to store locations
 */
public class Loc extends Rson<Loc> {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //

	// For Rson
	public Loc() { }
	
	public Loc(UUID world, Double x, Double y, Double z, Float yaw, Float pitch) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private UUID world;
	
	private Double x;
	private Double y;
	private Double z;
	private Float yaw;
	private Float pitch;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	public UUID getWorld() {
		return this.world;
	}
	
	public Double getX() {
		return this.x;
	}
	
	public Double getY() {
		return this.y;
	}
	
	public Double getZ() {
		return this.z;
	}
	
	public Float getPitch() {
		return this.pitch;
	}
	
	public Float getYaw() {
		return this.yaw;
	}
	
	public Double positionDifference(Loc otherLoc) {
		double cX = this.getX();
		double cY = this.getY();
		double cZ = this.getZ();

		double eX = otherLoc.getX();
		double eY = otherLoc.getY();
		double eZ = otherLoc.getZ();

		double dX = eX - cX;
		if (dX < 0.0) {
			dX = -dX;
		}
		double dZ = eZ - cZ;
		if (dZ < 0.0) {
			dZ = -dZ;
		}
		double dXZ = Math.hypot(dX, dZ);

		double dY = eY - cY;
		if (dY < 0.0) {
			dY = -dY;
		}
		double dXYZ = Math.hypot(dXZ, dY);

		return dXYZ;
	}
	
}
