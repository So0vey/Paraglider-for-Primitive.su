package tictim.paraglider.contents;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import tictim.paraglider.ParagliderMod;
import tictim.paraglider.api.bargain.Bargain;
import tictim.paraglider.contents.item.ParagliderItem;
import tictim.paraglider.contents.recipe.CosmeticRecipe;

public interface Contents{
	@NotNull static Contents get(){
		return ParagliderMod.instance().getContents();
	}

	// items

	@NotNull ParagliderItem paraglider();
	@NotNull ParagliderItem dekuLeaf();

	// recipes

	@NotNull CosmeticRecipe.Serializer cosmeticRecipeSerializer();
	@NotNull RecipeSerializer<? extends Bargain> bargainRecipeSerializer();

	@NotNull RecipeType<Bargain> bargainRecipeType();
}
