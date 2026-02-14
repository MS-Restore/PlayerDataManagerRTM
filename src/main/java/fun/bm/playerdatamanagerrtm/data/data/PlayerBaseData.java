package fun.bm.playerdatamanagerrtm.data.data;

import java.util.UUID;

public class PlayerBaseData {
    public String name;
    public UUID uuid;
    public String lastUsedIp = "unknown";

    public PlayerBaseData copy() {
        PlayerBaseData copy = new PlayerBaseData();
        copy.name = name;
        copy.uuid = uuid;
        copy.lastUsedIp = lastUsedIp;
        return copy;
    }
}
