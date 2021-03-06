package no.kristiania.http;

import no.kristiania.person.PersonDao;
import no.kristiania.person.RoleDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PersonnelServer {
    public static void main(String[] args) throws IOException {
        DataSource dataSource = createDataSource();
        RoleDao roleDao = new RoleDao(dataSource);
        PersonDao personDao = new PersonDao(dataSource);
        HttpServer httpServer = new HttpServer(1984);
        httpServer.addController("/api/roleOptions", new RoleOptionsController(roleDao));
        httpServer.addController("/api/newPerson", new AddPersonController(personDao));
        System.out.println("http://localhost:" + httpServer.getPort() + "/index.html");
    }

    public static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("pgr203.properties")) {
            properties.load(reader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url",
                "jdbc:postgresql://localhost:5432/person_db"
        ));
        dataSource.setUser(properties.getProperty("dataSource.user", "person_dbuser"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
