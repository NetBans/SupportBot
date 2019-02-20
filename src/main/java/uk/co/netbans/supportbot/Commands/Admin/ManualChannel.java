package uk.co.netbans.supportbot.Commands.Admin;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import uk.co.netbans.supportbot.CommandFramework.Command;
import uk.co.netbans.supportbot.CommandFramework.CommandArgs;
import uk.co.netbans.supportbot.CommandFramework.CommandCategory;
import uk.co.netbans.supportbot.NetBansBot;
import uk.co.netbans.supportbot.CommandFramework.CommandResult;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ManualChannel {

    @Command(name = "channel", displayName = "channel", permission = "supportbot.command.admin.channel", usage = "channel <create|move> <user> [<channel>]", category = CommandCategory.ADMIN)
    public CommandResult onManualChannel(CommandArgs commandArgs) {
        NetBansBot bot = commandArgs.getBot();
        String[] args = commandArgs.getArgs();
        TextChannel channel = (TextChannel) commandArgs.getChannel();
        if (args.length >= 2) {
            if (args[0].equals("create")) {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YY");
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
                TextChannel supportChannel;

                Member member = bot.getJDA().getGuildById(bot.getGuildID()).getMemberById(args[1].replaceAll("<", "").replaceAll("@", "").replaceAll("!", "").replaceAll(">", ""));
                if (args.length != 3) {
                    supportChannel = (TextChannel) bot.getJDA().getCategoryById(Long.valueOf(bot.getConfig().getConfigValue("category").getAsString()))
                            .createTextChannel(member.getEffectiveName() + "-" + "manual").complete();
                } else {
                    supportChannel = (TextChannel) bot.getJDA().getTextChannelById(args[2].replaceAll("<", "").replaceAll("#", "").replaceAll(">", "")).getParent().createTextChannel(member.getEffectiveName() + "-" + "manual").complete();
                }

                supportChannel.createPermissionOverride(member).setAllow(101440).complete();
                supportChannel.getManager().setTopic("Creation date: " + supportChannel.getCreationTime().format(dateFormat) + " Creation Time: " + supportChannel.getCreationTime().format(timeFormat) + "GMT").complete();
                Message message = new MessageBuilder()
                        .append("**User:** " + member.getAsMention())
                        .append("\n")
                        .append("**Message:** " + "This channel was created manually, so please see below messages for the reason!")
                        .append("\n")
                        .append("\n")
                        .append("_To close this ticket please react with a \u2705 to this message!_")
                        .build();
                Message supportMessage = bot.getMessenger().sendMessage(supportChannel, message, 0);
                supportMessage.pin().complete();
                supportChannel.getHistory().retrievePast(1).queue(l -> l.forEach(m -> m.delete().queue()));
                supportMessage.addReaction("\u2705").complete();
                member.getUser().openPrivateChannel().complete().sendMessage(new EmbedBuilder()
                        .setTitle("Support Channel")
                        .setDescription("https://discordapp.com/channels/" + bot.getGuildID() + "/" + supportChannel.getIdLong())
                        .setColor(new Color(127, 255, 212))
                        .build()).complete();
                return CommandResult.SUCCESS;
            } else if (args[0].equals("move")) {
                Category original = channel.getParent();
                TextChannel channelArg = bot.getJDA().getTextChannelById(args[1].replaceAll("<", "").replaceAll("#", "").replaceAll(">", ""));
                channel.getManager().setParent(channelArg.getParent()).complete();
                channel.getManager().setName(channel.getName().replaceAll("-[0-9]*", "-manual")).complete();
                channel.sendMessage("**Channel was moved from Category: " + original.getName() + " to Category: " + channelArg.getParent().getName() + "!**").complete();
                return CommandResult.SUCCESS;
            } else {
                return CommandResult.INVALIDARGS;
            }
        }
        return CommandResult.INVALIDARGS;
    }
}
