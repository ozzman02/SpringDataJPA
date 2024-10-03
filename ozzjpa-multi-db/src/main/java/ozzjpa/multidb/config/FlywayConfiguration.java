package ozzjpa.multidb.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfiguration {

    @Bean
    @ConfigurationProperties("spring.card.flyway")
    public DataSourceProperties cardFlywayDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean(initMethod = "migrate")
    public Flyway flywayCard(
            @Qualifier("cardFlywayDataSourceProps") DataSourceProperties cardFlywayDataSourceProps) {
        return Flyway.configure()
                .dataSource(
                        cardFlywayDataSourceProps.getUrl(),
                        cardFlywayDataSourceProps.getUsername(),
                        cardFlywayDataSourceProps.getPassword())
                .locations("classpath:/db/migration/card")
                .load();
    }

    @Bean
    @ConfigurationProperties("spring.cardholder.flyway")
    public DataSourceProperties cardHolderDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean(initMethod = "migrate")
    public Flyway flywayCardHolder(
            @Qualifier("cardHolderDataSourceProps") DataSourceProperties cardHolderDataSourceProps) {
        return Flyway.configure()
                .dataSource(
                        cardHolderDataSourceProps.getUrl(),
                        cardHolderDataSourceProps.getUsername(),
                        cardHolderDataSourceProps.getPassword())
                .locations("classpath:/db/migration/cardholder")
                .load();
    }

    @Bean
    @ConfigurationProperties("spring.pan.flyway")
    public DataSourceProperties panDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean(initMethod = "migrate")
    public Flyway flywayPan(
            @Qualifier("panDataSourceProps") DataSourceProperties panDataSourceProps) {
        return Flyway.configure()
                .dataSource(
                        panDataSourceProps.getUrl(),
                        panDataSourceProps.getUsername(),
                        panDataSourceProps.getPassword())
                .locations("classpath:/db/migration/pan")
                .load();
    }

}
