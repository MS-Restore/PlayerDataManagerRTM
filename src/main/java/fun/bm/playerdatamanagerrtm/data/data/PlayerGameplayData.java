package fun.bm.playerdatamanagerrtm.data.data;

public class PlayerJoinData {
    public long firstJoin = 0;
    public long lastJoin = 0;
    public long totalJoins = 0;

    public PlayerJoinData copy() {
        PlayerJoinData copy = new PlayerJoinData();
        copy.firstJoin = firstJoin;
        copy.lastJoin = lastJoin;
        copy.totalJoins = totalJoins;
        return copy;
    }
}
