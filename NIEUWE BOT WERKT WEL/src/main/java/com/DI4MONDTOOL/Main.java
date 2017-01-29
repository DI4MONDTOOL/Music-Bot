package com.DI4MONDTOOL;

import com.DI4MONDTOOL.Commands.HelpCommand;
import com.DI4MONDTOOL.Commands.MemeCommand;
import com.DI4MONDTOOL.Commands.PingCommand;
import com.DI4MONDTOOL.Music.GuildMusicManager;
import com.DI4MONDTOOL.Utils.CommandParser;
import com.DI4MONDTOOL.Utils.StaticValues;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

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

    }

    //Vars voor music
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;


    //Constructor voor main fixt alles voor music
	private Main() {
		this.musicManagers = new HashMap<>();
		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
    }

    //Geen idee wat dit doet
    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
	    long guildId = Long.parseLong(guild.getId());
	    GuildMusicManager musicManager = musicManagers.get(guildId);

    	if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
	        musicManagers.put(guildId, musicManager);
	    }

	    guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

	    return musicManager;
    }

    //als er message wordt ontvangen doe dit
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //als message begint met "/" dan is het een commando en doe dan dit
	    if (event.getMessage().getContent().startsWith("/") && event.getAuthor().getId() != jda.getSelfUser().getId()) {
            //Splits alle argumenten van elkaar en steek ze in een lijst.
	        Main.handleCommand(parser.parse(event.getMessage().getContent().toLowerCase(), event));
            //loadAndPlay(event.getTextChannel(), "https://www.youtube.com/watch?v=_lnvidAkBh4");
        }

    }

    //methode voor het laden en spelen van muziek
    private void loadAndPlay(TextChannel channel, String trackUrl) {
	    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

	    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
	        @Override
	        public void trackLoaded(AudioTrack track) {
		        channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

		        play(channel.getGuild(), musicManager, track);
	        }

	        @Override
	        public void playlistLoaded(AudioPlaylist playlist) {
		        AudioTrack firstTrack = playlist.getSelectedTrack();

		        if (firstTrack == null) {
		        firstTrack = playlist.getTracks().get(0);
		        }

		        channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

		        play(channel.getGuild(), musicManager, firstTrack);
	        }

	        @Override
            public void noMatches() {
		channel.sendMessage("Nothing found by " + trackUrl).queue();
	  }

	        @Override
	        public void loadFailed(FriendlyException exception) {
		        channel.sendMessage("Could not play: " + exception.getMessage()).queue();
	        }
	    });
    }
    //methode voor het spelen van muziek
    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
	    connectToFirstVoiceChannel(guild.getAudioManager());

	    musicManager.scheduler.queue(track);
    }

    private void skipTrack(TextChannel channel) {
	    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
	    musicManager.scheduler.nextTrack();

	    channel.sendMessage("Skipped to next track.").queue();
    }

    private static void connectToFirstVoiceChannel(AudioManager audioManager) {
	    if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
	        for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
		    audioManager.openAudioConnection(voiceChannel);
		    break;
	        }
	    }
    }


    public static void handleCommand(CommandParser.CommandContainer cmd) {
        //System.out.println(cmd.invoke);
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            if (safe) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }
        }

    }
}
