package fun.bm.playerdatamanagerrtm.data.player;

import fun.bm.playerdatamanagerrtm.data.player.data.PlayerBaseData;
import fun.bm.playerdatamanagerrtm.data.player.data.PlayerData;
import fun.bm.playerdatamanagerrtm.data.player.data.PlayerGameplayData;
import fun.bm.playerdatamanagerrtm.data.player.data.PlayerOldData;

public class DataQueueOutput {
    public static String exportPlayerData(PlayerData data, boolean baseData, boolean gameplayData, boolean oldData) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        if (baseData) {
            sb.append("baseData Part: \n")
                    .append(exportBaseData(data.baseData));
            first = false;
        }
        if (gameplayData) {
            if (!first) {
                sb.append("\n");
            }
            sb.append("gamePlayData Part: \n")
                    .append(exportGameplayData(data.gamePlayData));
            first = false;
        }
        if (oldData) {
            if (!first) {
                sb.append("\n");
            }
            sb.append("oldData Part: \n")
                    .append(exportOldData(data.oldData));
        }

        return sb.toString();
    }

    public static String exportPlayerData(PlayerData data) {
        return exportPlayerData(data, true, true, true);
    }

    public static String exportBaseData(PlayerBaseData data) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ")
                .append(data.name)
                .append("\n")
                .append("UUID: ")
                .append(data.uuid)
                .append("\n")
                .append("last known IP: ")
                .append(data.lastUsedIp); // TODO: IP Location API
        return sb.toString();
    }

    public static String exportOldData(PlayerOldData data) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        boolean bl1 = true;
        for (String name : data.usedNames) {
            if (bl1) {
                bl1 = false;
            } else {
                sb1.append(", ");
            }
            sb1.append(name);
        }
        StringBuilder sb2 = new StringBuilder();
        boolean bl2 = true;
        for (String ip : data.usedIps) {
            if (bl2) {
                bl2 = false;
            } else {
                sb2.append(", ");
            }
            sb2.append(ip); // TODO: IP Location API
        }
        sb.append("All known Names: ")
                .append(sb1)
                .append("\n")
                .append("Used IPs: ")
                .append(sb2);
        return sb.toString();
    }

    public static String exportGameplayData(PlayerGameplayData data) {
        StringBuilder sb = new StringBuilder();
        sb.append("First Join: ")
                .append(data.firstJoin) // TODO: Date Format
                .append("\n")
                .append("Last Join: ")
                .append(data.lastJoin) // TODO: Date Format
                .append("\n")
                .append("Last Leave: ")
                .append(data.lastLeave) // TODO: Date Format
                .append("\n")
                .append("Total Joins: ")
                .append(data.totalJoins);
        return sb.toString();
    }
}
