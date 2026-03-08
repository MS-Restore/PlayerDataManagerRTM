package fun.bm.playerdatamanagerrtm.event.playerdata;

import fun.bm.playerdatamanagerrtm.event.playerdata.events.PlayerJoinEvent;
import fun.bm.playerdatamanagerrtm.event.playerdata.events.PlayerLeaveEvent;

public class DataEventsRegister {
    public static void register() {
        PlayerJoinEvent.register();
        PlayerLeaveEvent.register();
    }
}
