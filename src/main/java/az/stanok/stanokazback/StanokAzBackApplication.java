package az.stanok.stanokazback;

import az.stanok.stanokazback.config.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing
@EnableJpaRepositories("az.stanok")
public class StanokAzBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(StanokAzBackApplication.class, args);
    }

}
