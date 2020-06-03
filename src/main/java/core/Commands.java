package core;

import commands.CommandInt;
import commands.CommandSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Commands implements CommandInt {
    private static final Logger logger = LogManager.getLogger(Commands.class.getName());
    protected List<String> rowCommandsPool;
    protected List<String> rowKeyList;
    protected Map<String, String> paramsPool;
    protected Map<String, String> keyPool;
    protected CommandSet cmd;

    protected Connection connection;

    public Commands() {
    }

    public Commands(String rowCommand) {
        rowCommandsPool = new ArrayList<>(Arrays.asList(rowCommand.split(" ")));
        rowKeyList = new ArrayList<>();
        paramsPool = new HashMap<>();
        keyPool = new HashMap<>();
        rowCommandsPool = rowCommandsPool.stream()
                .skip(1)
                .filter(x -> x != null && !x.isEmpty())
                .map(String::trim)
                .filter(x -> {
                    if (x.startsWith("-")) {
                        rowKeyList.add(x);
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    public static void showAvailableAliasList() {
        System.out.print("Available aliases: ");
        ConnectionManager.getManager().getConnectionList().forEach((key, value) -> System.out.print(key + " | "));
        System.out.println("");
    }

    public static void printHelp() {
        CommandSet[] commands = CommandSet.values();
        System.out.println("Available commands: ");
        Arrays.stream(commands).forEach(x -> System.out.println(x.getCommandText() + x.getCommandDescription()));
    }

    protected String obtain(int element) {
        try {
            return rowCommandsPool.get(element);
        } catch (IndexOutOfBoundsException e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    protected abstract boolean notEmpty();

    protected void printThisCommandHelp() {
        System.out.println(cmd.getCommandText() + cmd.getCommandDescription());
    }

}
