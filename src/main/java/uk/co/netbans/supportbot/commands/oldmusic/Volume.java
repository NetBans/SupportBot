package uk.co.netbans.supportbot.commands.oldmusic;

public class Volume {

//    @Command(name = "volume", displayName = "volume", category = CommandCategory.MUSIC)
//    public CommandResult onVolume(CommandArgs commandArgs) {
//        SupportBot bot = commandArgs.getBot();
//        Member member = commandArgs.getMember();
//        TextChannel channel = (TextChannel) commandArgs.getChannel();
//        MusicManager musicPlayer = bot.getMusicManager();
//        String[] args = commandArgs.getArgs();
//        if (args.length == 0)
//            return CommandResult.INVALIDARGS;
//
//        if (musicPlayer.canBotPlayMusic(member, channel, bot)) {
//            try {
//                int vol = Integer.valueOf(args[0]);
//                if (vol > 100) {
//                    bot.getMessenger().sendEmbed(channel, Messenger.VOLUME_TOO_HIGH, 10);
//                } else if (vol > 1) {
//                    bot.getMusicManager().setVolume(channel.getGuild(), channel, vol);
//                    bot.getMessenger().sendEmbed(channel, Messenger.VOLUME_NOW_AT(vol), 10);
//                } else {
//                    bot.getMessenger().sendEmbed(channel, Messenger.VOLUME_TOO_LOW, 10);
//                }
//                return CommandResult.SUCCESS;
//            } catch (NumberFormatException e) {
//                return CommandResult.INVALIDARGS;
//            }
//        }
//        return CommandResult.SUCCESS;
//    }

}
