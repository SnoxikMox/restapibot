package eu.snoxmox.bot.event;

import eu.snoxmox.bot.Main;
import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateTicketEvent extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        final Member member = e.getMember();
        if (member == null) return;
        if (!member.getPermissions().contains(Permission.ADMINISTRATOR)) return;
        if (e.getMessage().getContentRaw().equalsIgnoreCase("!wyslijinfonatematticketa")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("**vndev - мanager**");
            embedBuilder.setDescription("> Jeśli chcesz się z nami skontaktować\n" +
                    "> kliknij przycisk poniżej aby otworzyć ticket!");
            embedBuilder.setColor(new Color(116, 1, 188, 65));
            embedBuilder.setFooter(date(), Main.getJda().getSelfUser().getEffectiveAvatarUrl());
            e.getChannel().sendMessageEmbeds(embedBuilder.build()).addActionRow(Button.secondary("ticketcreate", "ѕтwórz тιcĸeт")).queue();
        }
    }

    private static String date() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    @Override
    public void onButtonInteraction(@NonNull ButtonInteractionEvent e) {
        ButtonInteraction button = e.getInteraction();
        if (Objects.equals(button.getButton().getId(), "ticketcreate")) {
            if (Objects.requireNonNull(e.getGuild()).getTextChannelsByName("\uD83D\uDCEE┃" + Objects.requireNonNull(e.getMember()).getUser().getName(), true).size() > 0) {
                button.reply("Juz masz otwarty ticket!").setEphemeral(true).queue();
                return;
            }
            Objects.requireNonNull(Objects.requireNonNull(e.getGuild()).getCategoryById(1045839483582566410L)).createTextChannel("\uD83D\uDCEE┃" + Objects.requireNonNull(e.getMember()).getUser().getName())
                    .queue(channel -> {
                        channel.getPermissionContainer().getManager().putPermissionOverride(e.getMember(), Permission.VIEW_CHANNEL.getRawValue(), Permission.VIEW_AUDIT_LOGS.getRawValue()).queue();
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("vndev - мanager");
                        embed.setDescription("> wιтaj υтworzyłeś тιcĸeт\n" +
                                "> napιѕz z czyм do naѕ przycнodzιѕz");
                        channel.sendMessageEmbeds(embed.build()).queue();
                    });
        }
    }
}