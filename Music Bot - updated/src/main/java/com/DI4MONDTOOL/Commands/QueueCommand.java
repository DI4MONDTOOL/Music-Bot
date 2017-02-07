package com.DI4MONDTOOL.Commands;

import com.DI4MONDTOOL.Command;
import com.DI4MONDTOOL.Utils.MusicCommands;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borre on 30/01/2017.
 */
public class QueueCommand implements Command {

    public ArrayList<String> queue;


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        queue = new ArrayList<>();
        for (AudioTrack a : MusicCommands.getQueue(event.getTextChannel())) {
            queue.add(a.getInfo().title);
            System.out.print(a.getInfo().title);

        }

        event.getChannel().sendMessage("Current queue: " + queue ).queue();
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean succes, MessageReceivedEvent event) {

    }
}
