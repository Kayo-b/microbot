package net.runelite.client.plugins.microbot.aiofighter.combat;

import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.Script;
import net.runelite.client.plugins.microbot.aiofighter.AIOFighterConfig;
import net.runelite.client.plugins.microbot.util.inventory.Rs2Inventory;
import net.runelite.client.plugins.microbot.util.inventory.Rs2ItemModel;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.eventbus.Subscribe;


import java.util.concurrent.TimeUnit;

public class EquipArrows extends Script {
    private AIOFighterConfig config;

    public boolean run(AIOFighterConfig config) {
        this.config = config;
        Microbot.getEventBus().register(this);
        return true;
    }

    @Override
    public void shutdown() {
        Microbot.getEventBus().unregister(this);
        super.shutdown();
    }

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        try {
            if (config == null || !config.toggleEquipArrows()) return;
            if (!Microbot.isLoggedIn()) return;
            if (chatMessage.getType() != ChatMessageType.GAMEMESSAGE) return;
            
            String msg = chatMessage.getMessage().toLowerCase();
            if (msg.contains("there is no ammo left in your quiver")) {
                if (Rs2Inventory.hasItem("arrow")) {
                    Rs2ItemModel arrowItem = Rs2Inventory.get("arrow");
                    if (arrowItem != null) {
                        Rs2Inventory.wield(arrowItem.getName());
                    }
                }
            }
        } catch (Exception ex) {
            Microbot.logStackTrace(this.getClass().getSimpleName(), ex);
        }
    }

}