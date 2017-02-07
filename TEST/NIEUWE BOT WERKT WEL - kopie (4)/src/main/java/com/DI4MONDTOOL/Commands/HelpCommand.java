package com.DI4MONDTOOL.Commands;

import com.DI4MONDTOOL.Command;
import com.DI4MONDTOOL.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Borre on 29/01/2017.
 */
public class HelpCommand implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String command = "";
        for(String s : Main.commands.keySet())
        {
            command = command + '/' + s + "\r\n";
        }
        event.getAuthor().getPrivateChannel().sendMessage("Do " + "**/<command> help**" + " to get more info about the command itself!" + "\r\n" +
                "**All possible commands are:**" + "\r\n" + command).queue();

    }
    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean succes, MessageReceivedEvent event) {
        return;
    }
}
