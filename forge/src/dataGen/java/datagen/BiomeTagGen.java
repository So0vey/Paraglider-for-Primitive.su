package datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import tictim.paraglider.api.ParagliderAPI;
import tictim.paraglider.contents.ParagliderTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class BiomeTagGen extends BiomeTagsProvider{
	public BiomeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper){
		super(output, lookupProvider, ParagliderAPI.MODID, existingFileHelper);
	}
}

