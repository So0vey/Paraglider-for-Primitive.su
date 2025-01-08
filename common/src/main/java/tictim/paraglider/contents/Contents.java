package tictim.paraglider.contents;

import org.jetbrains.annotations.NotNull;
import tictim.paraglider.ParagliderMod;
import tictim.paraglider.contents.item.ParagliderItem;
import tictim.paraglider.contents.recipe.CosmeticRecipe;

public interface Contents{
	@NotNull static Contents get(){
		return ParagliderMod.instance().getContents();
	}

	// items

	@NotNull ParagliderItem paraglider();
	@NotNull ParagliderItem dekuLeaf();
	@NotNull ParagliderItem coloredGliderOne();

	// recipes

	@NotNull CosmeticRecipe.Serializer cosmeticRecipeSerializer();
}
