package com.DI4MONDTOOL.Commands;

import com.DI4MONDTOOL.Command;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Borre on 29/01/2017.
 */
public class PingCommand implements Command {
    private final String HELP = "USAGE: /ping";

    public boolean called(String[] args, MessageReceivedEvent event) {

        return true;
    }

    public void action(String[] args, MessageReceivedEvent event) {


        if(event.isFromType(ChannelType.TEXT)){
            event.getChannel().sendMessage("PONG").queue();
        }else if (event.isFromType(ChannelType.PRIVATE)){
            event.getAuthor().getPrivateChannel().sendMessage("PONG").queue();
        }

    }

    public String help() {

        return HELP;
    }

    public void executed(boolean succes, MessageReceivedEvent event) {
        return;
    }
}
