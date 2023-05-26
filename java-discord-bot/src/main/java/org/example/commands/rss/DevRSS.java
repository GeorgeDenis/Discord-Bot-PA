package org.example.commands.rss;

import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.FetcherException;
import com.rometools.fetcher.impl.HttpURLFeedFetcher;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.commands.BotCommand;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class DevRSS implements BotCommand<MessageReceivedEvent> {
    @Override
    public String getCommand() {
        return "!dev";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (!event.getMessage().getContentDisplay().equalsIgnoreCase(getCommand())) {
            return;
        }

        FeedFetcher fetcher = new HttpURLFeedFetcher();
        try {
            SyndFeed feed = fetcher.retrieveFeed(new URL("https://dev.to/feed"));
            int count = 5;
            for (Object o : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) o;
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.PINK);
                eb.setAuthor(Objects.requireNonNull(event.getGuild()).getName());
                eb.setThumbnail("https://d2fltix0v2e0sb.cloudfront.net/dev-black.png");
                eb.addField("Title: ", entry.getTitle(), true);
                eb.addField("Link: ", entry.getLink(), true);
                eb.setDescription("Topic: " + "Developers");

                event.getChannel().sendMessageEmbeds(eb.build()).queue();
                count--;
                if (count == 0)
                    break;

            }
            feed.getTitle();
            feed.getLink();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (FetcherException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Shows 5 newest articles about Developers";
    }
}
