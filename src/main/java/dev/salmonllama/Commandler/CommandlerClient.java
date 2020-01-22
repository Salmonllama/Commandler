package dev.salmonllama.Commandler;

import org.javacord.api.AccountType;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.concurrent.CompletableFuture;

public class CommandlerClient {
    DiscordApi api;
    DiscordApiBuilder apiBuilder;

    private Configuration config;
    private Database db;
    private Handler handler;

    public Configuration getConfig() {
        return config;
    }

    public Database getDb() {
        return db;
    }

    public Handler getHandler() {
        return handler;
    }

    public CommandlerClient(String token) {
        apiBuilder = new DiscordApiBuilder().setAccountType(AccountType.BOT).setToken(token);
        // Init config, db, and handler
    }

    public CompletableFuture<CommandlerClient> connect() {
        CompletableFuture<CommandlerClient> future = new CompletableFuture<>();

        apiBuilder.login().thenAccept(api -> {
            api.addMessageCreateListener((event) -> {
                // Add command handling here
            });
        });

        return future;
    }
}