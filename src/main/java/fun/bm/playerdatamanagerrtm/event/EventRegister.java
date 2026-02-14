package fun.bm.playerdatamanagerrtm.event;

public class EventRegister {
    public static void register() {
        PlayerJoinEvent.register();
        PlayerLeaveEvent.register();
    }
}
