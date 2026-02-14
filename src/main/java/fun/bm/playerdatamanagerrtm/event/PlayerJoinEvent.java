package fun.bm.playerdatamanagerrtm.event;

import fun.bm.playerdatamanagerrtm.data.PlayerDataManager;
import fun.bm.playerdatamanagerrtm.data.data.PlayerBaseData;
import fun.bm.playerdatamanagerrtm.data.data.PlayerData;
import fun.bm.playerdatamanagerrtm.data.data.PlayerGameplayData;
import fun.bm.playerdatamanagerrtm.data.data.PlayerOldData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerJoinEvent {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            PlayerData data = PlayerDataManager.getPlayerData(player);

            {
                // process old data
                PlayerOldData oldData = data.oldData;
                PlayerBaseData baseData = data.baseData;
                if (!baseData.name.equals(player.getEntityName())) {
                    oldData.usedNames.add(baseData.name);
                }
                if (!baseData.lastUsedIp.equals(player.getIp()) && !baseData.lastUsedIp.equals("unknown")) {
                    oldData.usedIps.add(baseData.lastUsedIp);
                }
            }

            {
                // process base data
                PlayerBaseData baseData = data.baseData;
                baseData.name = player.getEntityName();
                baseData.lastUsedIp = player.getIp();
            }

            {
                // process gameplay data
                PlayerGameplayData gameplayData = data.gamePlayData;
                if (gameplayData.firstJoin == 0) {
                    gameplayData.firstJoin = System.currentTimeMillis();
                }

                gameplayData.lastJoin = System.currentTimeMillis();

                gameplayData.totalJoins++;
            }

            PlayerDataManager.save();
        });
    }
}
