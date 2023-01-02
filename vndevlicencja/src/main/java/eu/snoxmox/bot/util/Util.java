package eu.snoxmox.bot.util;

import eu.snoxmox.bot.Main;
import eu.snoxmox.bot.mysql.MySQL;
import lombok.SneakyThrows;

public class Util {

    private static final String license = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toLowerCase();


    private static final MySQL mySQL = Main.getMySQL();

    public static String generateLicense() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= 35; i++) {
            if (i == 9 || i == 18 || i == 27) builder.append("-");
            else builder.append(license.charAt((int) (Math.random() * license.length())));
        }
        if (checkIfLicenseExist(builder.toString())) {
            return generateLicense();
        }
        return builder.toString();
    }

    @SneakyThrows
    private static boolean checkIfLicenseExist(String license) {
        return mySQL.getConnection().sendPreparedStatement("SELECT * FROM license_list WHERE license = '" + license + "'").get().getRows().size() >= 1;
    }

}
