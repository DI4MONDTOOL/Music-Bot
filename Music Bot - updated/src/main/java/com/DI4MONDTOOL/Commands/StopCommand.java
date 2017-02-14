package com.DI4MONDTOOL.Commands;

import com.DI4MONDTOOL.Command;
import com.DI4MONDTOOL.Utils.MusicCommands;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by User-PC on 31/01/2017.
 */
public class StopCommand implements Command
{


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        MusicCommands.stopPlaying(event.getTextChannel());
        MusicCommands.deleteQueue(event.getTextChannel());


    }

    @Override
    public String help() {
        return "The **/stop command** stops the Music Bot and clears the queue. To continue listening, you must use **/resume** again.";
    }

    @Override
    public void executed(boolean succes, MessageReceivedEvent event) {

    }
}
