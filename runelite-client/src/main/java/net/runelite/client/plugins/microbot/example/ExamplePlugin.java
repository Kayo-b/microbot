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
import net.runelite.client.plugins.microbot.pluginscheduler.api.SchedulerCoordinates;
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
public class ExamplePlugin extends Plugin implements SchedulablePlugin, SchedulerCoordinates {
    @Inject
    private ExampleConfig config;
    
    private boolean hasSchedulerCoordinates = false;
    private int schedulerX, schedulerY, schedulerZ;
    private boolean scriptStarted = false; 
    @Provides
    ExampleConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ExampleConfig.class);
    }

    // @Inject
    // private OverlayManager overlayManager;
    // @Inject
    // private ExampleOverlay exampleOverlay;

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

    @Override
    protected void startUp() throws AWTException {
        // if (overlayManager != null) {
        //     overlayManager.add(exampleOverlay);
        //     exampleOverlay.myButton.hookMouseListener();
        // }
        
        // Use a delay to allow scheduler to set coordinates if this is a scheduled start
        // For manual activation, coordinates won't be set, so script starts immediately
        Microbot.getClientThread().invokeLater(() -> {
            // Give scheduler 1 game tick to set coordinates if this is a scheduled start
            if (!scriptStarted && !hasSchedulerCoordinates) {
                startScript();
            }
        });
    }

    protected void shutDown() {
        exampleScript.shutdown();
        clearSchedulerCoordinates(); // Clear coordinates on shutdown
        scriptStarted = false; // Reset script started flag
        // overlayManager.remove(exampleOverlay);
        // exampleOverlay.myButton.unhookMouseListener();
    }
    
    /**
     * Start the script - called either immediately (manual start) or after coordinates are set (scheduler start)
     */
    public synchronized void startScript() {
        if (!scriptStarted && !exampleScript.isRunning()) {
            Microbot.log("Starting ExampleScript with coordinates source: " + 
                (hasSchedulerCoordinates ? "scheduler" : "config"));
            exampleScript.run(this);
            scriptStarted = true;
        }
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

    @Override
    public void setSchedulerCoordinates(int x, int y, int z) {
        this.schedulerX = x;
        this.schedulerY = y;
        this.schedulerZ = z;
        this.hasSchedulerCoordinates = true;
        Microbot.log("Scheduler coordinates set: " + x + ", " + y + ", " + z);
        
        startScript();
    }
    
    @Override
    public boolean hasSchedulerCoordinates() {
        return hasSchedulerCoordinates;
    }
    
    @Override
    public void clearSchedulerCoordinates() {
        hasSchedulerCoordinates = false;
        schedulerX = schedulerY = schedulerZ = 0;
        // Don't reset scriptStarted here as the script might still be running with config coordinates
    }
    
    @Override
    public int getEffectiveX() {
        return hasSchedulerCoordinates ? schedulerX : config.walkX();
    }
    
    @Override
    public int getEffectiveY() {
        return hasSchedulerCoordinates ? schedulerY : config.walkY();
    }
    
    @Override
    public int getEffectiveZ() {
        return hasSchedulerCoordinates ? schedulerZ : config.walkZ();
    }

}
