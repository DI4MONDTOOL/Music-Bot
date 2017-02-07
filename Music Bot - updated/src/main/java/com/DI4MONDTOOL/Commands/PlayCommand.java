package com.DI4MONDTOOL.Commands;

import com.DI4MONDTOOL.Command;
import com.DI4MONDTOOL.Utils.MusicCommands;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Borre on 29/01/2017.
 */
public class PlayCommand implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String title = "";
        for (String s : args) {
            if(title == ""){
                title = title + s;
            }else {
                title = title + " " + s;
            }
        }
        MusicCommands.loadAndPlay(event.getTextChannel(), title);
    }

    @Override
    public String help() {
        final String help = "The " + "**/play**" + " command starts playing a song";
        return help;
    }

    @Override
    public void executed(boolean succes, MessageReceivedEvent event) {

    }
}
