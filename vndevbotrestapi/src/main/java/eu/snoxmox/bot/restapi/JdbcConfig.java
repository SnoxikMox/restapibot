package eu.snoxmox.bot.restapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("eu.snoxmox.bot.restapi")
public class JdbcConfig {
    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://www10621_snoxmox:D98TTDBnjHmlFcmbXcd3@base.vndev.fun:3306/www10621_snoxmox");
        return dataSource;
    }
}