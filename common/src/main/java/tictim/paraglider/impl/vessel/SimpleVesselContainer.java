package tictim.paraglider.impl.vessel;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tictim.paraglider.api.Copy;
import tictim.paraglider.api.Serde;
import tictim.paraglider.api.vessel.VesselContainer;
import tictim.paraglider.config.Cfg;

/**
 * Standard implementation of {@link VesselContainer}.
 */
public class SimpleVesselContainer implements VesselContainer, Serde, Copy{
	protected final Player player;

	protected int staminaVessel;

	public SimpleVesselContainer(@Nullable Player player){
		this.player = player;
	}

	@Override public final int staminaVessel(){
		return staminaVessel;
	}

	@Override @NotNull public SetResult setStaminaVessel(int amount, boolean simulate, boolean playEffect){
		if(amount<0) return SetResult.TOO_LOW;
		if(amount>Cfg.get().maxStaminaVessels()) return SetResult.TOO_HIGH;
		int change = amount-this.staminaVessel;
		if(change==0) return SetResult.NO_CHANGE;
		if(!simulate){
			this.staminaVessel = amount;
			onChange(ActionType.STAMINA_VESSEL, change);
			if(playEffect) playEffect(ActionType.STAMINA_VESSEL, change);
		}
		return SetResult.OK;
	}

	@Override public int giveStaminaVessels(int amount, boolean simulate, boolean playEffect){
		amount = Math.min(amount, Cfg.get().maxStaminaVessels()-this.staminaVessel);
		if(amount<=0) return 0;
		if(!simulate){
			this.staminaVessel += amount;
			onChange(ActionType.STAMINA_VESSEL, amount);
			if(playEffect) playEffect(ActionType.STAMINA_VESSEL, amount);
		}
		return amount;
	}
	@Override public int takeStaminaVessels(int amount, boolean simulate, boolean playEffect){
		amount = Math.min(amount, this.staminaVessel);
		if(amount<=0) return 0;
		if(!simulate){
			this.staminaVessel -= amount;
			onChange(ActionType.STAMINA_VESSEL, -amount);
			if(playEffect) playEffect(ActionType.STAMINA_VESSEL, -amount);
		}
		return amount;
	}

	@Override public void copyFrom(@NotNull Object from){
		if(!(from instanceof VesselContainer vessels)) return;
		setStaminaVessel(vessels.staminaVessel(), false, false);
	}

	protected void onChange(@NotNull ActionType actionType, int change){}

	protected void playEffect(@NotNull ActionType actionType, int change){
		if(change>0) switch(actionType){
			case HEART_CONTAINER -> spawnParticle(ParticleTypes.HEART, 5+5*change);
			case STAMINA_VESSEL -> spawnParticle(ParticleTypes.HAPPY_VILLAGER, 7+7*change);
		}
	}

	protected void spawnParticle(@NotNull ParticleOptions particle, int count){
		if(player!=null&&player.level() instanceof ServerLevel sl){
			sl.sendParticles(particle, player.getX(), player.getY(.5), player.getZ(), count, 1, 2, 1, 0);
		}
	}

	@Override @NotNull public CompoundTag write(){
		CompoundTag tag = new CompoundTag();
		tag.putInt("staminaVessels", this.staminaVessel);
		return tag;
	}

	@Override public void read(@NotNull CompoundTag tag){
		this.staminaVessel = tag.getInt("staminaVessels");
	}

	@Override public String toString(){
		return "SimpleVesselContainer{"+
				"heartContainer="+"0"+
				", staminaVessel="+staminaVessel+
				'}';
	}

	public enum ActionType{
		HEART_CONTAINER,
		STAMINA_VESSEL,
		ESSENCE
	}
}
