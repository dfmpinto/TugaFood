import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class RegistrationTestLifecycleManager implements QuarkusTestResourceLifecycleManager {

    public static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer<>("postgres:16.1")
            .withEnv("POSTGRES_USER", "admin")
            .withEnv("POSTGRES_PASSWORD", "admin")
            .withEnv("POSTGRES_DB", "restaurant")
            .withExposedPorts(5432);

    @Override
    public Map<String,String> start() {
        POSTGRES.start();

        return Map.of(
                "quarkus.datasource.jdbc.url", POSTGRES.getJdbcUrl(),
                "quarkus.datasource.username", POSTGRES.getUsername(),
                "quarkus.datasource.password",POSTGRES.getPassword(),
                "quarkus.hibernate-orm.database.generation","drop-and-create"
        );
    }

    @Override
    public void stop() {
        if(POSTGRES.isRunning()) {
            POSTGRES.stop();
        }
    }
}