package net.redstoneore.rcamerastudio.rtext;

import org.spongepowered.api.text.format.TextColors;

public class RColourSponge extends RColour {

	@Override
	protected RColour build() {
		this.BLACK = TextColors.BLACK;
		this.DARK_GRAY = TextColors.DARK_GRAY;
		this.GRAY = TextColors.GRAY;
		this.WHITE = TextColors.WHITE;

		this.DARK_RED = TextColors.DARK_RED;
		this.RED = TextColors.RED;

		this.DARK_GREEN = TextColors.DARK_GREEN;
		this.GREEN = TextColors.GREEN;

		this.BLUE = TextColors.BLACK;
		this.DARK_BLUE = TextColors.DARK_BLUE;

		this.AQUA = TextColors.AQUA;
		this.DARK_AQUA = TextColors.DARK_AQUA;

		this.DARK_PURPLE = TextColors.DARK_PURPLE;
		this.LIGHT_PURPLE = TextColors.LIGHT_PURPLE;
		
		this.GOLD = TextColors.GOLD;
		this.YELLOW = TextColors.YELLOW;

		return this;
	}

}
