package eu.snoxmox.bot.mysql;

import com.github.jasync.sql.db.Connection;
import com.github.jasync.sql.db.QueryResult;
import com.github.jasync.sql.db.mysql.MySQLConnectionBuilder;
import eu.snoxmox.bot.util.Util;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Member;
import java.util.concurrent.CompletableFuture;

@Getter
public class MySQL {

    private final Connection connection;

    @SneakyThrows
    public MySQL() {
        this.connection = MySQLConnectionBuilder.createConnectionPool("jdbc:mysql://www10621_snoxmox:D98TTDBnjHmlFcmbXcd3@base.vndev.fun:3306/www10621_snoxmox");
        createTable();
    }

    @SneakyThrows
    private void createTable() {
        CompletableFuture<QueryResult> ps = connection.sendPreparedStatement("CREATE TABLE IF NOT EXISTS license_list(id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255),license VARCHAR(36), PRIMARY KEY (id))");
        ps.get();
    }

    @SneakyThrows
    public boolean checkIfUserExist(Member member) {
        CompletableFuture<QueryResult> ps =  connection.sendPreparedStatement("SELECT * FROM license_list WHERE name = '" + member.getUser().getId() + "'");
        return ps.get().getRows().size() >= 1;
    }

    @SneakyThrows
    public void createLicense(Member member) {
        CompletableFuture<QueryResult> ps = connection.sendPreparedStatement("INSERT INTO license_list (name, license) VALUES ('" + member.getUser().getId() + "', '" + Util.generateLicense() + "')");
        ps.get();
    }

    @SneakyThrows
    public void createLicense(String member, String license) {
        CompletableFuture<QueryResult> ps = connection.sendPreparedStatement("INSERT INTO license_list (name, license) VALUES ('" + member + "', '" +license + "')");
        ps.get();
    }

    @SneakyThrows
    public String getLicense(Member member) {
        CompletableFuture<QueryResult> ps = connection.sendPreparedStatement("SELECT license FROM license_list WHERE name = '" + member.getUser().getId() + "'");
        if (ps.get().getRows().get(0) == null) return null;
        else return ps.get().getRows().get(0).getString("license");
    }

    @SneakyThrows
    public void deleteLicense(Member member) {
        CompletableFuture<QueryResult> ps = connection.sendPreparedStatement("DELETE FROM license_list WHERE name = '" + member.getUser().getId() + "'");
        ps.get();
    }

}
