package me.nivyox.discord;

import me.nivyox.discord.utils.StaticValues;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

/**
 * Created by Niek on 1/17/2017.
 */
public class Main {

    public static JDA jda;

    public static void main(String[] args) {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(StaticValues.botkey).addListener(new DiscordChannelListener()).buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
    }
}
