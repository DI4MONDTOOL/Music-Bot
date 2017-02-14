package com.DI4MONDTOOL.Commands;

import com.DI4MONDTOOL.Command;
import com.DI4MONDTOOL.Utils.MusicCommands;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Borre on 30/01/2017.
 */
public class PauseCommand implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        MusicCommands.stopPlaying(event.getTextChannel());
        event.getTextChannel().sendMessage("Player paused").queue();
    }

    @Override
    public String help() {
        final String help = "The " + "**/pause**" + " command pauses the music bot. The music is stopped until you do /resume";
        return help;
    }

    @Override
    public void executed(boolean succes, MessageReceivedEvent event) {

    }
}
