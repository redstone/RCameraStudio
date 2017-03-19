package net.redstoneore.rcamerastudio;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

}
