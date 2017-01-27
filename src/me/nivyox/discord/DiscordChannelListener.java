package me.nivyox.discord;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Niek on 1/17/2017.
 */
public class DiscordChannelListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContent().startsWith("!") && event.getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));


            //User sender = event.getAuthor();
            //Message message = event.getMessage();
            //MessageChannel channel = event.getChannel();
            //System.out.println(sender.getName() + " said > '" + message.getContent() + "' in " + channel.getName());
        }
    }


    @Override
    public void onReady(ReadyEvent event){

        //Main.log("status", "Logged in as: " + event.getJDA().getSelfUser().getName());

    }
}
