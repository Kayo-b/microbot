package net.runelite.client.plugins.microbot.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface ExampleConfig extends Config {
@ConfigItem(
    keyName = "walkX",
    name = "Walk X Coordinate",
    description = "X coordinate to walk to",
    position = 10
)
default int walkX() {
    return 0;
}

@ConfigItem(
    keyName = "walkY", 
    name = "Walk Y Coordinate",
    description = "Y coordinate to walk to",
    position = 11
)
default int walkY() {
    return 0;
}

@ConfigItem(
    keyName = "walkZ",
    name = "Walk Z Coordinate", 
    description = "Z coordinate to walk to",
    position = 12
)
default int walkZ() {
    return 0;
}
}
