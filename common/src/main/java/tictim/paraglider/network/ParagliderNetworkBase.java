package tictim.paraglider.network;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tictim.paraglider.ParagliderMod;
import tictim.paraglider.impl.movement.PlayerStateMap;
import tictim.paraglider.network.message.Msg;
import tictim.paraglider.network.message.SyncLookAtMsg;
import tictim.paraglider.network.message.SyncMovementMsg;
import tictim.paraglider.network.message.SyncPlayerStateMapMsg;
import tictim.paraglider.network.message.SyncRemoteMovementMsg;
import tictim.paraglider.network.message.SyncVesselMsg;
import tictim.paraglider.network.message.SyncWindMsg;
import tictim.paraglider.wind.WindChunk;

import java.util.Map;

@SuppressWarnings("SameParameterValue")
public abstract class ParagliderNetworkBase implements ParagliderNetwork{
	@Override public void syncStateMap(@NotNull ServerPlayer player, @NotNull PlayerStateMap stateMap){
		SyncPlayerStateMapMsg msg = new SyncPlayerStateMapMsg(stateMap);
		traceSendToPlayer(Kind.MOVEMENT, player, msg);
		sendToPlayer(player, msg);
	}

	protected abstract void sendToAll(@NotNull MinecraftServer server, @NotNull Msg msg);
	protected abstract void sendToPlayer(@NotNull ServerPlayer player, @NotNull Msg msg);
	protected abstract void sendToTracking(@NotNull MinecraftServer server, @NotNull Entity entity, @NotNull Msg msg);
	protected abstract void sendToTracking(@NotNull MinecraftServer server, @NotNull LevelChunk chunk, @NotNull Msg msg);
	protected abstract void sendToServer(@NotNull Msg msg);

	@Override public void syncStateMapToAll(@NotNull MinecraftServer server, @NotNull PlayerStateMap stateMap){
		SyncPlayerStateMapMsg msg = new SyncPlayerStateMapMsg(stateMap);
		traceSendToAll(Kind.MOVEMENT, msg);
		sendToAll(server, msg);
	}

	@Override public void syncMovement(@NotNull ServerPlayer player,
	                                   @NotNull ResourceLocation state,
	                                   int stamina,
	                                   boolean depleted,
	                                   int recoveryDelay,
	                                   double reductionRate){
		SyncMovementMsg msg = new SyncMovementMsg(state, stamina, depleted, recoveryDelay, reductionRate);
		traceSendToPlayer(Kind.MOVEMENT, player, msg);
		sendToPlayer(player, msg);
		syncRemoteMovement(player.server, player, state);
	}

	@Override public void syncRemoteMovement(@NotNull MinecraftServer server, @NotNull Entity entity, @NotNull ResourceLocation state){
		SyncRemoteMovementMsg msg = new SyncRemoteMovementMsg(entity.getUUID(), state);
		traceSendToTracking(Kind.MOVEMENT, entity, msg);
		sendToTracking(server, entity, msg);
	}

	@Override public void syncRemoteMovement(@NotNull Entity entity, @NotNull ServerPlayer target, @NotNull ResourceLocation state){
		SyncRemoteMovementMsg msg = new SyncRemoteMovementMsg(entity.getUUID(), state);
		traceSendToPlayer(Kind.MOVEMENT, target, msg);
		sendToPlayer(target, msg);
	}

	@Override public void syncVessels(@NotNull ServerPlayer player, int stamina, int staminaVessels){
		SyncVesselMsg msg = new SyncVesselMsg(stamina, staminaVessels);
		traceSendToPlayer(Kind.VESSEL, player, msg);
		sendToPlayer(player, msg);
	}

	@Override public void syncWind(@NotNull MinecraftServer server, @NotNull LevelChunk chunk, @NotNull WindChunk windChunk){
		SyncWindMsg msg = new SyncWindMsg(windChunk);
		traceSendToTracking(Kind.WIND, chunk, msg);
		sendToTracking(server, chunk, msg);
	}

	protected static void traceSendToAll(@NotNull Kind kind, @NotNull Msg msg){
		if(kind.isTraceEnabled()) ParagliderMod.LOGGER.debug("Dispatching {} to clients", msg);
	}
	protected static void traceSendToPlayer(@NotNull Kind kind, @NotNull ServerPlayer player, @NotNull Msg msg){
		if(kind.isTraceEnabled()) ParagliderMod.LOGGER.debug("Dispatching {} to {}", msg, player);
	}
	protected static void traceSendToTracking(@NotNull Kind kind, @NotNull Entity entity, @NotNull Msg msg){
		if(kind.isTraceEnabled()) ParagliderMod.LOGGER.debug("Dispatching {} to clients tracking entity {}", msg, entity);
	}
	protected static void traceSendToTracking(@NotNull Kind kind, @NotNull LevelChunk chunk, @NotNull Msg msg){
		if(kind.isTraceEnabled()) ParagliderMod.LOGGER.debug("Dispatching {} to clients tracking chunk {}", msg, chunk);
	}
	protected static void traceSendToServer(@NotNull Kind kind, @NotNull Msg msg){
		if(kind.isTraceEnabled()) ParagliderMod.LOGGER.debug("Dispatching {} to server", msg);
	}
}
