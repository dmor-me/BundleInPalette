package com.bundleinpalette;

import com.bundleinpalette.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BundleInPalette.MOD_ID)
public class BundleInPalette {
    public static final String MOD_ID = "bundleinpalette";
    private static final Logger LOGGER = LogUtils.getLogger();

    public BundleInPalette() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreativeTabItems);
        MinecraftForge.EVENT_BUS.register(this);
        logDependencyState();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // currently no-op but kept for future network/setup hooks
    }

    private void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            ModItems.getDyedBundles().forEach(bundle -> event.accept(bundle.get()));
        }
    }

    private void logDependencyState() {
        boolean hasBundleInCreative = ModList.get().isLoaded("bundleincreative");
        if (!hasBundleInCreative) {
            LOGGER.warn("Bundle In Creative is not present. Dyed bundles require the vanilla bundle to be obtainable.");
        } else {
            LOGGER.info("Bundle In Creative detected, enabling dyed bundle support.");
        }
    }
}
