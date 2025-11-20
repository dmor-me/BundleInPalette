package com.bundleinpalette.client;

import com.bundleinpalette.config.ModClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class BundleInPaletteConfigScreen extends Screen {
    private final Screen parent;
    private CycleButton<Boolean> tooltipColorToggle;

    public BundleInPaletteConfigScreen(Screen parent) {
        super(Component.literal("Bundle In Palette - Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 3;

        tooltipColorToggle = CycleButton.onOffBuilder(ModClientConfig.DYED_BUNDLE_TOOLTIP_COLORS.get())
                .withTooltip(value -> Tooltip.create(Component.literal("Tint dyed bundle tooltips to match their color")))
                .create(centerX - 170, y, 340, 20,
                        Component.literal("Dyed bundle tooltip colors"),
                        (button, value) -> ModClientConfig.DYED_BUNDLE_TOOLTIP_COLORS.set(value));
        addRenderableWidget(tooltipColorToggle);

        addRenderableWidget(Button.builder(Component.literal("Done"), btn -> {
            saveAndClose();
        }).bounds(centerX - 60, y + 40, 120, 20).build());
    }

    private void saveAndClose() {
        ModClientConfig.CLIENT_SPEC.save();
        Minecraft.getInstance().setScreen(parent);
    }

    @Override
    public void onClose() {
        saveAndClose();
    }

    @Override
    public void render(net.minecraft.client.gui.GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, this.height / 5, 0xFFFFFF);
    }
}
