package com.DI4MONDTOOL;

import com.DI4MONDTOOL.Commands.*;
import com.DI4MONDTOOL.Music.GuildMusicManager;
import com.DI4MONDTOOL.Utils.CommandParser;
import com.DI4MONDTOOL.Utils.MusicCommands;
import com.DI4MONDTOOL.Utils.StaticValues;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;


public class Main extends ListenerAdapter {

    public static JDA jda;
    public static final CommandParser parser = new CommandParser();
    public static HashMap<String, Command> commands = new HashMap<String, Command>();


    //jda bouwen met botkey uit StaticValues en steek alle commands in de HashMap commands
    public static void main(String[] args) throws Exception {
	    jda = new JDABuilder(AccountType.BOT)
		.setToken(StaticValues.botkey)
		.buildBlocking();
	    //New main aanmaken die luistert naar events
	    jda.addEventListener(new Main());

	    commands.put("ping", new PingCommand());
	    commands.put("help", new HelpCommand());
	    commands.put("meme", new MemeCommand());
	    commands.put("play", new PlayCommand());
	    commands.put("pause", new PauseCommand());
        commands.put("volume", new VolumeCommand());
        commands.put("resume", new ResumeCommand());
        commands.put("queue", new QueueCommand());
        commands.put("stop", new StopCommand());
        commands.put("skip", new SkipCommand());
        commands.put("current", new CurrentCommand());

    }

    //Vars voor music
    private static AudioPlayerManager playerManager;
    private static Map<Long, GuildMusicManager> musicManagers;


    //Constructor voor main fixt alles voor music
	private Main() {
		this.musicManagers = new HashMap<>();
		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
		new MusicCommands(playerManager, musicManagers);
    }

    //als er message wordt ontvangen doe dit
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //als message begint met "/" dan is het een commando en doe dan dit
	    if (event.getMessage().getContent().startsWith("/") && event.getAuthor().getId() != jda.getSelfUser().getId())
	    {
            //Splits alle argumenten van elkaar en steek ze in een lijst.
	        Main.handleCommand(parser.parse(event.getMessage().getContent(), event), event);
            //loadAndPlay(event.getTextChannel(), "https://www.youtube.com/watch?v=_lnvidAkBh4");

        }

    }

    public static void handleCommand(CommandParser.CommandContainer cmd, MessageReceivedEvent event) {
        //System.out.println(cmd.invoke);
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            if (safe) {
                if(cmd.args.length == 0){
                        commands.get(cmd.invoke).action(cmd.args, cmd.event);
                        commands.get(cmd.invoke).executed(safe, cmd.event);

                }else{
                    if(cmd.args[0].equals("help"))
                    {

                        event.getChannel().sendMessage(commands.get(cmd.invoke).help()).queue();
                    }else
                    {
                        commands.get(cmd.invoke).action(cmd.args, cmd.event);
                        commands.get(cmd.invoke).executed(safe, cmd.event);
                    }
                }


            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }


        }else event.getTextChannel().sendMessage("Use /help to see all available commands").queue();
    }

}