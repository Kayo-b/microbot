package net.runelite.client.plugins.microbot.example;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.pluginscheduler.api.SchedulablePlugin;
import net.runelite.client.plugins.microbot.pluginscheduler.event.PluginScheduleEntrySoftStopEvent;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.microbot.util.walker.Rs2Walker;

import javax.inject.Inject;
import java.awt.*;

@PluginDescriptor(
        name = PluginDescriptor.Default + "Example - Edit",
        description = "Microbot example plugin",
        tags = {"example", "microbot"},
        enabledByDefault = false
)
@Slf4j
public class ExamplePlugin extends Plugin implements SchedulablePlugin {
    @Inject
    private ExampleConfig config;
    @Provides
    ExampleConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ExampleConfig.class);
    }

    @Inject
    private OverlayManager overlayManager;
    @Inject
    private ExampleOverlay exampleOverlay;

    @Inject
    ExampleScript exampleScript;

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

    private void startWalkingToConfigLocation() {
        WorldPoint destination = new WorldPoint(config.walkX(), config.walkY(), config.walkZ());
        
        if (destination.getX() != 0 || destination.getY() != 0 || destination.getPlane() != 0) {
            Rs2Walker.walkWithState(destination);
            Microbot.log("Walking to configured location: " + destination);
        } else {
            Microbot.log("No coordinates configured, staying at current location");
        }
    }

    @Override
    protected void startUp() throws AWTException {
        if (overlayManager != null) {
            overlayManager.add(exampleOverlay);
            exampleOverlay.myButton.hookMouseListener();
        }
        startWalkingToConfigLocation();
        exampleScript.run(config);
    }

    protected void shutDown() {
        exampleScript.shutdown();
        overlayManager.remove(exampleOverlay);
        exampleOverlay.myButton.unhookMouseListener();
    }
    int ticks = 10;
    @Subscribe
    public void onGameTick(GameTick tick)
    {
        //System.out.println(getName().chars().mapToObj(i -> (char)(i + 3)).map(String::valueOf).collect(Collectors.joining()));

        if (ticks > 0) {
            ticks--;
        } else {
            ticks = 10;
        }

    }

}
