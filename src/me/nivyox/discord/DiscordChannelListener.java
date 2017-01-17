package me.nivyox.discord;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Niek on 1/17/2017.
 */
public class DiscordChannelListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User sender = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        System.out.println(sender.getName() + " said > '" + message.getContent() + "' in " + channel.getName());
    }
}
