package net.runelite.client.plugins.microbot.pluginscheduler.api;

/**
 * Interface for plugins that can receive coordinate overrides from the scheduler
 */
public interface SchedulerCoordinates {
    /**
     * Set coordinates from the scheduler that override plugin config values
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate (plane)
     */
    void setSchedulerCoordinates(int x, int y, int z);
    
    /**
     * Check if scheduler coordinates have been set
     * @return true if scheduler coordinates are available
     */
    boolean hasSchedulerCoordinates();
    
    /**
     * Clear scheduler coordinate overrides
     */
    default void clearSchedulerCoordinates() {
        // Default implementation - plugins can override if needed
    }
    
    /**
     * Get the effective X coordinate (scheduler override or config)
     * @return X coordinate to use
     */
    int getEffectiveX();
    
    /**
     * Get the effective Y coordinate (scheduler override or config)
     * @return Y coordinate to use
     */
    int getEffectiveY();
    
    /**
     * Get the effective Z coordinate (scheduler override or config)
     * @return Z coordinate to use
     */
    int getEffectiveZ();
}
