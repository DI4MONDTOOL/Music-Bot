package com.DI4MONDTOOL.Commands;

import com.DI4MONDTOOL.Command;
import com.DI4MONDTOOL.Utils.MusicCommands;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by User-PC on 31/01/2017.
 */
public class CurrentCommand implements Command{
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getChannel().sendMessage(MusicCommands.currentTrack(event.getTextChannel())).queue();
    }

    @Override
    public String help() {
        final String help = "The " + "**/current**" + " command gives you the current track when the music bot is playing!";
        return help;
    }

    @Override
    public void executed(boolean succes, MessageReceivedEvent event) {

    }
}
