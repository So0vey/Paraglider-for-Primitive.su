package tictim.paraglider.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import tictim.paraglider.ParagliderMod;
import tictim.paraglider.config.PlayerStateMapConfig;
import tictim.paraglider.impl.movement.PlayerStateMap;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.commands.arguments.EntityArgument.getPlayer;
import static net.minecraft.commands.arguments.EntityArgument.player;
import static net.minecraft.commands.arguments.coordinates.Vec3Argument.vec3;

public final class ParagliderCommands{
	private ParagliderCommands(){}

	public static LiteralArgumentBuilder<CommandSourceStack> register(){
		return literal("paraglider")
				.then(queryVessel())
				.then(setVessel(SetType.set))
				.then(setVessel(SetType.give))
				.then(setVessel(SetType.take))
				.then(reloadPlayerStates());
	}

	private static LiteralArgumentBuilder<CommandSourceStack> queryVessel(){
		return literal("query")
				.requires(s -> s.hasPermission(1))
				.then(literal(ResourceType.HEART.name)
						.then(argument("player", player())
								.executes(ctx -> ResourceType.HEART.tell(ctx.getSource(), getPlayer(ctx, "player")))))
				.then(literal(ResourceType.STAMINA.name)
						.then(argument("player", player())
								.executes(ctx -> ResourceType.STAMINA.tell(ctx.getSource(), getPlayer(ctx, "player")))))
				.then(literal(ResourceType.ESSENCE.name)
						.then(argument("player", player())
								.executes(ctx -> ResourceType.ESSENCE.tell(ctx.getSource(), getPlayer(ctx, "player")))));
	}

	private static LiteralArgumentBuilder<CommandSourceStack> setVessel(@NotNull SetType type){
		return literal(type.name())
				.requires(s -> s.hasPermission(2))
				.then(literal(ResourceType.HEART.name)
						.then(argument("player", player())
								.then(argument("amount", integer(0))
										.executes(ctx -> ResourceType.HEART.run(
												ctx.getSource(),
												getPlayer(ctx, "player"),
												getInteger(ctx, "amount"),
												type)))))
				.then(literal(ResourceType.STAMINA.name)
						.then(argument("player", player())
								.then(argument("amount", integer(0))
										.executes(ctx -> ResourceType.STAMINA.run(
												ctx.getSource(),
												getPlayer(ctx, "player"),
												getInteger(ctx, "amount"),
												type)))))
				.then(literal(ResourceType.ESSENCE.name)
						.then(argument("player", player())
								.then(argument("amount", integer(0))
										.executes(ctx -> ResourceType.ESSENCE.run(
												ctx.getSource(),
												getPlayer(ctx, "player"),
												getInteger(ctx, "amount"),
												type)))));
	}

	private static LiteralArgumentBuilder<CommandSourceStack> reloadPlayerStates(){
		return literal("reloadPlayerStates")
				.requires(s -> s.hasPermission(3))
				.executes(context -> reloadPlayerStates(context.getSource()));
	}

	private static int reloadPlayerStates(@NotNull CommandSourceStack source){
		MinecraftServer server = source.getServer();
		ParagliderMod.instance().getPlayerStateMapConfig().scheduleReload(server,
				new PlayerStateMapConfig.Callback(){
					@Override public void onSuccess(@NotNull PlayerStateMap stateMap, boolean updated){
						source.sendSuccess(() -> Component.translatable("commands.paraglider.reload_player_states.success"), true);
					}
					@Override public void onFail(@NotNull PlayerStateMap stateMap, @NotNull RuntimeException exception, boolean update){
						source.sendFailure(Component.translatable("commands.paraglider.reload_player_states.fail"));
					}
				});
		return 1;
	}

	private enum SetType{
		set, give, take
	}

	private enum ResourceType{
		HEART("heart_container"),
		STAMINA("stamina_vessel"),
		ESSENCE("essence");

		private final String name;

		private final String getResult;
		private final String setSuccess;
		private final String setNoChange;
		private final String giveSuccess;
		private final String takeSuccess;

		private final String setTooHigh;
		private final String setTooLow;
		private final String setFail;
		private final String giveFail;
		private final String takeFail;

		ResourceType(String name){
			this.name = name;
			this.getResult = "commands.paraglider.get."+name+".result";
			this.setSuccess = "commands.paraglider.set."+name+".success";
			this.setNoChange = "commands.paraglider.set."+name+".no_change";
			this.giveSuccess = "commands.paraglider.give."+name+".success";
			this.takeSuccess = "commands.paraglider.take."+name+".success";

			this.setTooHigh = "commands.paraglider.set."+name+".too_high";
			this.setTooLow = "commands.paraglider.set."+name+".too_low";
			this.setFail = "commands.paraglider.set."+name+".fail";
			this.giveFail = "commands.paraglider.give."+name+".fail";
			this.takeFail = "commands.paraglider.take."+name+".fail";
		}

		private int tell(@NotNull CommandSourceStack source, @NotNull Player player){
			int value = switch(this){
                case HEART -> 0;
                case STAMINA -> 0;
				case ESSENCE -> 0;
			};
			source.sendSuccess(() -> Component.translatable(getResult, player.getDisplayName(), value), false);
			return value;
		}

		private int run(@NotNull CommandSourceStack source,
		                @NotNull Player player,
		                int amount,
		                @NotNull SetType type){
			switch(type){
				case set -> {
					return 0;
//					-1 and 1 etc
				}
				case give -> {
					return 0;
				}
				case take -> {
					return 0;
				}
			}
			throw new IllegalStateException("Unreachable");
		}


		private int give(@NotNull int amount, boolean simulate, boolean playEffect){
			return switch(this){
                case HEART -> 0;
                case STAMINA -> 0;
				case ESSENCE -> 0;
			};
		}

		private int take(@NotNull int amount, boolean simulate, boolean playEffect){
			return switch(this){
                case HEART -> 0;
                case STAMINA -> 0;
				case ESSENCE -> 0;
			};
		}
	}
}
