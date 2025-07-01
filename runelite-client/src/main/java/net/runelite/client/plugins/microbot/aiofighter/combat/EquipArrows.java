package net.runelite.client.plugins.microbot.aiofighter.combat;

import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.Script;
import net.runelite.client.plugins.microbot.aiofighter.AIOFighterConfig;
import net.runelite.client.plugins.microbot.util.equipment.Rs2Equipment;
import net.runelite.client.plugins.microbot.util.player.Rs2Player;
import net.runelite.client.plugins.microbot.util.inventory.Rs2Inventory;
import net.runelite.client.plugins.microbot.util.inventory.Rs2ItemModel;
import net.runelite.client.plugins.microbot.api.ChatMessageType;

import java.util.concurrent.TimeUnit;

public class EquipArrows extends Script {

    public boolean run(AIOFighterConfig config) {
        mainScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                if (!Microbot.isLoggedIn()) return;
                if (!super.run() || !config.toggleEquipArrow()) return;
                if (event.getType() == ChatMessageType.GAMEMESSAGE) {
                    String msg = event.getMessage().toLowerCase();
                    if (msg.contains("there is no ammo left in your quiver")) {
                        if (Rs2Inventory.hasItem("arrow")) {
                            Rs2ItemModel arrowItem = Rs2Inventory.get("arrow");
                            if (arrowItem != null) {
                                Rs2Inventory.wield(arrowItem.getName());
                            }
                        }
                    }
                }
                // if (Rs2Inventory.hasItem("Iron arrow")) {
                //     Rs2Inventory.useItem("Iron arrow");
                // } else if (Rs2Inventory.hasItem("Bronze arrow")) {
                //     Rs2Inventory.useItem("Bronze arrow");
                // }
           } catch (Exception ex) {
                Microbot.logStackTrace(this.getClass().getSimpleName(), ex);
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
        return true;
    }
}