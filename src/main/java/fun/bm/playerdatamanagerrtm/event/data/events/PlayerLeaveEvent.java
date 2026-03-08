package fun.bm.playerdatamanagerrtm.event.data.events;

import fun.bm.playerdatamanagerrtm.data.PlayerDataManager;
import fun.bm.playerdatamanagerrtm.data.data.PlayerData;
import fun.bm.playerdatamanagerrtm.data.data.PlayerGameplayData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerLeaveEvent {
    public static void register() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            processData(handler);
        });
    }

    private static void processData(ServerPlayNetworkHandler handler) {
        ServerPlayerEntity player = handler.player;
        PlayerData data = PlayerDataManager.getPlayerData(player);
        PlayerGameplayData gameplayData = data.gamePlayData;
        gameplayData.lastLeave = System.currentTimeMillis();
        PlayerDataManager.saveDataToFile();
    }
}
