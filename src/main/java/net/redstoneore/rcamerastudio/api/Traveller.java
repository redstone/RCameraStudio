package net.redstoneore.rcamerastudio.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.redstoneore.rcamerastudio.Loc;
import net.redstoneore.rcamerastudio.config.Config;
import net.redstoneore.rcamerastudio.replay.Replay;
import net.redstoneore.rcamerastudio.rtext.RColour;
import net.redstoneore.rcamerastudio.rtext.RText;

public abstract class Traveller {

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
		
	private List<Loc> pointsList = new ArrayList<Loc>();
	
	private Boolean travelling = false;
	
	// Store these so we can reset them back to their original settings
	private Boolean prevAllowedFlight = false;
	private Boolean prevFlying = false;
	
	private Loc finishLocation = null;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	/**
	 * Get all locations
	 * @return a list of locations
	 */
	public List<Loc> getPoints() {
		return this.pointsList;
	}

	/**
	 * Add a point at a location
	 * @param location
	 */
	public void addPoint(Loc point) {
		this.pointsList.add(point);
	}
	
	/**
	 * Add point at current location
	 */
	public void addPoint() {
		addPoint(this.getLoc());
	}

	/**
	 * Get amount of points
	 * @return amount of points
	 */
	public int countPoints() {
		return this.pointsList.size();
	}

	/**
	 * Remove a point (use point number NOT array index)
	 * @param i
	 */
	public void removePoint(int pointNumber) {
		this.pointsList.remove(pointNumber-1);
	}

	/**
	 * Clear all points
	 */
	public void clearPoints() {
		this.pointsList.clear();
	}

	/**
	 * Get point (use point number NOT array index)
	 * @param i
	 * @return
	 */
	public Loc get(int pointNumber) {
		return this.pointsList.get(pointNumber-1);
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

		for (int i = 0; i < locations.size()-1; i++) {
			Loc thisPoint = locations.get(i);
			Loc nextPoint = locations.get(i + 1);
			int travelPoint = travelTimes.get(i);

			double moveX = nextPoint.getX() - thisPoint.getX();
			double moveY = nextPoint.getY() - thisPoint.getY();
			double moveZ = nextPoint.getZ() - thisPoint.getZ();
			double movePitch = nextPoint.getPitch() - thisPoint.getPitch();

			double yawDiff = Math.abs(nextPoint.getYaw() - thisPoint.getYaw());
			double c = 0.0;

			if (yawDiff <= 180.0) {
				if (thisPoint.getYaw() < nextPoint.getYaw()) {
					c = yawDiff;
				} else {
					c = -yawDiff;
				}
			} else if (thisPoint.getYaw() < nextPoint.getYaw()) {
				c = -(360.0 - yawDiff);
			} else {
				c = 360.0 - yawDiff;
			}

			double d = c / travelPoint;

			for (int x = 0; x < travelPoint; x++) {
				Loc l = new Loc(
					world,
					thisPoint.getX() + moveX / travelPoint * x,
					thisPoint.getY() + moveY / travelPoint * x,
					thisPoint.getZ() + moveZ / travelPoint * x,
					(float) (thisPoint.getYaw() + d * x),
					(float) (thisPoint.getPitch() + movePitch / travelPoint * x)
				);
				
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
							msg(RText.of(completedMessage).colour(RColour.impl().GREEN));
						}
					}
				}
			}, 1L);
		} catch (Exception e) {
			if (failMessage != null) {
				msg(RText.of(failMessage).colour(RColour.impl().RED));
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
	
	public abstract void msg(RText msg);
	
	public abstract void teleport(Loc loc);
	
	public abstract void setAllowFlight(Boolean allowFlight);
	public abstract void setFlying(Boolean flying);
	
	public abstract Boolean getAllowFlight();
	public abstract Boolean getFlying();
	
	public abstract void runLater(Runnable task, Long time);
	
	public abstract Boolean hasPermission(String permission);

	public abstract Boolean isPlayer();
	
}
