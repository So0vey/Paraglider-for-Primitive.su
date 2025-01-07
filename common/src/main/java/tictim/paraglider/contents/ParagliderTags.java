package tictim.paraglider.contents;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import static net.minecraft.core.registries.Registries.ITEM;
import static tictim.paraglider.api.ParagliderAPI.id;

public interface ParagliderTags{
	TagKey<Item> PARAGLIDERS = TagKey.create(ITEM, id("paragliders"));
	TagKey<Item> STATUES_GODDESS = TagKey.create(ITEM, id("statues/goddess"));

}
