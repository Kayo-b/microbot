package net.runelite.client.plugins.microbot.example;

import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.Script;
import net.runelite.client.plugins.microbot.util.walker.Rs2Walker;

import java.util.concurrent.TimeUnit;

public class ExampleScript extends Script {
    public static double version = 1.0;
    private boolean hasWalkedToConfig = false;
    public static boolean test = false;
    
    private ExamplePlugin plugin; 

    public boolean run(ExamplePlugin plugin) { 
        this.plugin = plugin;
        Microbot.enableAutoRunOn = false;

        hasWalkedToConfig = false;
        mainScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                if (!Microbot.isLoggedIn()) return;
                if (!super.run()) return;
                long startTime = System.currentTimeMillis();
                Microbot.log("has walked to config: " + hasWalkedToConfig);
                // CODE HERE
                if (!hasWalkedToConfig) {
                    walkToConfigLocation();
                    hasWalkedToConfig = true;
                }
                Microbot.log("Hello World from ExampleScript!");
                
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println("Total time for loop " + totalTime);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
        return true;
    }
    
    private void walkToConfigLocation() {
        // Use plugin effective coordinates (scheduler overrides config)
        WorldPoint destination = new WorldPoint(
            plugin.getEffectiveX(), 
            plugin.getEffectiveY(), 
            plugin.getEffectiveZ()
        );
        
        String source = plugin.hasSchedulerCoordinates() ? "scheduler" : "config";
        Microbot.log("Using " + source + " coordinates: " + destination.getX() + ", " + 
                     destination.getY() + ", " + destination.getPlane());
        
        if (destination.getX() != 0 || destination.getY() != 0 || destination.getPlane() != 0) {
            Rs2Walker.walkTo(destination);
            Microbot.log("Walking to " + source + " location: " + destination);
        }
    }
    @Override
    public void shutdown() {
        super.shutdown();
        hasWalkedToConfig = false;
        Rs2Walker.setTarget(null);
        Microbot.log("ExampleScript shutdown");
    }
}