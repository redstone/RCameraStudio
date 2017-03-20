package net.redstoneore.rcamerastudio.rtext;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.format.TextColor;

public class RTextSponge extends RText {

	public RTextSponge() { }
	public RTextSponge(Object... start) {
		StringBuilder sb = new StringBuilder();
		
		for (Object part : start) {
			sb.append(part.toString());
		}
		
		textBuilder.append(Text.of(sb.toString()));
	}
	
	public Builder textBuilder = Text.builder();
	
	@Override
	protected RText startOf(Object... str) {
		return new RTextSponge(str);
	}

	@Override
	public RText colour(Object colour) {
		textBuilder.color((TextColor) colour);
		return this;
	}

	@Override
	public RText then(RText rtext) {
		RTextSponge sRText = (RTextSponge) rtext;
		textBuilder.append(sRText.textBuilder.toText());
		return this;
	}

}
