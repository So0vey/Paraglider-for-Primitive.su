package tictim.paraglider.network.message;

import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public sealed interface Msg permits SyncLookAtMsg, SyncMovementMsg, SyncPlayerStateMapMsg, SyncRemoteMovementMsg, SyncVesselMsg, SyncWindMsg{
	void write(@NotNull FriendlyByteBuf buffer);
}
