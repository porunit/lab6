package executionmanager;


import commandmanagement.Command;
import commandmanagement.CommandData;
import commandmanagement.CommandMapsBuilder;
import commandmanagement.IdValidator;
import commandmanagement.commands.RemoveByIdCommand;
import commandmanagement.commands.UpdateCommand;
import io.InputHandler;
import io.OutputHandler;
import io.network.ClientInputHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;


public class CommandProcessor {
    final static int COMMAND_NO_ARG_LENGTH = 1;
    final static int COMMAND_MAX_ARG_LENGTH = 2;

    private static final HashMap<String, Command> commandsHashMap;
    private static final HashMap<String, Command> commandsWithID;

    static {
        commandsWithID = new HashMap<>();
        commandsHashMap =CommandMapsBuilder.buildCommandMap();
        commandsWithID.put("update",new UpdateCommand());
        commandsWithID.put("remove", new RemoveByIdCommand());
    }

    /**
     * Parses the command input string and executes the corresponding command. The method first trims the input string and
     * splits it into an array of strings separated by whitespace. The first element of the array is checked if it is a
     * valid command. If not, an error message is printed. If it is a command without argument, and the array length is not
     * equal to the expected length, another error message is printed. If it is a command without argument and the length is
     * correct, the corresponding command is executed. If it is a command with argument, the array length is checked, and if
     * it is not equal to 2 or the second argument is null or empty, an error message is printed. Otherwise, the second
     * element of the array is retrieved as the argument and the corresponding command is executed with that argument.
     *
     * @param commandName a string representing the command input to parse
     */
    public static void parse(String commandName, OutputHandler outputHandler, ClientInputHandler inputHandler) throws IOException {
        if (commandName == null) return;
        String[] array = commandName.split("\\s+");
        var node = array[0];
        if (array.length > COMMAND_MAX_ARG_LENGTH) {
            throw new IllegalArgumentException("Too much arguments for command");
        }
        if (!isCommand(node)) {
            throw new IllegalArgumentException("Command doesn't exists");
        } else if (array.length == COMMAND_NO_ARG_LENGTH) {
            Command command = commandsHashMap.get(node);
            command.proxy(new CommandData(null, outputHandler));
        } else {
            String argument = array[1];
            if(commandsWithID.containsKey(node)){
                IdValidator validator = new IdValidator(inputHandler, outputHandler);
                if(!validator.validateId(argument)){
                    throw new IllegalArgumentException("id doesnt exists");
                }
            }
            Command command = commandsHashMap.get(node);
            command.proxy(new CommandData(argument, outputHandler));
        }
    }

    private static boolean isCommand(String command) {
        return commandsHashMap.containsKey(command);
    }
}
