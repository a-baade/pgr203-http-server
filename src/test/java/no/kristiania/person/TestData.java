package no.kristiania.person;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class TestData {

    public static DataSource testDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:persondb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
