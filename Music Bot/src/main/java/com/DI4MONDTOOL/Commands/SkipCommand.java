package com.DI4MONDTOOL.Commands;

import com.DI4MONDTOOL.Command;
import com.DI4MONDTOOL.Utils.MusicCommands;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by User-PC on 31/01/2017.
 */
public class SkipCommand implements Command {


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        MusicCommands.skipTrack(event.getTextChannel());
    }

    @Override
    public String help() {
        return "The **/skip command** skips the current track.";
    }

    @Override
    public void executed(boolean succes, MessageReceivedEvent event) {

    }
}
