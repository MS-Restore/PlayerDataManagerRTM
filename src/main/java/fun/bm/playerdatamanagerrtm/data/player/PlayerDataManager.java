package fun.bm.playerdatamanagerrtm.data.player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.mojang.logging.LogUtils;
import fun.bm.playerdatamanagerrtm.data.player.data.PlayerBaseData;
import fun.bm.playerdatamanagerrtm.data.player.data.PlayerData;
import fun.bm.playerdatamanagerrtm.util.DirectoryAccessor;
import fun.bm.playerdatamanagerrtm.util.GsonUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static fun.bm.playerdatamanagerrtm.Playerdatamanagerrtm.BASE_DIR;

public class PlayerDataManager {
    public static final File playerDataFile = new File(BASE_DIR, "playerData.json");
    public static final Set<PlayerData> allPlayerData = new HashSet<>();
    private static final Gson GSON = GsonUtil.createPrettyGson();
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void loadPlayerData() {
        DirectoryAccessor.initFile(playerDataFile);
        // load player data from file
        try (JsonReader jsonReader = new JsonReader(new FileReader(playerDataFile))) {
            JsonArray dataArray = GSON.fromJson(jsonReader, JsonArray.class);

            if (dataArray != null) {
                for (int i = 0; i < dataArray.size(); i++) {
                    JsonElement dataElement = dataArray.get(i);
                    PlayerData data = GSON.fromJson(dataElement, PlayerData.class);

                    allPlayerData.add(data);
                }
            }
            saveDataToFile();
        } catch (Exception e) {
            LOGGER.warn("Failed to read data file", e);
            throw new RuntimeException(e);
        }
    }

    public static void saveDataToFile() {
        try {
            DirectoryAccessor.initFile(playerDataFile);
            FileWriter fileWriter = new FileWriter(playerDataFile);
            fileWriter.write(GSON.toJson(allPlayerData));
            fileWriter.close();
        } catch (Exception e) {
            LOGGER.warn("Failed to save data file", e);
        }
    }

    public static @Nullable PlayerData getPlayerDataOrNull(String playerName) {
        for (PlayerData data : allPlayerData) {
            if (data.baseData.name.equals(playerName)) {
                return data;
            }
        }
        return null;
    }

    public static @Nullable PlayerData getPlayerDataOrNull(UUID playerUuid) {
        for (PlayerData data : allPlayerData) {
            if (data.baseData.uuid.equals(playerUuid)) {
                return data;
            }
        }
        return null;
    }

    public static @Nullable PlayerData getPlayerDataOrNull(ServerPlayerEntity player) {
        return getPlayerDataOrNull(player.getUuid());
    }

    public static @NotNull PlayerData getPlayerData(ServerPlayerEntity player) {
        PlayerData data = getPlayerDataOrNull(player);
        if (data == null) {
            data = generateNewPlayerData(player);
            allPlayerData.add(data);
        }
        return data;
    }

    private static @NotNull PlayerData generateNewPlayerData(ServerPlayerEntity player) {
        PlayerData data = new PlayerData();
        PlayerBaseData baseData = new PlayerBaseData();
        baseData.name = player.getName().getString();
        baseData.uuid = player.getUuid();
        data.baseData = baseData;
        return data;
    }
}
