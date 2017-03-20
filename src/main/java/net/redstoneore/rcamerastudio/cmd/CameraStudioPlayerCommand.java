package net.redstoneore.rcamerastudio.cmd;

import java.util.List;
import java.util.Optional;

import net.redstoneore.rcamerastudio.Traveller;
import net.redstoneore.rcamerastudio.rtext.RText;

public abstract class CameraStudioPlayerCommand<T> extends CameraStudioCommand<T> {

	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public void preExec(Traveller traveller, List<String> arguments) {
		if (!traveller.isPlayer()) {
			msg(RText.of("This command can only be used by players."));
			return;
		}
		
		super.preExec(traveller, arguments);
	}
	
	public Traveller getTraveller() {
		return super.traveller().get();
	}
	
	/**
	 * <b>Discouraged.</b> Use getTraveller()
	 */
	@Override
	public Optional<Traveller> traveller() {
		return super.traveller();
	}
	
	
}
