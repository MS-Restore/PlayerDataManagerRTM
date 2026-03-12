package fun.bm.playerdatamanagerrtm.command;

public abstract class AbstractCommand {
    protected final String name;

    protected AbstractCommand(String name) {
        this.name = name;
    }

    public abstract void register();
}
