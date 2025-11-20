package com.bundleinpalette.registry;

import com.bundleinpalette.BundleInPalette;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public final class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BundleInPalette.MOD_ID);
    private static final EnumMap<DyeColor, RegistryObject<Item>> DYED_BUNDLES = new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) {
            DYED_BUNDLES.put(color, registerBundle(color));
        }
    }

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    private static RegistryObject<Item> registerBundle(DyeColor color) {
        String colorName = color.getName();
        return ITEMS.register(colorName + "_bundle", () -> new BundleItem(new Item.Properties().stacksTo(1)));
    }

    public static Collection<RegistryObject<Item>> getDyedBundles() {
        return DYED_BUNDLES.values();
    }

    public static RegistryObject<Item> getBundle(DyeColor color) {
        return DYED_BUNDLES.get(color);
    }

    public static DyeColor getColorForItem(Item item) {
        for (Map.Entry<DyeColor, RegistryObject<Item>> entry : DYED_BUNDLES.entrySet()) {
            if (entry.getValue().get() == item) {
                return entry.getKey();
            }
        }
        return null;
    }
}
