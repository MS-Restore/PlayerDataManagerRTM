package fun.bm.playerdatamanagerrtm.data.data;

public class PlayerGameplayData {
    public long firstJoin = 0;
    public long lastJoin = 0;
    public long lastLeave = 0;
    public long totalJoins = 0;

    public PlayerGameplayData copy() {
        PlayerGameplayData copy = new PlayerGameplayData();
        copy.firstJoin = firstJoin;
        copy.lastJoin = lastJoin;
        copy.lastLeave = lastLeave;
        copy.totalJoins = totalJoins;
        return copy;
    }
}
