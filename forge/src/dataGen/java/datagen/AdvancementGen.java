package datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import tictim.paraglider.api.ParagliderAPI;
import tictim.paraglider.contents.Contents;
import tictim.paraglider.contents.ParagliderAdvancements;
import tictim.paraglider.contents.ParagliderTags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;
import static net.minecraft.advancements.critereon.ItemPredicate.Builder.item;
import static tictim.paraglider.api.ParagliderAPI.MODID;

public class AdvancementGen extends ForgeAdvancementProvider{
	public AdvancementGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper){
		super(output, registries, existingFileHelper, List.of((r, s, e) -> {
			Contents contents = Contents.get();
			Advancement root = advancement(
					new ItemStack(contents.paraglider()),
					"advancement.paraglider",
					ParagliderAPI.id("textures/gui/advancement_background.png"),
					FrameType.TASK,
					false,
					false,
					false)
					.addCriterion("crafting_table", hasItems(Blocks.CRAFTING_TABLE))
					.save(s, MODID+":root");
			Advancement paraglider = advancement(
					new ItemStack(contents.paraglider()),
					"advancement.paraglider.paraglider",
					FrameType.GOAL,
					true,
					true,
					false)
					.parent(root)
					.addCriterion("paraglider", hasItems(item().of(ParagliderTags.PARAGLIDERS).build()))
					.save(s, MODID+":paraglider");
			Advancement prayToTheGoddess = advancement(
					new ItemStack(contents.goddessStatue()),
					"advancement.paraglider.pray_to_the_goddess",
					FrameType.GOAL,
					true,
					true,
					false)
					.parent(root)
					.addCriterion("bargain", new ImpossibleTrigger.TriggerInstance())
					.save(s, ParagliderAdvancements.PRAY_TO_THE_GODDESS.toString());
			Advancement statuesBargain = advancement(
					new ItemStack(contents.hornedStatue()),
					"advancement.paraglider.statues_bargain",
					FrameType.GOAL,
					true,
					true,
					false)
					.parent(root)
					.addCriterion("bargain", new ImpossibleTrigger.TriggerInstance())
					.save(s, ParagliderAdvancements.STATUES_BARGAIN.toString());
		}));
	}

	private static Advancement.Builder advancement(ItemStack stack,
	                                               String display,
	                                               FrameType frameType,
	                                               boolean showToast,
	                                               boolean announceToChat,
	                                               boolean hidden){
		return advancement(stack, display, null, frameType, showToast, announceToChat, hidden);
	}
	private static Advancement.Builder advancement(ItemStack stack,
	                                               String display,
	                                               @Nullable ResourceLocation background,
	                                               FrameType frameType,
	                                               boolean showToast,
	                                               boolean announceToChat,
	                                               boolean hidden){
		return Advancement.Builder.advancement().display(stack,
				Component.translatable(display),
				Component.translatable(display+".desc"),
				background,
				frameType,
				showToast,
				announceToChat,
				hidden);
	}
}
