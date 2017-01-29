package me.nivyox.discord.utils;

import me.nivyox.discord.Main;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Borre on 29/01/2017.
 */
public class DiscordChannelListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContent().startsWith("/")) {
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));
        }
    }

    @Override
    public void onReady(ReadyEvent event){
        //Main.log("status", "Logged in as: " + event.getJDA().getSelfUser().getName());
    }


}
