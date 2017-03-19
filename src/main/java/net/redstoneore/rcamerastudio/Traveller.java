package net.redstoneore.rcamerastudio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


import net.redstoneore.rcamerastudio.bukkit.BukkitTraveller;
import net.redstoneore.rcamerastudio.config.Config;
import net.redstoneore.rcamerastudio.replay.Replay;

public abstract class Traveller {

	// --------------------------------------------------
	// STATIC
	// --------------------------------------------------

	private static Map<UUID, Traveller> pointsMap = new HashMap<UUID, Traveller>();
	public static Traveller get(UUID uuid) {
		if (!pointsMap.containsKey(uuid)) {
			pointsMap.put(uuid, new BukkitTraveller(uuid));
		}
		return pointsMap.get(uuid);
	}
	
	// --------------------------------------------------
	// FIELDS
	// --------------------------------------------------
		
	private List<Loc> pointsList = new ArrayList<Loc>();
	
	private Boolean travelling = false;
	
	private Boolean prevAllowedFlight = false;
	private Boolean prevFlying = false;
	
	private Loc finishLocation = null;
	
	// --------------------------------------------------
	// METHODS
	// --------------------------------------------------
	
	/**
	 * Get all locations
	 * @return a list of locations
	 */
	public List<Loc> getAll() {
		return this.pointsList;
	}

	/**
	 * Add a point at a location
	 * @param location
	 */
	public void add(Loc location) {
		this.pointsList.add(location);
	}
	
	/**
	 * Add point at current location
	 */
	public void add() {
		add(this.getLoc());
	}

	/**
	 * Get amount of points
	 * @return amount of points
	 */
	public int size() {
		return this.pointsList.size();
	}

	/**
	 * Remove a point from the array (index)
	 * @param i
	 */
	public void remove(int i) {
		this.pointsList.remove(i);
	}

	/**
	 * Clear all points
	 */
	public void clear() {
		this.pointsList.clear();
	}

	/**
	 * Get point at index
	 * @param i
	 * @return
	 */
	public Loc get(int i) {
		return this.pointsList.get(i);
	}

	/**
	 * Set all points
	 * @param pointsList
	 */
	public void set(List<Loc> pointsList) {
		this.pointsList = pointsList;
	}
	
	/**
	 * Set a player as travelling or not, will call setAllowedFlight and setFlying. See config for other specifics. 
	 * @param travelling
	 */
	public void travelling(Boolean travelling) {
		this.travelling = travelling;
		
		this.prevAllowedFlight = this.getAllowFlight();;
		this.prevFlying = this.getFlying();
		
		if (this.travelling) {
			this.setAllowFlight(true);
			this.setFlying(true);
		} else {
			
			if (Config.get().resetAfter > 0) {
				this.runLater(new Runnable() {
					@Override
					public void run() {
						setAllowFlight(prevAllowedFlight);
						setFlying(prevFlying);
						
						if (finishLocation != null) {
							teleport(finishLocation);
							finishLocation = null;
						}
						
					}
				}, Config.get().resetAfter);
			} else {
				if (this.finishLocation != null) {
					this.teleport(this.finishLocation);
					this.finishLocation = null;
				}
			}
		}
	}
	
	/**
	 * Returns travelling value
	 * @return
	 */
	public Boolean travelling() {
		return this.travelling;
	}
	
	/**
	 * Set a finish location, will be placed here when they stop travelling
	 * @param location
	 */
	public void setFinishLocation(Loc location) {
		this.finishLocation = location;
	}
	
	/**
	 * Make a player travel between a list of locations, with a silent or not option
	 * @param locations   Locations to travel across
	 * @param time        Time of executing
	 * @param silent      Silent or not
	 */
	public void travel(List<Loc> locations, Integer time, Boolean silent) {
		if (silent) {
			travel(locations, time, silent);
		} else {
			travel(locations, time,
					"An error occured during travelling", 
					"Travelling completed."
			);
		}
	}

	/**
	 * Make a player travel between a list of locations, with no messages
	 * @param time       Time of executing
	 * @param silent      Silent or not
	 */
	public void travel(Integer time, Boolean silent) {
		travel(this.pointsList, time, silent);
	}
	
	/**
	 * Make a player travel between a list of locations, with messages
	 * @param locations   Locations to travel across
	 * @param time        Time of executing
	 */
	public void travel(List<Loc> locations, Integer time, String failMessage, String completedMessage) {
		List<Double> diffs = new ArrayList<Double>();
		List<Integer> travelTimes = new ArrayList<Integer>();

		Double totalDiff = 0.0D;

		for (int i = 0; i < locations.size() - 1; i++) {
			Loc start = locations.get(i);
			Loc next = locations.get(i + 1);
			
			Double diff = start.positionDifference(next);
			totalDiff += diff;
			diffs.add(diff);
		}

		for (Iterator<Double> n = diffs.iterator(); n.hasNext();) {
			double d = ((Double) n.next()).doubleValue();
			travelTimes.add(Integer.valueOf((int) (d / totalDiff * time)));
		}

		final List<Loc> tps = new ArrayList<Loc>();

		UUID world = this.getLoc().getWorld();

		for (int i = 0; i < locations.size() - 1; i++) {
			Loc s = locations.get(i);
			Loc n = locations.get(i + 1);
			int t = (travelTimes.get(i)).intValue();

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
				Loc l = new Loc(world, s.getX() + moveX / t * x, s.getY() + moveY / t * x,
						s.getZ() + moveZ / t * x, (float) (s.getYaw() + d * x),
						(float) (s.getPitch() + movePitch / t * x));
				tps.add(l);
			}
			
		}
		
		try {
			this.travelling(true);
			
			this.teleport(tps.get(0));
			
			this.runLater(new Runnable() {
				private int ticks = 0;

				public void run() {
					if (this.ticks < tps.size()) {

						teleport(tps.get(this.ticks));
						
						// check if they cancelled 
						if (travelling()) {
							runLater(this, 1L);
						} 
						this.ticks += 1;
					} else {
						travelling(false);
						
						if (completedMessage != null) {
							msg(completedMessage);
						}
					}
				}
			}, 1L);
		} catch (Exception e) {
			if (failMessage != null) {
				this.msg(failMessage);
			}
			e.printStackTrace();
		}
	}

	/**
	 * Set points list 
	 * @param replay
	 */
	public void set(Optional<Replay> replay) {
		if (!replay.isPresent()) return;
		this.set(replay.get());
	}
	
	/**
	 * Set points list
	 * @param replay
	 */
	public void set(Replay replay) {
		this.pointsList = replay.points;
	}
	
	public abstract UUID getUUID();
	
	public abstract Loc getLoc();
	
	public abstract void msg(String msg);
	
	public abstract void teleport(Loc loc);
	
	public abstract void setAllowFlight(Boolean allowFlight);
	public abstract void setFlying(Boolean flying);
	
	public abstract Boolean getAllowFlight();
	public abstract Boolean getFlying();
	
	public abstract void runLater(Runnable task, Long time);
	
	public abstract Boolean hasPermission(String permission);

	public abstract Boolean isPlayer();
	
}
