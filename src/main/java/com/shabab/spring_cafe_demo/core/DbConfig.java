package com.shabab.spring_cafe_demo.core;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    private final String url, username, password;

    public DbConfig(
            @Value("${db.url}") String url,
            @Value("${db.username}") String username,
            @Value("${db.password}") String password
    ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Bean
    @Profile("local")
    public DataSource localDataSource() {
        var dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);

        return dataSource;
    }

    @Bean
    @Profile("cloud")
    public DataSource cloudDataSource() {
        var dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setMaximumPoolSize(100);

        return dataSource;
    }
}
