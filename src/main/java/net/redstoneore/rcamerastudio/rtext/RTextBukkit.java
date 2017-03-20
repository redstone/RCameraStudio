package net.redstoneore.rcamerastudio.rtext;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class RTextBukkit extends RText {

	public RTextBukkit() { }
	public RTextBukkit(Object... start) {
		for (Object part : start) {
			this.message.addExtra(part.toString());
		}
	}
	
	public TextComponent message = new TextComponent();
	
	@Override
	protected RText startOf(Object... str) {
		return new RTextBukkit(str);
	}

	@Override
	public RText colour(Object colour) {
		this.message.setColor((ChatColor) colour); 
		return this;
	}

	@Override
	public RText then(RText rtext) {
		RTextBukkit bRText = (RTextBukkit) rtext;
		this.message.addExtra(bRText.message);

		return this;
	}

}
