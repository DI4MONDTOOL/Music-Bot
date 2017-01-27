package me.nivyox.discord;

import me.nivyox.discord.Commands.MemeCommand;
import me.nivyox.discord.Commands.PingCommand;
import me.nivyox.discord.utils.CommandParser;
import me.nivyox.discord.utils.StaticValues;


import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

public class Main {

    public static JDA jda;
    public static final CommandParser parser = new CommandParser();
    public static MemeCommand Meme;
    public static HashMap<String, Command> commands = new HashMap<String, Command>();

    public static void main(String[] args) {
        try {
            jda = new JDABuilder(AccountType.BOT).addListener(new DiscordChannelListener()).setToken(StaticValues.botkey).buildBlocking();

        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }

        commands.put("ping", new PingCommand());
        commands.put("meme", new MemeCommand());
    }

    public static void handleCommand(CommandParser.CommandContainer cmd){
        //System.out.println(cmd.invoke);
        if(commands.containsKey(cmd.invoke)){
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            if (safe){
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }else{
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }
        }

    }
}
