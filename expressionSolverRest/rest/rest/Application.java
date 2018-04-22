package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        // Setup the cache table in H2 (see http://www.h2database.com/html/main.html)

        // Create the cache table. Each row of this table contains
        // an operation (add or mult), left and right operands, and answer
        jdbcTemplate.execute("DROP TABLE cache IF EXISTS");
        jdbcTemplate.execute(
                "CREATE TABLE cache(" +
                "id SERIAL, " +
                "expression VARCHAR(255), " +
                "answer INT)"
        );
    }
}
