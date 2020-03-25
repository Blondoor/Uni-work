package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu()
    {
        this.commands = new HashMap<String, Command>();
    }

    public void addCommand(Command c)
    {
        commands.put(c.getKey(),c);
    }

    private void printMenu()
    {
        for(Command com : commands.values())
        {
            String line = String.format("%4s : %s", com.getKey(),com.getDesc());
            System.out.println(line);
        }
    }

    public void Show()
    {
        Scanner scanner = new Scanner(System.in);

        while(true)
        {
            printMenu();
            System.out.println(">");
            String key = scanner.nextLine();
            Command com = commands.get(key);

            if(com == null) {
                System.out.println("Invalid option");
                continue;
            }
            com.execute();
        }
    }
}
