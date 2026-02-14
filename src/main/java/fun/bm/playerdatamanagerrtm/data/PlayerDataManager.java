package fun.bm.playerdatamanagerrtm.data;

import com.google.gson.stream.JsonReader;
import com.mojang.logging.LogUtils;
import fun.bm.playerdatamanagerrtm.data.data.PlayerBaseData;
import fun.bm.playerdatamanagerrtm.data.data.PlayerData;
import fun.bm.playerdatamanagerrtm.util.GsonUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerDataManager {
    public static final File playerDataFile = new File("playerData.json");
    public static final Set<PlayerData> allPlayerData = new HashSet<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void loadPlayerData() {
        if (!playerDataFile.exists()) {
            try {
                if (!playerDataFile.createNewFile()) {
                    LOGGER.warn("Failed to create {}", playerDataFile);
                }
            } catch (IOException e) {
                LOGGER.warn("Failed to create {}:", playerDataFile, e);
                throw new RuntimeException(e);
            }
        }
        // load player data from file

        try (JsonReader jsonReader = new JsonReader(new FileReader(playerDataFile))) {
            com.google.gson.JsonArray dataArray = GsonUtil.createGson().fromJson(jsonReader, com.google.gson.JsonArray.class);

            if (dataArray != null) {
                for (int i = 0; i < dataArray.size(); i++) {
                    com.google.gson.JsonElement dataElement = dataArray.get(i);
                    PlayerData data = GsonUtil.createGson().fromJson(dataElement, PlayerData.class);

                    allPlayerData.add(data);
                }
            }

            save();

        } catch (Exception e) {
            LOGGER.warn("Failed to read data file", e);
            throw new RuntimeException(e);
        }
    }

    public static void save() {
        try {
            FileWriter fileWriter = new FileWriter(playerDataFile);
            fileWriter.write(GsonUtil.createGson().toJson(allPlayerData));
            fileWriter.close();
        } catch (Exception e) {
            LOGGER.warn("Failed to save data file", e);
        }
    }

    public static PlayerData getPlayerData(String playerName) {
        for (PlayerData data : allPlayerData) {
            if (data.baseData.name.equals(playerName)) {
                return data;
            }
        }
        return null;
    }

    public static PlayerData getPlayerData(UUID playerUuid) {
        for (PlayerData data : allPlayerData) {
            if (data.baseData.uuid.equals(playerUuid)) {
                return data;
            }
        }
        return null;
    }

    public static PlayerData getPlayerData(ServerPlayerEntity player) {
        for (PlayerData data : allPlayerData) {
            if (data.baseData.uuid.equals(player.getUuid())) {
                return data;
            }
        }
        PlayerData data = generateNewPlayerData(player);
        allPlayerData.add(data);
        return data;
    }

    public static PlayerData generateNewPlayerData(ServerPlayerEntity player) {
        PlayerData data = new PlayerData();
        PlayerBaseData baseData = new PlayerBaseData();
        baseData.name = player.getName().getString();
        baseData.uuid = player.getUuid();
        data.baseData = baseData;
        return data;
    }
}
