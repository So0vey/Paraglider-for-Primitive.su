package tictim.paraglider.network;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import tictim.paraglider.ParagliderMod;
import tictim.paraglider.ParagliderUtils;
import tictim.paraglider.api.bargain.Bargain;
import tictim.paraglider.api.bargain.BargainResult;
import tictim.paraglider.network.message.Msg;

import static tictim.paraglider.ParagliderUtils.DIALOG_RNG;

public final class ServerPacketHandler{
	private ServerPacketHandler(){}

	private static void trace(@NotNull Kind kind, @NotNull ServerPlayer player, @NotNull Msg msg){
		if(kind.isTraceEnabled()) ParagliderMod.LOGGER.debug("Received {} from client {}", msg, player);
	}
}
