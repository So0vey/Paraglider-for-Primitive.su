package tictim.paraglider.forge;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import tictim.paraglider.api.ParagliderAPI;
import tictim.paraglider.network.ClientPacketHandler;
import tictim.paraglider.network.ParagliderNetwork;
import tictim.paraglider.network.ParagliderNetworkBase;
import tictim.paraglider.network.ServerPacketHandler;
import tictim.paraglider.network.message.Msg;
import tictim.paraglider.network.message.SyncLookAtMsg;
import tictim.paraglider.network.message.SyncMovementMsg;
import tictim.paraglider.network.message.SyncPlayerStateMapMsg;
import tictim.paraglider.network.message.SyncRemoteMovementMsg;
import tictim.paraglider.network.message.SyncVesselMsg;
import tictim.paraglider.network.message.SyncWindMsg;

import java.util.Optional;

public final class ForgeParagliderNetwork extends ParagliderNetworkBase{
	private static final ForgeParagliderNetwork instance = new ForgeParagliderNetwork();

	public static final String NETVERSION = "2.0";

	@NotNull public static ParagliderNetwork get(){
		return instance;
	}

	// just for loading the class
	public static void init(){}

	private final SimpleChannel net = NetworkRegistry.newSimpleChannel(
			ParagliderAPI.id("master"), () -> NETVERSION, NETVERSION::equals, NETVERSION::equals);

	private ForgeParagliderNetwork(){
		net.registerMessage(0, SyncPlayerStateMapMsg.class,
				SyncPlayerStateMapMsg::write, SyncPlayerStateMapMsg::read,
				(msg, ctx) -> {
					ctx.get().setPacketHandled(true);
					ctx.get().enqueueWork(() -> ClientPacketHandler.handleSyncPlayerStateMap(msg));
				}, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		net.registerMessage(1, SyncMovementMsg.class,
				SyncMovementMsg::write, SyncMovementMsg::read,
				(msg, ctx) -> {
					ctx.get().setPacketHandled(true);
					ctx.get().enqueueWork(() -> ClientPacketHandler.handleSyncMovement(msg));
				}, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		net.registerMessage(2, SyncRemoteMovementMsg.class,
				SyncRemoteMovementMsg::write, SyncRemoteMovementMsg::read,
				(msg, ctx) -> {
					ctx.get().setPacketHandled(true);
					ctx.get().enqueueWork(() -> ClientPacketHandler.handleSyncRemoteMovement(msg));
				}, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		net.registerMessage(3, SyncVesselMsg.class,
				SyncVesselMsg::write, SyncVesselMsg::read,
				(msg, ctx) -> {
					ctx.get().setPacketHandled(true);
					ctx.get().enqueueWork(() -> ClientPacketHandler.handleSyncVessel(msg));
				}, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		net.registerMessage(6, SyncLookAtMsg.class,
				SyncLookAtMsg::write, SyncLookAtMsg::read,
				(msg, ctx) -> {
					ctx.get().setPacketHandled(true);
				}, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		net.registerMessage(10, SyncWindMsg.class,
				SyncWindMsg::write, SyncWindMsg::read,
				(msg, ctx) -> {
					ctx.get().setPacketHandled(true);
					ctx.get().enqueueWork(() -> ClientPacketHandler.handleSyncWind(msg));
				}, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}

	@Override protected void sendToAll(@NotNull MinecraftServer server, @NotNull Msg msg){
		net.send(PacketDistributor.ALL.noArg(), msg);
	}
	@Override protected void sendToPlayer(@NotNull ServerPlayer player, @NotNull Msg msg){
		net.send(PacketDistributor.PLAYER.with(() -> player), msg);
	}
	@Override protected void sendToTracking(@NotNull MinecraftServer server, @NotNull Entity entity, @NotNull Msg msg){
		net.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
	}
	@Override protected void sendToTracking(@NotNull MinecraftServer server, @NotNull LevelChunk chunk, @NotNull Msg msg){
		net.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
	}
	@Override protected void sendToServer(@NotNull Msg msg){
		net.sendToServer(msg);
	}
}
