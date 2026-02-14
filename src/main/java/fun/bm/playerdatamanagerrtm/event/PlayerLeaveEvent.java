package fun.bm.playerdatamanagerrtm.event;

import fun.bm.playerdatamanagerrtm.data.PlayerDataManager;
import fun.bm.playerdatamanagerrtm.data.data.PlayerData;
import fun.bm.playerdatamanagerrtm.data.data.PlayerGameplayData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerLeaveEvent {
    public static void register() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.player;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            PlayerGameplayData gameplayData = data.gamePlayData;
            gameplayData.lastLeave = System.currentTimeMillis();
        });
    }
}
