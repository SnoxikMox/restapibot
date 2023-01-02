package eu.snoxmox.bot;

import eu.snoxmox.bot.event.CreateTicketEvent;
import eu.snoxmox.bot.event.LicenseButtonsEvent;
import eu.snoxmox.bot.event.SendInfoEvent;
import eu.snoxmox.bot.mysql.MySQL;
import eu.snoxmox.bot.util.Util;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

@Getter
public class Main {

    @Getter
    private static JDA jda;

    @Getter
    private static MySQL mySQL;

    public static void main(String[] args) {
        jda = JDABuilder.createDefault("MTA0MjM1MTg1MTYyODEzNDQ0Mg.GCe5iM.4XnYNAdkqEKMh61Q5lHEQ__Q_rpDTh1w9LjRtk")
                .setAutoReconnect(true)
                .enableIntents(GatewayIntent.GUILD_EMOJIS_AND_STICKERS)
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .enableCache(CacheFlag.EMOJI)
                .enableCache(CacheFlag.ACTIVITY)
                .enableCache(CacheFlag.MEMBER_OVERRIDES)
                .enableCache(CacheFlag.VOICE_STATE)
                .enableCache(CacheFlag.SCHEDULED_EVENTS)
                .enableCache(CacheFlag.CLIENT_STATUS)
                .enableCache(CacheFlag.ONLINE_STATUS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        mySQL = new MySQL();
        jda.addEventListener(new SendInfoEvent());
        jda.addEventListener(new LicenseButtonsEvent(mySQL));
        jda.addEventListener(new CreateTicketEvent());
        System.out.println("Zalogowano: " + jda.getSelfUser().getAsTag());
        String license = Util.generateLicense();
        System.out.println("Wygenerowano licencje: " + license);
        mySQL.createLicense("jakis tam dojebany member", license);
    }
}
