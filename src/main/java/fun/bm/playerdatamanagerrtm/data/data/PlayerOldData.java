package fun.bm.playerdatamanagerrtm.data.data;

import java.util.HashSet;
import java.util.Set;

public class PlayerOldData {
    public Set<String> usedNames = new HashSet<>();
    public Set<String> usedIps = new HashSet<>();

    public PlayerOldData copy() {
        PlayerOldData copy = new PlayerOldData();
        copy.usedNames = new HashSet<>(usedNames);
        copy.usedIps = new HashSet<>(usedIps);
        return copy;
    }
}
