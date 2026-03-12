package fun.bm.playerdatamanagerrtm.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.util.UUIDTypeAdapter;
import fun.bm.playerdatamanagerrtm.data.player.DataQueueOutput;
import fun.bm.playerdatamanagerrtm.data.player.PlayerDataManager;
import fun.bm.playerdatamanagerrtm.data.player.data.PlayerData;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.UUID;

public class DataQueryCommand extends AbstractCommand {
    protected DataQueryCommand() {
        super("rtm-query");
    }

    @Override
    public void register() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal(this.name)
                            .requires(source -> source.hasPermissionLevel(4))
                            .then(
                                    CommandManager.argument("onlinePlayer", EntityArgumentType.player())
                                            .executes(ctx -> {
                                                ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "onlinePlayer");
                                                PlayerData data = PlayerDataManager.getPlayerDataOrNull(player);
                                                executeDataProcess(ctx, data);
                                                return 1;
                                            })
                            )
                            .then(
                                    CommandManager.argument("id", StringArgumentType.string())
                                            .executes(ctx -> {
                                                String nameOrUuid = StringArgumentType.getString(ctx, "id");
                                                UUID uuid = null;
                                                try {
                                                    uuid = UUIDTypeAdapter.fromString(nameOrUuid);
                                                } catch (Exception ignored) {
                                                }
                                                PlayerData data;
                                                if (uuid == null) {
                                                    data = PlayerDataManager.getPlayerDataOrNull(nameOrUuid);
                                                } else {
                                                    data = PlayerDataManager.getPlayerDataOrNull(uuid);
                                                }
                                                executeDataProcess(ctx, data);
                                                return 1;
                                            })
                            )
            );
        }));
    }

    private void executeDataProcess(CommandContext<ServerCommandSource> ctx, PlayerData data) {
        ctx.getSource().sendMessage(Text.literal(DataQueueOutput.exportPlayerData(data)));
    }
}
