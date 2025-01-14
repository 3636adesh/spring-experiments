package com.example;

import com.example.common.AbstractIntegrationTest;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=validate"})
class ApplicationIntegrationTest extends AbstractIntegrationTest {

    @Autowired private DataSource dataSource;

    @Test
    void contextLoads() {
        assertThat(dataSource).isInstanceOf(HikariDataSource.class);
    }
}