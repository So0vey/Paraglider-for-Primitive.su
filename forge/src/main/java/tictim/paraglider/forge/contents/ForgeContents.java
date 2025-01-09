package tictim.paraglider.forge.contents;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import tictim.paraglider.api.ParagliderAPI;
import tictim.paraglider.contents.Contents;
import tictim.paraglider.contents.item.ParagliderItem;
import tictim.paraglider.contents.recipe.CosmeticRecipe;
import tictim.paraglider.forge.contents.item.ForgeParagliderItem;
import tictim.paraglider.forge.contents.loot.LootConditions;
import tictim.paraglider.forge.contents.loot.ParagliderLoot;
import tictim.paraglider.forge.contents.loot.VesselLoot;

import static tictim.paraglider.api.ParagliderAPI.MODID;
import static tictim.paraglider.contents.CommonContents.*;

@SuppressWarnings("unused")
public final class ForgeContents implements Contents{
	private final DeferredRegister<Block> blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private final DeferredRegister<RecipeSerializer<?>> recipeSerializers = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
	private final DeferredRegister<RecipeType<?>> recipeTypes = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);
	private final DeferredRegister<Attribute> attributes = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);
	private final DeferredRegister<Codec<? extends IGlobalLootModifier>> loots = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);
	private final DeferredRegister<LootItemConditionType> lootConditions = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, MODID);
	private final DeferredRegister<CreativeModeTab> creativeTabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	private final RegistryObject<ParagliderItem> paraglider = items.register("paraglider", () -> new ForgeParagliderItem(PARAGLIDER_DEFAULT_COLOR));
	private final RegistryObject<ParagliderItem> dekuLeaf = items.register("deku_leaf", () -> new ForgeParagliderItem(DEKU_LEAF_DEFAULT_COLOR));
	private final RegistryObject<ParagliderItem> coloredGliderOne = items.register("colored_glider_one", () -> new ForgeParagliderItem(PARAGLIDER_COLORED_DEFAULT_COLOR));

	private final RegistryObject<CosmeticRecipe.Serializer> cosmeticRecipe = recipeSerializers.register("cosmetic", CosmeticRecipe.Serializer::new);

	private final RegistryObject<Codec<ParagliderLoot>> paragliderLoot = loots.register("paraglider", () -> ParagliderLoot.CODEC);
	private final RegistryObject<Codec<VesselLoot>> vesselLoot = loots.register("vessel", () -> VesselLoot.CODEC);

	public final RegistryObject<LootItemConditionType> witherDropsVesselConfigCondition = lootConditions.register("config_wither_drops_vessel",
			() -> new LootItemConditionType(LootConditions.WITHER_DROPS_VESSEL));


	private final RegistryObject<CreativeModeTab> tab = creativeTabs.register(MODID, () -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(paraglider.get()))
			.title(Component.translatable("itemGroup."+MODID))
			.displayItems((features, out) -> {
				out.accept(paraglider.get());
				out.accept(dekuLeaf.get());
				out.accept(coloredGliderOne.get());
			}).build());

	{
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		blocks.register(eventBus);
		items.register(eventBus);
		loots.register(eventBus);
		lootConditions.register(eventBus);
		recipeSerializers.register(eventBus);
		attributes.register(eventBus);
		recipeTypes.register(eventBus);
		creativeTabs.register(eventBus);
	}

	@NotNull public DeferredRegister<Block> blocks(){
		return blocks;
	}

	@Override @NotNull public ParagliderItem paraglider(){
		return paraglider.get();
	}
	@Override @NotNull public ParagliderItem dekuLeaf(){
		return dekuLeaf.get();
	}
	@Override public @NotNull ParagliderItem coloredGliderOne() {
		return coloredGliderOne.get();
	}
	@Override @NotNull public CosmeticRecipe.Serializer cosmeticRecipeSerializer(){
		return cosmeticRecipe.get();
	}
}
