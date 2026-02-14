package fun.bm.playerdatamanagerrtm;

import fun.bm.playerdatamanagerrtm.data.PlayerDataManager;
import fun.bm.playerdatamanagerrtm.event.EventRegister;
import net.fabricmc.api.ModInitializer;

public class Playerdatamanagerrtm implements ModInitializer {

    @Override
    public void onInitialize() {
        PlayerDataManager.loadPlayerData();
        EventRegister.register();
    }
}
