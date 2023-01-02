package eu.snoxmox.bot.event;

import eu.snoxmox.bot.mysql.MySQL;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class LicenseButtonsEvent extends ListenerAdapter {

    private final MySQL mySQL;
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e) {
        if (e.getMember() == null) return;
        final String id = e.getComponentId();
        if (id.equalsIgnoreCase("createlicense")) {
            if (mySQL.checkIfUserExist(e.getMember())) {
                e.reply("Posiadasz już licencje!").setEphemeral(true).queue();
                return;
            }
            mySQL.createLicense(e.getMember());
            e.reply("Pomyślnie wygenerowano licencje!").setEphemeral(true).queue();
        } else if (id.equalsIgnoreCase("checklicense")) {
            if (!mySQL.checkIfUserExist(e.getMember())) {
                e.reply("Nie posiadasz licencji!").setEphemeral(true).queue();
                return;
            }
            e.reply("Twoja licencja to: " + mySQL.getLicense(e.getMember())).setEphemeral(true).queue();
        }
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent e) {
        final Member member = e.getMember();
        if (member == null) return;
        if (mySQL.checkIfUserExist(member)) {
            mySQL.deleteLicense(member);
        }
    }

}
