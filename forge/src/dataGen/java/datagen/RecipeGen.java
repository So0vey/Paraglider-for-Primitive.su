package datagen;

import datagen.builder.CosmeticRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import tictim.paraglider.api.ParagliderAPI;
import tictim.paraglider.contents.Contents;
import tictim.paraglider.contents.ParagliderTags;
import tictim.paraglider.forge.contents.ConfigConditionSerializer;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider{
	public RecipeGen(@NotNull PackOutput output){
		super(output);
	}

	@Override protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer){
		Contents contents = Contents.get();

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, contents.paraglider())
				.pattern("121")
				.pattern("212")
				.pattern("1 1")
				.define('1', Tags.Items.RODS_WOODEN)
				.define('2', Tags.Items.LEATHER)
				.unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
				.save(consumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, contents.coloredGliderOne())
				.pattern("222")
				.pattern("212")
				.pattern("1 1")
				.define('1', Tags.Items.RODS_WOODEN)
				.define('2', Tags.Items.LEATHER)
				.unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
				.save(consumer);

		new CosmeticRecipeBuilder(contents.dekuLeaf(), Ingredient.of(ParagliderTags.PARAGLIDERS), Ingredient.of(ItemTags.LEAVES))
				.addCriterion("has_paragliders", has(ParagliderTags.PARAGLIDERS))
				.build(consumer, ParagliderAPI.id("cosmetic/deku_leaf"));

		new CosmeticRecipeBuilder(contents.paraglider(), Ingredient.of(ParagliderTags.PARAGLIDERS), Ingredient.of(Tags.Items.RODS_WOODEN))
				.addCriterion("has_paragliders", has(ParagliderTags.PARAGLIDERS))
				.build(consumer, ParagliderAPI.id("cosmetic/paraglider"));

//		new CosmeticRecipeBuilder(contents.coloredGliderOne(), Ingredient.of(ParagliderTags.PARAGLIDERS), Ingredient.of(Tags.Items.RODS_WOODEN))
//				.addCriterion("has_paragliders", has(ParagliderTags.PARAGLIDERS))
//				.build(consumer, ParagliderAPI.id("cosmetic/coloredGliderOne"));
	}
}
