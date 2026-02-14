package fun.bm.playerdatamanagerrtm.data.data;

public class PlayerData {
    public PlayerBaseData baseData = null;
    public PlayerOldData oldData = new PlayerOldData();
    public PlayerGameplayData gamePlayData = new PlayerGameplayData();

    public PlayerData copy() {
        PlayerData data = new PlayerData();
        data.baseData = baseData.copy();
        data.oldData = oldData.copy();
        data.gamePlayData = gamePlayData.copy();
        return data;
    }
}
