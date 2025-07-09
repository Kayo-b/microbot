package net.runelite.client.plugins.microbot.smelting;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.pluginscheduler.api.SchedulablePlugin;
import net.runelite.client.plugins.microbot.pluginscheduler.event.PluginScheduleEntrySoftStopEvent;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;

@PluginDescriptor(
        name = PluginDescriptor.Vince + "Auto Smelting",
        description = "Smelt ores/coal into bars",
        tags = {"smithing", "smelting", "microbot", "skilling"},
        enabledByDefault = false
)
@Slf4j
public class AutoSmeltingPlugin extends Plugin implements SchedulablePlugin {
    @Inject
    private AutoSmeltingConfig config;
    @Provides
    AutoSmeltingConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(AutoSmeltingConfig.class);
    }

    @Inject
    private OverlayManager overlayManager;
    @Inject
    private AutoSmeltingOverlay autoSmeltingOverlay;

    @Inject
	AutoSmeltingScript autoSmeltingScript;

    @Subscribe
    @Override
    public void onPluginScheduleEntrySoftStopEvent(PluginScheduleEntrySoftStopEvent event) {
        if (event.getPlugin() == this) {
            // Cleanup operations
            Microbot.getClientThread().invokeLater(() -> {
                Microbot.stopPlugin(this);
            });
        }
    }

    @Override
    protected void startUp() throws AWTException {
        if (overlayManager != null) {
            overlayManager.add(autoSmeltingOverlay);
        }
        autoSmeltingScript.run(config);
    }

    protected void shutDown() {
        autoSmeltingScript.shutdown();
        overlayManager.remove(autoSmeltingOverlay);
    }
}
