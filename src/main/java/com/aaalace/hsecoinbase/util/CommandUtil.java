package com.aaalace.hsecoinbase.util;

import com.aaalace.hsecoinbase.domain.enums.Command;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommandUtil {
    private static final String OPTION_1 = "1. Bank account";
    private static final String OPTION_2 = "2. Category";
    private static final String OPTION_3 = "3. Operation";
    private static final String OPTION_4 = "4. Exit";

    private static final String OPTION_GET = "5. GET by id";
    private static final String OPTION_ADD = "6. ADD";
    private static final String OPTION_UPD = "7. UPDATE";
    private static final String OPTION_DEL = "8. DELETE by id";

    private static final String OPTION_AMOUNT = "9. Count operations amount in last hour";
    private static final String OPTION_PNL = "10. Find last hour operations PnL";

    private static final Map<String, Command> commandMap = new HashMap<>();

    static {
        CommandIterator iterator = new CommandIterator();

        while (iterator.hasNext()) {
            Command command = iterator.next();
            commandMap.put(command.id, command);
        }
    }

    public static Command getCommandById(String id) throws Exception {
        final Command command = commandMap.get(id);
        if (command == null) {
            throw new Exception("Incorrect command");
        }
        return command;
    }

    public static void outCommands() {
        System.out.println("[ Choose command ]");

        System.out.println(OPTION_1);
        System.out.println(OPTION_2);
        System.out.println(OPTION_3);
        System.out.println(OPTION_4);
        System.out.println(OPTION_AMOUNT);
        System.out.println(OPTION_PNL);

        System.out.print(">>> ");
    }

    public static void outCrudOps() {
        System.out.println("[ Choose CRUD operation ]");

        System.out.println(OPTION_GET);
        System.out.println(OPTION_ADD);
        System.out.println(OPTION_UPD);
        System.out.println(OPTION_DEL);

        System.out.print(">>> ");
    }
}

class CommandIterator implements Iterator<Command> {
    private int pos = 0;

    @Override
    public boolean hasNext() {
        return pos < Command.values().length;
    }

    @Override
    public Command next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more elements");
        }
        return Command.values()[pos++];
    }
}