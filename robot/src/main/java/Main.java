import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.awt.*;


public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        String token = "";
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new Main());
        builder.setActivity(Activity.playing("Looking good video."));
        builder.build();
    }

    public void onReady(ReadyEvent event) {
        System.out.println("Logged in as " + event.getJDA().getSelfUser().getAsTag());
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        else {
            if (event.getChannel().getType().name().equals("dm")) return;
            if (event.getMessage().getContentRaw().toLowerCase().equals("s!ping")) {
                long ping = event.getJDA().getGatewayPing();

                event.getChannel().sendMessage("Pong! " + ping + "ms").queue();
            }

            if (event.getMessage().getContentRaw().toLowerCase().equals("s!help")) {
                EmbedBuilder embedHelp = new EmbedBuilder();
                embedHelp.setTitle("Help command for " + event.getJDA().getSelfUser().getName());
                embedHelp.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
                embedHelp.addField(":smile: General", "help, ping, stats, serverinfo", true);
                embedHelp.setFooter("Requested by " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());

                event.getChannel().sendMessage(embedHelp.build()).queue();
            }

            if (event.getMessage().getContentRaw().toLowerCase().equals("s!stats")) {
                EmbedBuilder embedStats = new EmbedBuilder();
                embedStats.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
                embedStats.setTitle("Statistics " + event.getJDA().getSelfUser().getName());
                embedStats.setColor(Color.RED);
                embedStats.addField("Bot Information", "Username: " + event.getJDA().getSelfUser().getAsTag() + "\nDiscriminator: " + event.getJDA().getSelfUser().getDiscriminator(), false);
                embedStats.addField("System Information", "Websocket ping: " + event.getJDA().getGatewayPing() +"ms\nPlatform: " + System.getProperty("os.name"), false);

                event.getChannel().sendMessage(embedStats.build()).queue();
            }

            if (event.getMessage().getContentRaw().toLowerCase().equals("s!serverinfo")) {
                String name = event.getMessage().getGuild().getName();
                String region = event.getMessage().getGuild().getRegionRaw();
                String owner = event.getMessage().getGuild().getOwner().getUser().getAsTag();
                int rolesSize = event.getMessage().getGuild().getRoles().size();
                int membersSize = event.getMessage().getGuild().getMemberCount();
                int boost = event.getMessage().getGuild().getBoostCount();

                EmbedBuilder embedServer = new EmbedBuilder();

                embedServer.setColor(Color.black);
                embedServer.setThumbnail(event.getMessage().getGuild().getIconUrl());
                embedServer.setTitle("Server " + name);
                embedServer.addField("Server Region", region.toUpperCase(), false);
                embedServer.addField("Server Owner", owner, false);
                embedServer.addField("Server Roles", String.valueOf(rolesSize) + " roles", false);
                embedServer.addField("Server Members", String.valueOf(membersSize) + " members", false);
                embedServer.addField("Server Boosts", String.valueOf(boost) + " Boosts", false);

                event.getChannel().sendMessage(embedServer.build()).queue();


            }
            if (event.getMessage().getContentRaw().equals("<@!" + event.getJDA().getSelfUser().getId() + ">") ||
            event.getMessage().getContentRaw().equals("<@" + event.getJDA().getSelfUser().getId() + ">")) {
                event.getChannel().sendMessage(":wave: | Hello <@!" + event.getAuthor().getId() + "> my prefix is `s!`").queue();
            }
        }
    }
}
