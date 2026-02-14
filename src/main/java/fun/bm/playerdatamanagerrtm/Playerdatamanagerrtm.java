package fun.bm.playerdatamanagerrtm;

import fun.bm.playerdatamanagerrtm.data.PlayerDataManager;
import fun.bm.playerdatamanagerrtm.event.EventRegister;
import net.fabricmc.api.ModInitializer;

import java.io.File;

public class Playerdatamanagerrtm implements ModInitializer {
    public final static File BASE_DIR = new File("rtm/playerdata");

    @Override
    public void onInitialize() {
        PlayerDataManager.loadPlayerData();
        EventRegister.register();
    }
}
