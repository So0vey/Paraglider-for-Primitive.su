package tictim.paraglider.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import tictim.paraglider.ParagliderMod;
import tictim.paraglider.api.movement.Movement;
import tictim.paraglider.api.stamina.Stamina;
import tictim.paraglider.network.message.Msg;
import tictim.paraglider.network.message.SyncLookAtMsg;
import tictim.paraglider.network.message.SyncMovementMsg;
import tictim.paraglider.network.message.SyncPlayerStateMapMsg;
import tictim.paraglider.network.message.SyncRemoteMovementMsg;
import tictim.paraglider.network.message.SyncVesselMsg;
import tictim.paraglider.network.message.SyncWindMsg;
import tictim.paraglider.wind.Wind;

public final class ClientPacketHandler{
	private ClientPacketHandler(){}

	// movement

	public static void handleSyncPlayerStateMap(SyncPlayerStateMapMsg msg){
		trace(Kind.MOVEMENT, msg);
		ParagliderMod.instance().setSyncedPlayerStateMap(msg.stateMap());
	}

	public static void handleSyncMovement(SyncMovementMsg msg){
		trace(Kind.MOVEMENT, msg);
		Minecraft mc = Minecraft.getInstance();
		if(mc.player==null) return;
		if(Movement.get(mc.player) instanceof SyncMovementHandle smh){
			smh.syncMovement(msg.state(), msg.stamina(), msg.depleted(), msg.recoveryDelay(), msg.reductionRate());
		}
	}

	public static void handleSyncRemoteMovement(SyncRemoteMovementMsg msg){
		trace(Kind.MOVEMENT, msg);
		Minecraft mc = Minecraft.getInstance();
		if(mc.level==null) return;
		Player player = mc.level.getPlayerByUUID(msg.entityId());
		if(player==null) return;
		if(Movement.get(player) instanceof SyncMovementHandle smh){
			smh.syncRemoteMovement(msg.state());
		}
	}

	public static void handleSyncVessel(SyncVesselMsg msg){
		trace(Kind.VESSEL, msg);
		Minecraft mc = Minecraft.getInstance();
		if(mc.player==null) return;
		Stamina.get(mc.player).setStamina(msg.stamina());
	}

	// wind

	public static void handleSyncWind(SyncWindMsg msg){
		trace(Kind.WIND, msg);
		ClientLevel world = Minecraft.getInstance().level;
		if(world==null) return;
		Wind wind = Wind.of(world);
		if(wind!=null) wind.put(msg.windChunk());
	}

	private static void trace(@NotNull Kind kind, @NotNull Msg msg){
		if(kind.isTraceEnabled()) ParagliderMod.LOGGER.debug("Received {} from server", msg);
	}
}
