package eu.crushedpixel.camerastudio;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Util {

	public static double round(double unrounded, int precision) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(precision, 4);
		return rounded.doubleValue();
	}

	public static int parseTimeString(String timeString) throws java.text.ParseException {
		Date length;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("mm'm'ss's'");
			length = formatter.parse(timeString);
		} catch (Exception e) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("m'm'ss's'");
				length = formatter.parse(timeString);
			} catch (Exception e1) {
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("m'm's's'");
					length = formatter.parse(timeString);
				} catch (Exception e2) {
					try {
						SimpleDateFormat formatter = new SimpleDateFormat("mm'm's's'");
						length = formatter.parse(timeString);
					} catch (Exception e3) {
						try {
							SimpleDateFormat formatter = new SimpleDateFormat("mm'm'");
							length = formatter.parse(timeString);
						} catch (Exception e4) {
							try {
								SimpleDateFormat formatter = new SimpleDateFormat("m'm'");
								length = formatter.parse(timeString);
							} catch (Exception e5) {
								try {
									SimpleDateFormat formatter = new SimpleDateFormat("s's'");
									length = formatter.parse(timeString);
								} catch (Exception e6) {
									SimpleDateFormat formatter = new SimpleDateFormat("ss's'");
									length = formatter.parse(timeString);
								}
							}
						}
					}
				}
			}
		}

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(length);

		int time = (cal.get(12) * 60 + cal.get(13)) * 20;
		
		return time;
	}
	
	public static double positionDifference(Location cLoc, Location eLoc) {
		double cX = cLoc.getX();
		double cY = cLoc.getY();
		double cZ = cLoc.getZ();

		double eX = eLoc.getX();
		double eY = eLoc.getY();
		double eZ = eLoc.getZ();

		double dX = eX - cX;
		if (dX < 0.0D) {
			dX = -dX;
		}
		double dZ = eZ - cZ;
		if (dZ < 0.0D) {
			dZ = -dZ;
		}
		double dXZ = Math.hypot(dX, dZ);

		double dY = eY - cY;
		if (dY < 0.0D) {
			dY = -dY;
		}
		double dXYZ = Math.hypot(dXZ, dY);

		return dXYZ;
	}
	
	public static String getSerializedLocation(Location loc) {
        return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getWorld().getUID() + ";" + loc.getPitch() + ";" + loc.getYaw();
    }
 
    public static Location getDeserializedLocation(String s) {
            String [] parts = s.split(";");
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            double z = Double.parseDouble(parts[2]);
            UUID u = UUID.fromString(parts[3]);
            float pitch = Float.parseFloat(parts[4]);
            float yaw = Float.parseFloat(parts[5]);
            World w = Bukkit.getServer().getWorld(u);
            return new Location(w, x, y, z, yaw, pitch);
    }

}
