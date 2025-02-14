package net.mexicanminion.bountyhunt;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = BountyHuntMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    static private int fileVer = 1;

    private static final ForgeConfigSpec.ConfigValue<String> ITEM_INGOT = BUILDER
            .comment("The item for the ingot type")
            .define("itemIngot", "minecraft:diamond", Config::validateItemName);

    private static final ForgeConfigSpec.IntValue INGOT_TO_BLOCK_AMOUNT = BUILDER
            .comment("The amount of ingots it takes to make a block")
            .defineInRange("ingotToBlockAmount", 9, 0, 576);

    private static final ForgeConfigSpec.IntValue ANNOUNCE_AMOUNT = BUILDER
            .comment("The amount of ingots it takes to announce a bounty to the whole server")
            .defineInRange("announceAmount", 9, 0, 576);

    private static final ForgeConfigSpec.BooleanValue ONLY_INGOT = BUILDER
            .comment("If the currency is only ingots")
            .define("onlyIngot", false);

    private static final ForgeConfigSpec.ConfigValue<String> ITEM_BLOCK = BUILDER
            .comment("The item for the ingot type")
            .define("itemBlock", "minecraft:diamond_block", Config::validateItemName);

    private static final ForgeConfigSpec.IntValue BH_CONFIG_VERSION = BUILDER
            .comment("The amount of ingots it takes to make a block")
            .defineInRange("BHVersion", fileVer, 0, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Item itemIngot;
    public static int ingotToBlockAmount;
    public static int announceAmount;
    public static boolean onlyIngot;
    public static Item itemBlock;
    public static int BHVersion;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(ResourceLocation.tryParse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        itemIngot = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(ITEM_INGOT.get()));
        ingotToBlockAmount = INGOT_TO_BLOCK_AMOUNT.get();
        announceAmount = ANNOUNCE_AMOUNT.get();
        onlyIngot = ONLY_INGOT.get();
        itemBlock = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(ITEM_BLOCK.get()));
        BHVersion = BH_CONFIG_VERSION.get();
    }
}
