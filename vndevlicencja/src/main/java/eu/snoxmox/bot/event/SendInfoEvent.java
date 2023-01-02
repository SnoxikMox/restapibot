package eu.snoxmox.bot.event;

import eu.snoxmox.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.junit.Test;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class SendInfoEvent extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        final Member member = e.getMember();
        if (member == null) return;
        if (!member.getPermissions().contains(Permission.ADMINISTRATOR))return;
        if (e.getMessage().getContentRaw().equalsIgnoreCase("!wyslijinfonatematlicencji")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("**Licencja**");
            embedBuilder.setDescription("> Witaj! \n" +
                    "> Tutaj możesz wygenerować swoją własną licencje, potrzebną do aktywacji naszych pluginów. \n" +
                    "> Aby ją wygenerować, kliknij guzik **Stwórz licencje!** \n" +
                    "> Jeśli posiadasz już licencje i chcesz ją sprawdzić, kliknij guzik **Sprawdź licencje!**");
            embedBuilder.setColor(new Color(116, 1, 188, 65));
            embedBuilder.setFooter(date(), Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            e.getChannel().sendMessageEmbeds(embedBuilder.build()).addActionRow(Button.success("createlicense", "Stwórz licencje!"), Button.secondary("checklicense", "Sprawdź licencje!")).queue();
        }
    }

    private static String date() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

}
