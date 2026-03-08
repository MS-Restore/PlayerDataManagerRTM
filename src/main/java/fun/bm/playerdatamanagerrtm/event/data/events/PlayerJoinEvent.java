package fun.bm.playerdatamanagerrtm.event.data.events;

import fun.bm.playerdatamanagerrtm.data.PlayerDataManager;
import fun.bm.playerdatamanagerrtm.data.data.PlayerBaseData;
import fun.bm.playerdatamanagerrtm.data.data.PlayerData;
import fun.bm.playerdatamanagerrtm.data.data.PlayerGameplayData;
import fun.bm.playerdatamanagerrtm.data.data.PlayerOldData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerJoinEvent {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> processData(handler));
    }

    private static void processData(ServerPlayNetworkHandler handler) {
        ServerPlayerEntity player = handler.player;
        PlayerData data = PlayerDataManager.getPlayerData(player);
        PlayerOldData oldData = data.oldData;
        PlayerBaseData baseData = data.baseData;

        // process old data
        if (!baseData.name.equals(player.getEntityName())) {
            oldData.usedNames.add(baseData.name);
        }
        if (!baseData.lastUsedIp.equals(player.getIp()) && !baseData.lastUsedIp.equals("unknown")) {
            oldData.usedIps.add(baseData.lastUsedIp);
        }

        // process base data
        baseData.name = player.getEntityName();
        baseData.lastUsedIp = player.getIp();

        // process gameplay data
        PlayerGameplayData gameplayData = data.gamePlayData;
        if (gameplayData.firstJoin == 0) {
            gameplayData.firstJoin = System.currentTimeMillis();
        }
        gameplayData.lastJoin = System.currentTimeMillis();
        gameplayData.totalJoins++;

        // save modified data
        PlayerDataManager.saveDataToFile();
    }
}
