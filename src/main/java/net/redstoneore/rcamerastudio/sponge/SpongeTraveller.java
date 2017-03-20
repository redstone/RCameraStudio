package net.redstoneore.rcamerastudio.sponge;

import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import net.redstoneore.rcamerastudio.Loc;
import net.redstoneore.rcamerastudio.Traveller;
import net.redstoneore.rcamerastudio.rtext.RText;
import net.redstoneore.rcamerastudio.rtext.RTextSponge;

public class SpongeTraveller extends Traveller {

	public SpongeTraveller(UUID uuid) {
		// player traveller
		if (uuid != null) {
		    this.cmdSrc = Sponge.getServer().getPlayer(uuid).get().getCommandSource().get();
		    this.player = Sponge.getServer().getPlayer(uuid).get();
		}
	}

	public SpongeTraveller() {
		// console traveller
		this.cmdSrc = Sponge.getServer().getConsole();
	}
	
	private CommandSource cmdSrc = null;
	private Player player = null;
	
	@Override
	public UUID getUUID() {
		return this.player.getUniqueId();
	}

	@Override
	public Loc getLoc() {
		Location<World> location = this.player.getLocation();
		Vector3d rotation = this.player.getRotation();
		
		Loc loc = new Loc(this.player.getWorld().getUniqueId(), location.getX(), location.getY(), location.getZ(), (float) rotation.getY(), (float) rotation.getX());
		
		return loc;
	}

	@Override
	public void msg(RText msg) {
		RTextSponge sRText = (RTextSponge) msg;
		this.cmdSrc.sendMessage(sRText.textBuilder.build());
	}

	@Override
	public void teleport(Loc loc) {
		World world = Sponge.getServer().getWorld(loc.getWorld()).get();
		
		Location<World> location = new Location<World>(world, loc.getX(), loc.getY(), loc.getZ());
		Vector3d rotation = new Vector3d((double) loc.getPitch(), (double) loc.getYaw(), 0D);
		
		this.player.setLocationAndRotation(location, rotation);
	}

	@Override
	public void setAllowFlight(Boolean allowFlight) {
		this.player.offer(Keys.CAN_FLY, allowFlight);
	}

	@Override
	public void setFlying(Boolean flying) {
		this.player.offer(Keys.IS_FLYING, flying);
	}

	@Override
	public Boolean getAllowFlight() {
		return this.player.get(Keys.CAN_FLY).get();
	}

	@Override
	public Boolean getFlying() {
		return this.player.get(Keys.IS_FLYING).get();
	}

	@Override
	public void runLater(Runnable task, Long time) {
		Task.builder()
			.delayTicks(time)
			.execute(task)
			.submit(RCameraStudioSponge.get());
	}

	@Override
	public Boolean hasPermission(String permission) {
		return (this.cmdSrc.hasPermission(permission));
	}

	@Override
	public Boolean isPlayer() {
		return this.player != null;
	}

}
