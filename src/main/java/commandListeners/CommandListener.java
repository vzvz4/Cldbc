package commandListeners;

public interface CommandListener {
    boolean listenCommands(String command);
    String getName();
    boolean isOver();
    void close();
}
