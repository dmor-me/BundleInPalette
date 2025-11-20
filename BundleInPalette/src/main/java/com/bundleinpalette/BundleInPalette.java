package com.bundleinpalette;

import com.bundleinpalette.registry.ModItems;
import com.bundleinpalette.config.ModClientConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.client.ConfigScreenHandler;
import com.bundleinpalette.client.BundleInPaletteConfigScreen;

@Mod(BundleInPalette.MOD_ID)
public class BundleInPalette {
    public static final String MOD_ID = "bundleinpalette";
    private static final Logger LOGGER = LogUtils.getLogger();

    public BundleInPalette() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreativeTabItems);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientConfig.CLIENT_SPEC);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (minecraft, parent) -> new BundleInPaletteConfigScreen(parent)));
        }
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

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientEvents {
        @SubscribeEvent
        public static void tintBundleTooltip(RenderTooltipEvent.Color event) {
            if (!ModClientConfig.DYED_BUNDLE_TOOLTIP_COLORS.get()) {
                return;
            }

            ItemStack stack = event.getItemStack();
            Item item = stack.getItem();
            if (!(item instanceof BundleItem)) {
                return;
            }

            var dyeColor = ModItems.getColorForItem(item);
            if (dyeColor == null) {
                return;
            }

            int rgb = dyeColor.getTextColor() & 0xFFFFFF;
            int backgroundStart = 0xE0000000 | rgb;
            int backgroundEnd = 0xF0000000 | darken(rgb, 0.35f);
            int borderStart = 0xF0000000 | darken(rgb, 0.55f);
            int borderEnd = 0xF0000000 | darken(rgb, 0.75f);

            event.setBackgroundStart(backgroundStart);
            event.setBackgroundEnd(backgroundEnd);
            event.setBorderStart(borderStart);
            event.setBorderEnd(borderEnd);
        }

        private static int darken(int rgb, float factor) {
            int r = (int) (((rgb >> 16) & 0xFF) * (1.0f - factor));
            int g = (int) (((rgb >> 8) & 0xFF) * (1.0f - factor));
            int b = (int) (((rgb) & 0xFF) * (1.0f - factor));
            return (r << 16) | (g << 8) | b;
        }
    }
}
