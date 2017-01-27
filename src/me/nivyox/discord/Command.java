package me.nivyox.discord;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.security.MessageDigest;

/**
 * Created by Borre on 27/01/2017.
 */
public interface Command {

    public boolean called(String[] args, MessageReceivedEvent event);
    public void action(String[] args, MessageReceivedEvent event);
    public String help();
    public void executed(boolean succes, MessageReceivedEvent event);

}
