package tictim.paraglider.network;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class NetUtils{
	private NetUtils(){}

	@Nullable public static Vec3 readLookAt(@NotNull FriendlyByteBuf buffer){
		return buffer.readBoolean() ? new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()) : null;
	}

	public static void writeLookAt(@NotNull FriendlyByteBuf buffer, @Nullable Vec3 lookAt){
		buffer.writeBoolean(lookAt!=null);
		if(lookAt!=null){
			buffer.writeDouble(lookAt.x);
			buffer.writeDouble(lookAt.y);
			buffer.writeDouble(lookAt.z);
		}
	}
}
