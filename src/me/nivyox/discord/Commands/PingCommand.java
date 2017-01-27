package me.nivyox.discord.Commands;

import me.nivyox.discord.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Borre on 27/01/2017.
 */
public class PingCommand implements Command {
    private final String HELP = "USAGE: /ping";

    public boolean called(String[] args, MessageReceivedEvent event) {

        return true;
    }

    public void action(String[] args, MessageReceivedEvent event) {

        event.getTextChannel().sendMessage("PONG");
    }

    public String help() {

        return HELP;
    }

    public void executed(boolean succes, MessageReceivedEvent event) {
        return;
    }
}
