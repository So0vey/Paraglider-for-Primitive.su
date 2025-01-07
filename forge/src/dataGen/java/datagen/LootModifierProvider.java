package datagen;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.jetbrains.annotations.NotNull;
import tictim.paraglider.api.ParagliderAPI;
import tictim.paraglider.forge.contents.loot.LootConditions;
import tictim.paraglider.forge.contents.loot.ParagliderLoot;
import tictim.paraglider.forge.contents.loot.VesselLoot;

public class LootModifierProvider extends GlobalLootModifierProvider{
	public LootModifierProvider(@NotNull PackOutput output){
		super(output, ParagliderAPI.MODID);
	}

	@Override protected void start() {
		add("totw_reworked/chest", new ParagliderLoot(
				false,
				LootTableIdCondition.builder(new ResourceLocation("totw_reworked", "tower_chest")).build()
		));
		add("totw_reworked/ocean_chest", new ParagliderLoot(
				true,
				LootTableIdCondition.builder(new ResourceLocation("totw_reworked", "ocean_tower_chest")).build()
		));

		add("wither", new VesselLoot(
				1,
				LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.WITHER)).build(),
				LootItemKilledByPlayerCondition.killedByPlayer().build(),
				LootConditions.WITHER_DROPS_VESSEL
		));
	}
}
