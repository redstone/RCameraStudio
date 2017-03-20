package net.redstoneore.rcamerastudio.rtext;

import net.md_5.bungee.api.ChatColor;

public class RColourBukkit extends RColour {

	@Override
	protected RColour build() {
		this.BLACK = ChatColor.BLACK;// = new String(new char[] {'\u00A7', '0'});
		this.DARK_GRAY = ChatColor.DARK_GRAY;// = new String(new char[] {'\u00A7', '8'});
		this.GRAY = ChatColor.GRAY;// = new String(new char[] {'\u00A7', '7'});
		this.WHITE = ChatColor.WHITE;// = new String(new char[] {'\u00A7', 'f'});

		this.DARK_RED = ChatColor.DARK_RED;// = new String(new char[] {'\u00A7', '4'});
		this.RED = ChatColor.RED;// = new String(new char[] {'\u00A7', 'c'});

		this.DARK_GREEN = ChatColor.DARK_GREEN;// = new String(new char[] {'\u00A7', '2'});
		this.GREEN = ChatColor.GREEN;// = new String(new char[] {'\u00A7', 'a'});

		this.BLUE = ChatColor.BLUE;// = new String(new char[] {'\u00A7', '9'});
		this.DARK_BLUE = ChatColor.DARK_BLUE;// = new String(new char[] {'\u00A7', '1'});

		this.AQUA = ChatColor.AQUA;// = new String(new char[] {'\u00A7', 'b'});
		this.DARK_AQUA = ChatColor.DARK_AQUA;// = new String(new char[] {'\u00A7', '3'});

		this.DARK_PURPLE = ChatColor.DARK_PURPLE;// = new String(new char[] {'\u00A7', '5'});
		this.LIGHT_PURPLE = ChatColor.LIGHT_PURPLE;// = new String(new char[] {'\u00A7', 'd'});
		
		this.GOLD = ChatColor.GOLD;// = new String(new char[] {'\u00A7', '6'});
		this.YELLOW = ChatColor.YELLOW;// = new String(new char[] {'\u00A7', 'e'});

		return this;
	}

}
