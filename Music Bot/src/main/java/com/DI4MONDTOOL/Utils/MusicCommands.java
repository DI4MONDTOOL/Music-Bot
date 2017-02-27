package com.DI4MONDTOOL.Utils;

import com.DI4MONDTOOL.Music.GuildMusicManager;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.Map;
import java.util.concurrent.BlockingDeque;

/**
 * Created by Borre on 29/01/2017.
 */
public class MusicCommands {

    //Vars voor music
    public static AudioPlayerManager playerManager;
    public static Map<Long, GuildMusicManager> musicManagers;
    public static BlockingDeque<AudioTrack> queue;

    public MusicCommands(AudioPlayerManager playerManager, Map<Long, GuildMusicManager> musicManagers ){
        this.playerManager=playerManager;
        this.musicManagers=musicManagers;
    }

    //Geen idee wat dit doet
    private static synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    //methode voor het laden en spelen van muziek
    public static void loadAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        if (trackUrl.startsWith("https")) {
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
        }else{

            playerManager.loadItemOrdered(musicManager, "ytsearch: " + trackUrl, new AudioLoadResultHandler() {
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

                    channel.sendMessage("Adding to queue " + firstTrack.getInfo().title).queue();

                    play(channel.getGuild(), musicManager, firstTrack);
                }

                @Override
                public void noMatches() {

                }

                @Override
                public void loadFailed(FriendlyException exception) {

                }
            });
        }

    }
    //methode voor het spelen van muziek
    public static void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.setVolume(15);

        connectToFirstVoiceChannel(guild.getAudioManager());

        musicManager.scheduler.queue(track);
    }

    public static void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("Skipped to next track.").queue();
    }

    public static void stopPlaying(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.pause();
    }

    public static void resumePlaying(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.resume();
    }

    public static void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }
        }
    }

    public static void setVolume(int volume, TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.setVolume(volume);
    }

    public static BlockingDeque<AudioTrack> getQueue(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        return musicManager.scheduler.getQueue();
    }

    public static void deleteQueue(TextChannel channel)
    {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.deleteQueue();
        musicManager.player.stopTrack();
    }

    public static String currentTrack(TextChannel channel)
    {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        AudioTrack track = musicManager.player.getPlayingTrack();

        return track.getInfo().title;

    }



}
