package config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {

    @Value("${SERVER_PORT}")
    private int serverPort;

    @Value("${SPRING_DATASOURCE_URL}")
    private String dataSourceUrl;

    @Value("${SPRING_DATASOURCE_USERNAME}")
    private String dataSourceUsername;

    @Value("${SPRING_DATASOURCE_PASSWORD}")
    private String dataSourcePassword;

    @Value("${SPRING_JPA_HIBERNATE_DDL_AUTO}")
    private String hibernateDdlAuto;

    @Value("${SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT}")
    private String hibernateDialect;

    @Value("${SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL}")
    private boolean hibernateFormatSql;

    @Value("${SPRING_JPA_SHOW_SQL}")
    private boolean showSql;

    @Value("${SPRING_MVC_PATHMATCH_MATCHING_STRATEGY}")
    private String pathMatchMatchingStrategy;

    @Value("${SPRING_LIQUIBASE_ENABLED}")
    private boolean liquibaseEnabled;

    @Value("${SPRING_LIQUIBASE_DROP_FIRST}")
    private boolean liquibaseDropFirst;

    @Value("${SPRING_LIQUIBASE_CHANGE_LOG}")
    private String liquibaseChangeLog;

    @Value("${SPRING_LIQUIBASE_DEFAULT_SCHEMA}")
    private String liquibaseDefaultSchema;

    @Value("${SPRING_DATASOURCE_HIKARI_CONNECTION_TIMEOUT}")
    private int hikariConnectionTimeout;

    @Value("${SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE}")
    private int hikariMaximumPoolSize;

    @Value("${SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE}")
    private int hikariMinimumIdle;

    @Value("${SPRING_DATASOURCE_HIKARI_IDLE_TIMEOUT}")
    private int hikariIdleTimeout;

    @Value("${PGADMIN_DEFAULT_EMAIL}")
    private String pgAdminDefaultEmail;

    @Value("${PGADMIN_DEFAULT_PASSWORD}")
    private String pgAdminDefaultPassword;
}
