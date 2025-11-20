package com.bundleinpalette.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ModClientConfig {
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ForgeConfigSpec.BooleanValue DYED_BUNDLE_TOOLTIP_COLORS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("client");
        DYED_BUNDLE_TOOLTIP_COLORS = builder
                .comment("If true, dyed bundles tint their tooltip background to match their color.")
                .define("dyedBundleTooltipColors", false);
        builder.pop();

        CLIENT_SPEC = builder.build();
    }

    private ModClientConfig() {
    }
}
