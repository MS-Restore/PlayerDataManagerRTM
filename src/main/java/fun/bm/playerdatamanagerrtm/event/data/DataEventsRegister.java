package fun.bm.playerdatamanagerrtm.event.data;

import fun.bm.playerdatamanagerrtm.event.data.events.PlayerJoinEvent;
import fun.bm.playerdatamanagerrtm.event.data.events.PlayerLeaveEvent;

public class DataEventsRegister {
    public static void register() {
        PlayerJoinEvent.register();
        PlayerLeaveEvent.register();
    }
}
